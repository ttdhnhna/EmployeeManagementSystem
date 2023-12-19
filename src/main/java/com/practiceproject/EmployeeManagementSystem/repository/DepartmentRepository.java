package com.practiceproject.EmployeeManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.practiceproject.EmployeeManagementSystem.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("SELECT p FROM Department WHERE p.tenpb LIKE %?1%"
    + "OR p.diachi LIKE %?1%"
    + "OR p.idpb LIKE %?1%")
    public List<Department> findAllDepartments(String keyword);
}
