package com.practiceproject.EmployeeManagementSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practiceproject.EmployeeManagementSystem.repository.UserRepository;
import com.practiceproject.EmployeeManagementSystem.entity.User;


@Service
public class UserService {
    @Autowired
    UserRepository repository;

    public List<User> getAccounts(){
        return repository.findAll();
    }
}
