package com.practiceproject.EmployeeManagementSystem.service;

import java.util.List;
import java.util.Optional;

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

    public Salary getSalaryID(long id){
        Optional<Salary> optional=repository.findById(id);
        Salary salary=null;
        if(optional.isPresent()){
            salary=optional.get();
        }else{
            throw new RuntimeException("Không tìm thấy id lương: "+id);
        }
        return salary;
    }
}
