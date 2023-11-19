package com.practiceproject.EmployeeManagementSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.practiceproject.EmployeeManagementSystem.entity.UserRegistrationDto;
import com.practiceproject.EmployeeManagementSystem.service.UserService;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
    
    private UserService service;

    public UserRegistrationController(UserService service) {
        super();
        this.service = service;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto(){
        return new UserRegistrationDto();
    }
    @GetMapping
    public String showRegistrationFomr(){
        return "registration";
    }

    @PostMapping
    public String registerUserAcc(@ModelAttribute("user") UserRegistrationDto registrationDto){
        service.save(registrationDto);
        return "redirect: /registration?success";
    }
}
