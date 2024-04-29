package com.practiceproject.EmployeeManagementSystem.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.practiceproject.EmployeeManagementSystem.service.AccountService;
import com.practiceproject.EmployeeManagementSystem.service.CustomerNotFoundException;
import com.practiceproject.EmployeeManagementSystem.service.Utility;

import net.bytebuddy.utility.RandomString;


@Controller
public class AppController {
    @Autowired
    UserRepository repository;
    @Autowired
    AccountService service;


    @GetMapping("/registration")
    public String registerPage(Model model){
        model.addAttribute("user",new User());
        return "newaccount";
    }
    @PostMapping("/saveRegistration")
    public String saveRegistration(@RequestParam ("confirm") String confirm, 
    User user){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        if(!user.getPassword().equals(confirm)){
            throw new IllegalStateException("Mật khẩu không trùng khớp");
        }
        String ePass=encoder.encode(user.getPassword());
        user.setPassword(ePass);
        this.repository.save(user);
        return "redirect:/registration?success";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/accounts")
    public String showAccountPage(Model model){
        return findPaginatedAcc(1, "iduser", "asc", model);
    }
    
    @GetMapping("/deleteAccount/{id}")
    public String deleteAccount(@PathVariable(value = "id") long id){
        this.service.deleteAccountById(id);
        return "redirect:/accounts";
    }

    @GetMapping("/updateAccount/{id}")
    public String updateAccount(@PathVariable(value = "id")long id, Model model){
        User user=service.getUserByID(id);
        model.addAttribute("user", user);
        return "updateaccount";
    }

    @PostMapping("/saveAccount")
    public String saveAccount(@ModelAttribute("user") User user){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String ePass=encoder.encode(user.getPassword());
        user.setPassword(ePass);
        service.saveAccount(user);
        return "redirect:/accounts";
    }

    @GetMapping("/finduser")
    public String findUsers(Model model, @Param("keyword") String keyword){
        List<User> ListAccounts=service.findAllUsers(keyword);
        model.addAttribute("ListAccounts", ListAccounts);
        return "accountspage";
    }

    @GetMapping("/pageAcc/{pageAccNo}")
    public String findPaginatedAcc(@PathVariable(value = "pageAccNo") int pageNo,
    @RequestParam("sortField") String sortField,
    @RequestParam("sortDir") String sortDir, Model model){
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
    @PostMapping("/changePassword")
    public String changePassword(@RequestParam(value = "currentpassword", required = false) String currpass
    , @RequestParam(value = "newpassword", required = false) String newpass
    , @RequestParam(value = "confirmpassword", required = false) String confirm
    , @ModelAttribute("user") User user
    ){
        this.service.changePassword(currpass, newpass, confirm, user);
        return "redirect:/accounts";
    }
    @GetMapping("/changePassword/{id}")
    public String changePassword(@PathVariable(value = "id")long id, Model model){
        User user=service.getUserByID(id);
        model.addAttribute("user", user);
        return "changepassword";
    }

    //Chức năng quên mật khẩu

    @GetMapping("/forgotpassword")
    public String showForgotPassForm(Model model){
        model.addAttribute("pageTitle", "Forgot Password");
        return "forgotpassword";
    }
    //Quá trình tạo random token cho email 
    @PostMapping("/upforgotpassword")
    public String ProcessforgotPasswordFrom(HttpServletRequest request, Model model){
        String email = request.getParameter("email");
        String token = RandomString.make(50);//Cần xem lại trong csdl là mình để nvarchar bao nhiêu rồi thay đổi ở đây.
        // System.out.println(email);
        // System.out.println(token);
        try {
            service.updateResetPass(token, email);
            String reserPasswordLink = Utility.getSiteUrl(request) + "/resetpassword?token=" + token;
            System.out.println(reserPasswordLink);
            //Gửi email
        } catch (CustomerNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "forgotpassword";
    }
}
