package com.practiceproject.EmployeeManagementSystem.service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.practiceproject.EmployeeManagementSystem.entity.AuditLog;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.repository.AuditLogRepository;

@Service
public class AuditLogService {
    @Autowired 
    AuditLogRepository repository;
    @Autowired
    AccountService uService;

    public AuditLog getLogByID(long id){
        Optional<AuditLog> optional = repository.findById(id);
        AuditLog auditLog = null;
        if(optional.isPresent()){
            auditLog=optional.get();
        }else{
            throw new IllegalStateException("Không tìm thấy id Log: "+id);
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
//            .filter(log -> ChronoUnit.DAYS.between(log.getNgayth(), now)>120)
            .filter(log -> ChronoUnit.DAYS.between(log.getNgayth(), now)>1)

            .collect(Collectors.toList());
        /*List<AuditLog> list = this.repository.findAll(): có tác dụng là lấy tất cả các log và cho vào list
         * .stream(): giúp tạo các log thành 1 chuỗi các phần tử có thể được xử lý song song hoặc theo trình tự
         * để thực hiện hoạt động lọc loại bỏ.
         * .filter(log -> ...): áp dụng bộ lọc cho stream nó sẽ chỉ giữ các phần tử mà hàm lọc trả về true.
         * ChronoUnit.DAYS.between(log.getNgayth(), now): Phương thức này tính toán số ngày giữa 2 biến.
         * .collect(Collectors.toList()): Thu thập các phần tử của stream vào 1 list 
         * Collectors.toList() là một trình thu thập tích lũy các phần tử đã lọc vào một List<AuditLog> mới.
         */
        this.repository.deleteAll(list);
    }

    public List<AuditLog> getListLogs(){
        User iduser = uService.getUserByID(Utility.getCurrentUserId());
        List<AuditLog> logs = this.repository.findTop10ByIduserOrderByNgaythDesc(iduser);
        return logs;
    }

    public void trackChanges(Object oldEntity, Object newEntity, Long iduser){
        Class<?> clasz = oldEntity.getClass();
        Field[] fields = clasz.getDeclaredFields();

        for(Field field:fields){
            field.setAccessible(true);

            try {
                Object oldValue = field.get(oldEntity);
                Object newValue = field.get(newEntity);
                
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
}
