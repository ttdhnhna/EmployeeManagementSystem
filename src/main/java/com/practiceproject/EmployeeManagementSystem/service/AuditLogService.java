package com.practiceproject.EmployeeManagementSystem.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.practiceproject.EmployeeManagementSystem.entity.AuditLog;
import com.practiceproject.EmployeeManagementSystem.entity.EntityChanges;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.repository.AuditLogRepository;
import com.practiceproject.EmployeeManagementSystem.repository.EntityChangesRepository;

@Service
public class AuditLogService {
    @Autowired 
    AuditLogRepository repository;
    @Autowired
    EntityChangesRepository eRepository;
    @Autowired
    AccountService uService;
    @Autowired
    MessageSource messageSource;

    public void saveLog(AuditLog log){
        this.repository.save(log);
    }

    public AuditLog getLogByID(long id){
        Optional<AuditLog> optional = repository.findById(id);
        AuditLog auditLog = null;
        String mess = messageSource.getMessage("cantfindidlog", new Object[] { id }, LocaleContextHolder.getLocale());
    
        if(optional.isPresent()){
            auditLog=optional.get();
        }else{
            throw new IllegalStateException(mess+id);
        }
        return auditLog;
    }

    public Page<AuditLog> findPaginated(int pageNo, int pageSize, String sortField, 
    String sortDirection, Long iduser){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(sortField).ascending()
            : Sort.by(sortField).descending();
        Pageable pageable=PageRequest.of(pageNo-1, pageSize, sort);
        User user = uService.getUserByID(iduser);
        return this.repository.findAllByIduser(user, pageable);
    }

    public List<AuditLog> findAll(Long iduser, String keyword){
        if(keyword!=null){
            return repository.findAll(iduser, keyword);
        }
        return Collections.emptyList();
    }

    @Scheduled(cron = "0 0 0 * * ?")//Chức năng sẽ tự chạy sau mỗi nửa đêm.
    public void autoDeletelog(){
        LocalDateTime now = LocalDateTime.now();
        //Xóa toàn bộ các log có tuổi đời lớn hơn 120 ngày trực tiếp từ csdl.
        List<AuditLog> list = this.repository.findAll().stream()
        //  .filter(log -> ChronoUnit.DAYS.between(log.getNgayth(), now)>=1)
            .filter(log -> ChronoUnit.DAYS.between(log.getNgayth(), now) >= 120)
            .collect(Collectors.toList());
        for(AuditLog a : list){
            eRepository.deleteAll(eRepository.findAllByIdlog(a));
            repository.delete(a);
        }
        /*List<AuditLog> list = this.repository.findAll(): có tác dụng là lấy tất cả các log và cho vào list
         * .stream(): giúp tạo các log thành 1 chuỗi các phần tử có thể được xử lý song song hoặc theo trình tự
         * để thực hiện hoạt động lọc loại bỏ.
         * .filter(log -> ...): áp dụng bộ lọc cho stream nó sẽ chỉ giữ các phần tử mà hàm lọc trả về true.
         * ChronoUnit.DAYS.between(log.getNgayth(), now): Phương thức này tính toán số ngày giữa 2 biến.
         * .collect(Collectors.toList()): Thu thập các phần tử của stream vào 1 list 
         * Collectors.toList() là một trình thu thập tích lũy các phần tử đã lọc vào một List<AuditLog> mới.
         */
    }

    public List<AuditLog> getListLogs(){
        User iduser = uService.getUserByID(Utility.getCurrentUserId());
        List<AuditLog> logs = this.repository.findTop10ByIduserOrderByNgaythDesc(iduser);
        return logs;
    }

    public List<EntityChanges> getDetailLog(AuditLog auditLog){
        List<EntityChanges> entityChanges = eRepository.findAllByIdlog(auditLog);    
        return entityChanges;        
    }

    public int getUnreadLog(Long iduser){
        User user = uService.getUserByID(iduser);
        return repository.countByIduserAndIsReadFalse(user);
    }

    @Transactional
    public void readAllLog(){
        this.repository.markAllLogsasRead();
    }
}
