package com.practiceproject.EmployeeManagementSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.practiceproject.EmployeeManagementSystem.dto.UserRegistrationDto;
import com.practiceproject.EmployeeManagementSystem.service.UserService;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
    
    private UserService service;

    public UserRegistrationController(UserService service) {
        super();
        this.service = service;
    }

    public String registerUserAcc(@ModelAttribute("user") UserRegistrationDto registrationDto){
        service.save(registrationDto);
        return "redirect: /registration?success";
    }
}
