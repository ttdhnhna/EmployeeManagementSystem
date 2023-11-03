package com.practiceproject.EmployeeManagementSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practiceproject.EmployeeManagementSystem.entity.Department;
import com.practiceproject.EmployeeManagementSystem.repository.DepartmentRepository;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository repository;
    public List<Department> getDepartments(){
        return repository.findAll();
    }
}
