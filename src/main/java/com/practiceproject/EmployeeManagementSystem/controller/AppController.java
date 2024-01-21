package com.practiceproject.EmployeeManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.repository.UserRepository;

@Controller
public class AppController {
    @Autowired
    private UserRepository repository;
    @GetMapping("/registration")
    public String regisPage(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }
    @PostMapping("/saveRegistration")
    public String saveRegistration(@ModelAttribute("user") User user){
        this.repository.save(user);
        return "redirect:/";
    }
}
