package com.practiceproject.EmployeeManagementSystem.controller;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.practiceproject.EmployeeManagementSystem.entity.AuditLog;
import com.practiceproject.EmployeeManagementSystem.entity.Account;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.repository.UserRepository;
import com.practiceproject.EmployeeManagementSystem.service.AccountService;
import com.practiceproject.EmployeeManagementSystem.service.AuditLogService;
import com.practiceproject.EmployeeManagementSystem.service.CustomerNotFoundException;
import com.practiceproject.EmployeeManagementSystem.service.LoginAccountService;
import com.practiceproject.EmployeeManagementSystem.service.Utility;

import net.bytebuddy.utility.RandomString;


@Controller
public class AppController {
    @Autowired
    UserRepository repository;
    @Autowired
    AccountService service;
    @Autowired
    LoginAccountService lService;
    @Autowired
    AuditLogService aService;
    @Autowired
    MessageSource messageSource;

    //Thư viện giúp gửi email
    @Autowired
    JavaMailSender mailSender;

    @GetMapping("/registration")
    public String registerPage(Model model){
        model.addAttribute("account",new Account());
        return "registration";
    }

    @PostMapping("/saveRegistration")
    public String saveRegistration(@ModelAttribute("account") Account account, 
    @RequestParam(value = "hoten") String hoten, Model model){
        String mess = messageSource.getMessage("regissuccessful", null, LocaleContextHolder.getLocale());
        try {
            System.out.println("Dang test dang ky tai khoan");
            lService.saveRegistration(account, null);
            model.addAttribute("successRegismess", mess);
            return "login";
        }catch (IllegalStateException e){
            model.addAttribute("alertMessage", e.getMessage());
            return "registration";
        }
    }

    @GetMapping("/regisEmpAcc/{id}")
    public String regisEmpAcc(@PathVariable(value = "id") long id, Model model){
        List<AuditLog> ListLogs = aService.getListLogs();
        model.addAttribute("ListLogs", ListLogs); 
        int unreadCount = aService.getUnreadLog(Utility.getCurrentUserId());
        model.addAttribute("unreadCount", unreadCount);
        model.addAttribute("account", new Account());
        model.addAttribute("idnv", id);
        return "regisempacc";
    }

    @PostMapping("/saveRegisEmpAcc")
    public String saveRegisEmpAcc(@ModelAttribute("account") Account account, 
    @RequestParam(value = "idnv") long id,
    Model model, RedirectAttributes redirectAttributes) throws IOException{
        try {
            if (account!=null) {
                lService.createEmpAccount(account, id); 
            }
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("alertMessage", e.getMessage());
            return "redirect:/regisEmpAcc/"+id;
        }
        return "redirect:/";
    }

    @GetMapping("/manager/login")
    public String login(){
        return "login";
    }

    @GetMapping("/employee/login")
    public String loginemployee(){
        return "employeeacclogin";
    }

    @GetMapping("/employee/home")
    public String employePage(Model model){
        return "employeehomepage";
    }

    @GetMapping("/accounts")
    public String showAccountPage(Model model){
        Long iduser = Utility.getCurrentUserId();
        List<AuditLog> ListLogs = aService.getListLogs();
        model.addAttribute("ListLogs", ListLogs); 
        User user = service.getUserByID(iduser);
        model.addAttribute("user", user);
        int unreadCount = aService.getUnreadLog(Utility.getCurrentUserId());
        model.addAttribute("unreadCount", unreadCount);
        return "accountspage";
    }

    @GetMapping("/updateAccount/{id}")
    public String updateAccount( @PathVariable(value = "id")long id, Model model){
        List<AuditLog> ListLogs = aService.getListLogs();
        model.addAttribute("ListLogs", ListLogs); 
        int unreadCount = aService.getUnreadLog(Utility.getCurrentUserId());
        model.addAttribute("unreadCount", unreadCount);
        User user=service.getUserByID(id);
        model.addAttribute("user", user);
        return "updateaccount";
    }

