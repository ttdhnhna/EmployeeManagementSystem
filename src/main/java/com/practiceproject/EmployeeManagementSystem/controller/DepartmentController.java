package com.practiceproject.EmployeeManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.practiceproject.EmployeeManagementSystem.entity.Department;
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

    @GetMapping("/addDepartment")
    public String addDepartment(Model model){
        Department department=new Department();
        model.addAttribute("department", department);
        return "newdepartment";
    }

    @PostMapping("/saveDepartment")
    public String saveDepartment(@ModelAttribute("department") Department department){
        service.saveDepartment(department); 
        return "redirect:/departments";
    }

    @GetMapping("/deleteDepartment/{id}")
    public String deleteDepartment(@PathVariable(value = "id") long id){
        this.service.deleteDepartmentID(id);
        return "redirect:/departments";
    }
}
