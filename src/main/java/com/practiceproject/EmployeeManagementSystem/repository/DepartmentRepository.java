package com.practiceproject.EmployeeManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practiceproject.EmployeeManagementSystem.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    
}
