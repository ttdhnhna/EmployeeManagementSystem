package com.practiceproject.EmployeeManagementSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practiceproject.EmployeeManagementSystem.entity.Salary;
import com.practiceproject.EmployeeManagementSystem.repository.SalaryRepository;

@Service
public class SalaryService {
    @Autowired
    SalaryRepository repository;

    public List<Salary> getSalaries(){
        return repository.findAll();
    }

    public void saveSalary(Salary salary){
        this.repository.save(salary);
    }
}
