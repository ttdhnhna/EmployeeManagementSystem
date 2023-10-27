package com.practiceproject.EmployeeManagementSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practiceproject.EmployeeManagementSystem.entity.Employee;
import com.practiceproject.EmployeeManagementSystem.repository.EmployeeRepository;

@Service
//Được sử dụng với các lớp cung cấp các chức năng business
public class EmployeeService {
    @Autowired //Được sử dụng để tự động Dependency Injection
    private EmployeeRepository repository;//Hay có thể nói cách khác là giống như kế thừa các thuộc tính của lớp EmployeeRepository vào repository  
    //Để có thể sử dụng các chức năng của nó trong service.
    public List<Employee> getEmployees(){
        return repository.findAll();
    }
}
