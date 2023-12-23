package com.practiceproject.EmployeeManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PostMapping("/saveSalary")
    public String saveSalary(@ModelAttribute("salary") Salary salary){
        service.saveSalary(salary);
        return "redirect: /salaries";
    }

    @GetMapping("/addSalary")
    public String addSalary(Model model){
        Salary salary=new Salary();
        model.addAttribute("salary", salary);
        return "newsalary";
    }
}
