package com.practiceproject.EmployeeManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.repository.UserRepository;

@Service
public class AppService{
    @Autowired
    UserRepository repository;

    public void saveUser(User user){
        this.repository.save(user);
    }
}
