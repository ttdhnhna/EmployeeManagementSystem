package com.practiceproject.EmployeeManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practiceproject.EmployeeManagementSystem.entity.EmployeeAccount;

@Repository
public interface EmpAccountRepository extends JpaRepository<EmployeeAccount, Long>{
    
}
