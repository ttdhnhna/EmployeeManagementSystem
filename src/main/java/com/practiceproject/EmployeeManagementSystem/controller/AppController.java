package com.practiceproject.EmployeeManagementSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.practiceproject.EmployeeManagementSystem.entity.User;

@Controller
public class AppController {
    @GetMapping("/registration")
    public String regisPage(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }
}