    @PostMapping("/saveAccount")
    public String saveAccount(@ModelAttribute("user") User user){
        service.saveAccount(user);
        return "redirect:/accounts";
    }

    @GetMapping("/changePassword/{id}")
    public String changePassword(@PathVariable(value = "id")long id, Model model){
        List<AuditLog> ListLogs = aService.getListLogs();
        model.addAttribute("ListLogs", ListLogs); 
        int unreadCount = aService.getUnreadLog(Utility.getCurrentUserId());
        model.addAttribute("unreadCount", unreadCount);
        User user=service.getUserByID(id);
        model.addAttribute("user", user);
        return "changepassword";
    }

    @PostMapping("/savechangePassword")
    public String savechangePassword(@RequestParam(value = "currentpassword", required = false) String currentpass,
    @RequestParam(value = "newpassword") String newpass,
    @RequestParam(value = "confirmpassword") String confirmpass,
    @ModelAttribute("user") User user, RedirectAttributes redirect,
    Model model){
        User fullUser = service.getUserByID(user.getIduser());
        String mess = messageSource.getMessage("changepasssuccessful", null, LocaleContextHolder.getLocale());
        try {
            lService.changePassword(currentpass, newpass, confirmpass, fullUser);
            redirect.addFlashAttribute("message", mess);
            return "redirect:/accounts";
        } catch (IllegalStateException | IllegalArgumentException e) {
            redirect.addFlashAttribute("alertMessage", e.getMessage());
            redirect.addFlashAttribute("currentpassword", currentpass);
            redirect.addFlashAttribute("newpassword", newpass);
            redirect.addFlashAttribute("confirmpassword", confirmpass);
            redirect.addFlashAttribute("user", user);
            return "redirect:/changePassword/"+ user.getIduser() ; 
        }
    }

    //Chức năng quên mật khẩu
    @GetMapping("/forgotpassword")
    public String showForgotPassForm(Model model){
        return "forgotpassword";
    }
    //Quá trình tạo random token cho email 
    @PostMapping("/upforgotpassword")
    public String ProcessforgotPasswordFrom(HttpServletRequest request, Model model){
        String email = request.getParameter("email");
        String token = RandomString.make(50);
        String mess1 = messageSource.getMessage("sentlinkemail", null, LocaleContextHolder.getLocale());
        String mess2 = messageSource.getMessage("errorsendemail", null, LocaleContextHolder.getLocale());
        try {
            lService.updateResetPass(token, email);
            String reserPasswordLink = Utility.getSiteUrl(request) + "/resetpassword?token=" + token;
            //Chức năng gửi email
            lService.sendEmail(email, reserPasswordLink);   
            model.addAttribute("message", mess1);
        } catch (CustomerNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        } catch (UnsupportedEncodingException e) {
            model.addAttribute("error", mess2);
        } catch (MessagingException e) {
            model.addAttribute("error", mess2);
        }
        return "forgotpassword";
    }

    @GetMapping("/resetpassword")
    public String showResetPassForm(@Param(value = "token")String token, Model model){
        Account acc = lService.get(token);
        String mess = messageSource.getMessage("errortoken", null, LocaleContextHolder.getLocale());
        if(acc == null){
            model.addAttribute("error", mess);
        }
        model.addAttribute("token", token);
        return "resetpassword";
    }

    @PostMapping("/upresetpassword")
    public String processResetPass(HttpServletRequest request, Model model){
        String token = request.getParameter("token");
        String pass = request.getParameter("password");
        String mess1 = messageSource.getMessage("errortoken", null, LocaleContextHolder.getLocale());
        String mess2 = messageSource.getMessage("changepasssuccessful", null, LocaleContextHolder.getLocale());
        
        Account acc = lService.get(token);
        if(acc == null){
            model.addAttribute("error", mess1);
        }else{
            lService.updatePassword(acc, pass);
            model.addAttribute("message", mess2);
        }
        return "login";
    }
    
}
