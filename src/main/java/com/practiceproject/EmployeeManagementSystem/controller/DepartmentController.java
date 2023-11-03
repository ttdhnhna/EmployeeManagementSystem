package com.practiceproject.EmployeeManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.practiceproject.EmployeeManagementSystem.service.DepartmentService;

@Controller
public class DepartmentController {
    @Autowired
    DepartmentService service;

    @GetMapping("/departments")
    public String getDepartments(Model model){
        model.addAttribute("ListDepartments", service.getDepartments());
        return "departmentspage";
    } 
}
