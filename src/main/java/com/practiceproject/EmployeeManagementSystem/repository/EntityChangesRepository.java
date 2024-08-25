package com.practiceproject.EmployeeManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practiceproject.EmployeeManagementSystem.entity.AuditLog;
import com.practiceproject.EmployeeManagementSystem.entity.EntityChanges;

@Repository
public interface EntityChangesRepository extends JpaRepository<EntityChanges, Long>{
    public List<EntityChanges> findAllByIdlog(AuditLog idlog);
}
