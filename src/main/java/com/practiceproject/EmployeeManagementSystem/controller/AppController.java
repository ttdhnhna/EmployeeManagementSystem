package com.practiceproject.EmployeeManagementSystem.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.repository.UserRepository;
import com.practiceproject.EmployeeManagementSystem.service.UserService;

@Controller
public class AppController {
    @Autowired
    UserRepository repository;
    @Autowired
    UserService service;

    @GetMapping("/registration")
    public String registerPage(Model model){
        model.addAttribute("user",new User());
        return "registration";
    }
    @PostMapping("/saveRegistration")
    public String saveRegistration(User user){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String ePass=encoder.encode(user.getPassword());
        user.setPassword(ePass);
        this.repository.save(user);
        return "redirect:/registration?success";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/loginadmin")
    public String loginadmin(){
        return "loginadmin";
    }
    @GetMapping("/userpage")
    public String showUserpage(){
        return "userpage";
    }
    @GetMapping("/accounts")
    public String showAccountPage(Model model){
        List<User> ListAccounts= service.getAccounts();
        model.addAttribute("ListAccounts", ListAccounts);
        return "accountspage";
    }
    
    @GetMapping("/deleteAccount/{id}")
    public String deleteAccount(@PathVariable(value = "id") long id){
        this.service.deleteAccountById(id);
        return "redirect:/accounts";
    }
}
