package com.practiceproject.EmployeeManagementSystem.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.practiceproject.EmployeeManagementSystem.entity.AuditLog;
import com.practiceproject.EmployeeManagementSystem.entity.User;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long>{
    public Page<AuditLog> findAllByIduser(User iduser, Pageable pageable);

    @Query(value = "SELECT a.* FROM audit_log a" 
    + " WHERE a.id_user = ?1 AND (a.ngayth LIKE %?2% "
    + " OR a.id_log LIKE %?2%);", nativeQuery = true)
    public List<AuditLog> findAll(Long iduser, String keywords);

    public List<AuditLog> findTop10ByIduserOrderByNgaythDesc(User iduser);
}
