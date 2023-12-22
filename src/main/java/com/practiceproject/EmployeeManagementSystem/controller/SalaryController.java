package com.practiceproject.EmployeeManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.practiceproject.EmployeeManagementSystem.entity.Salary;
import com.practiceproject.EmployeeManagementSystem.service.SalaryService;

@Controller
public class SalaryController {
    @Autowired
    SalaryService service;

    @GetMapping("/salaries")
    public String getSalaries(Model model){
        List<Salary> ListSalaries=service.getSalaries();
        model.addAttribute("ListSalaries", ListSalaries);
        return "salariespage";
    }
}
