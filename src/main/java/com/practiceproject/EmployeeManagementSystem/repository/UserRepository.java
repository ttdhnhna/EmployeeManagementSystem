package com.practiceproject.EmployeeManagementSystem.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.practiceproject.EmployeeManagementSystem.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
    @Query("SELECT u FROM User u WHERE u.email=?1")
    User findbyEmail(String email);
    @Query("SELECT u FROM User u WHERE u.hoten LIKE %?1%"
    + "OR u.iduser LIKE %?1%"
    + "OR u.email LIKE %?1%"
    + "OR u.role LIKE %?1%")
    public List<User> findAllUsers(String keywords);
}
