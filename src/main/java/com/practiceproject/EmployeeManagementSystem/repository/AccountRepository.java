package com.practiceproject.EmployeeManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practiceproject.EmployeeManagementSystem.entity.User;

public interface AccountRepository extends JpaRepository<User, Long>{
    
}
