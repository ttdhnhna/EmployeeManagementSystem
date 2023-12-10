package com.practiceproject.EmployeeManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
        return findPaginated(1, model);
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

    @GetMapping("/updateDepartment/{id}")
    public String updateDepartment(@PathVariable(value = "id") long id, Model model){
        Department department=service.getDepartmentID(id);
        model.addAttribute("department", department);
        return "updatedepartment";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo")int pageNo, Model model){
        int pageSize=10;

        Page<Department> page=service.findPaginated(pageNo, pageSize);
        List<Department> ListDepartments=page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("ListDepartments", ListDepartments);
        return "departmentspage";
    }
}
