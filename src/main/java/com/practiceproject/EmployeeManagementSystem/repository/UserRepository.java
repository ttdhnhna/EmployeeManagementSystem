package com.practiceproject.EmployeeManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practiceproject.EmployeeManagementSystem.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    // User findbyUserName(String name);
}
