package com.practiceproject.EmployeeManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practiceproject.EmployeeManagementSystem.service.EmployeeService;

@RestController
public class EmployeeController {
    @Autowired 
    private EmployeeService service;
    //Hiển thị trang chủ
    @GetMapping("/")
    public String getEmployees(Model model){
        model.addAttribute("ListEmployees", service.getEmployees());
        return "homepage";
    }
}
