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
import org.springframework.web.bind.annotation.RequestParam;

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
        return "salariespage";
    }

    @GetMapping("/addSalary")
    public String addSalary(Model model){
        Salary salary=new Salary();
        model.addAttribute("salary", salary);
        return "newsalary";
    }

    @GetMapping("/updateSalary/{id}")
    public String updateEmployee(@PathVariable(value = "id") long id, Model model){
        Salary salary=service.getSalaryID(id);
        model.addAttribute("salary", salary);
        return "updatesalary";
    }

    @GetMapping("/deleteSalary/{id}")
    public String deleteSalary(@PathVariable(value = "id") long id){
        this.service.deleteSalarybyID(id);
        return "salariespage";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
    @RequestParam("sortField") String sortField,
    @RequestParam("sortDir") String sortDir, Model model){
        int pageSize=10;

        Page<Salary> page=service.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Salary> ListSalaries=page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("ListSalaries", ListSalaries);
        return "salariespage";
    }
}
