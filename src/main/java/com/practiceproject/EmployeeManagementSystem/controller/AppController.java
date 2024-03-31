package com.practiceproject.EmployeeManagementSystem.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.repository.UserRepository;
import com.practiceproject.EmployeeManagementSystem.service.UserService;

@Controller
public class AppController {
    @Autowired
    UserRepository repository;
    @Autowired
    UserService service;

    @GetMapping("/admin/registration")
    public String registerPage(Model model){
        model.addAttribute("user",new User());
        return "registration";
    }
    @PostMapping("/admin/saveRegistration")
    public String saveRegistration(User user){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String ePass=encoder.encode(user.getPassword());
        user.setPassword(ePass);
        this.repository.save(user);
        return "redirect:/admin/registration?success";
    }
    @GetMapping("/user/login")
    public String login(){
        return "login";
    }
    @GetMapping("/login")
    public String loginadmin(){
        return "loginadmin";
    }
    @GetMapping("/user/userpage")
    public String showUserpage(){
        return "userpage";
    }
    @GetMapping("/admin/accounts")
    public String showAccountPage(Model model){
        return findPaginatedAcc(1, "iduser", "asc", model);
    }
    
    @GetMapping("/admin/deleteAccount/{id}")
    public String deleteAccount(@PathVariable(value = "id") long id){
        this.service.deleteAccountById(id);
        return "redirect:/admin/accounts";
    }

    @GetMapping("/admin/updateAccount/{id}")
    public String updateAccount(@PathVariable(value = "id")long id, Model model){
        User user=service.getUserByID(id);
        model.addAttribute("user", user);
        return "updateaccount";
    }

    @PostMapping("/admin/saveAccount")
    public String saveAccount(@ModelAttribute("user") User user){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String ePass=encoder.encode(user.getPassword());
        user.setPassword(ePass);
        service.saveAccount(user);
        return "redirect:/admin/accounts";
    }

    @GetMapping("/finduser")
    public String findUsers(Model model, @Param("keyword") String keyword){
        List<User> ListAccounts=service.findAllUsers(keyword);
        model.addAttribute("ListAccounts", ListAccounts);
        return "accountspage";
    }

    @GetMapping("/admin/pageAcc/{pageAccNo}")
    public String findPaginatedAcc(@PathVariable(value = "pageAccNo") int pageNo,
    @RequestParam("sortAccField") String sortField,
    @RequestParam("sortAccDir") String sortDir, Model model){
        int pageSize=10;

        Page<User> page=service.findPaginatedAcc(pageNo, pageSize, sortField, sortDir);
        List<User> ListAccounts=page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("ListAccounts", ListAccounts);
        return "accountspage";
    }
}
