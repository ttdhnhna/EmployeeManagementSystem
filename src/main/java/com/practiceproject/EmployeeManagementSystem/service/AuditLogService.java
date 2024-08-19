package com.practiceproject.EmployeeManagementSystem.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
}
