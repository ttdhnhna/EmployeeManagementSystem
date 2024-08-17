package com.practiceproject.EmployeeManagementSystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practiceproject.EmployeeManagementSystem.entity.AuditLog;
import com.practiceproject.EmployeeManagementSystem.entity.User;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long>{
    public Page<AuditLog> findAllByIduser(User iduser, Pageable pageable);
}
