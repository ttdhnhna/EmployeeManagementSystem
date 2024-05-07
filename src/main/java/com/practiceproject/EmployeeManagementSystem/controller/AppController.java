package com.practiceproject.EmployeeManagementSystem.controller;


import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

    //Thư viện giúp gửi email
    @Autowired
    JavaMailSender mailSender;

    @GetMapping("/registration")
    public String registerPage(Model model){
        model.addAttribute("user",new User());
        return "registration";
    }
    @PostMapping("/saveRegistration")
    public String saveRegistration(@ModelAttribute("user") User user, Model model){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String ePass=encoder.encode(user.getPassword());
        user.setPassword(ePass);
        this.repository.save(user);
        model.addAttribute("successRegismess", "Bạn đã đăng ký tài khoản thành công!");
        return "login";
    }

    @GetMapping("/addAccount")
    public String newAccount(Model model){
        model.addAttribute("user",new User());
        return "newaccount";
    }

    @PostMapping("/saveNewAccount")
    public String saveNewAccount(@ModelAttribute("user") User user){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String ePass=encoder.encode(user.getPassword());
        user.setPassword(ePass);
        this.repository.save(user);
        return "redirect:/addAccount?success";
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
    public String updateAccount( @PathVariable(value = "id")long id, Model model){
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
        User user=service.getUserByID(id);
        model.addAttribute("user", user);
        return "changepassword";
    }

    @PostMapping("/savechangePassword")
    public String savechangePassword(HttpServletRequest request, @ModelAttribute("user") User user, Model model){
        String currentpass = request.getParameter("currentpassword");
        String newpass = request.getParameter("newpassword");
        String confirmpass = request.getParameter("confirmpassword");
        service.changePassword(currentpass, newpass, confirmpass, user);
        service.saveAccount(user);
        model.addAttribute("message", "Bạn đã thay đổi mật khẩu thành công cho tài khoản có ID: " + user.getIduser() + "!");
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
        String token = RandomString.make(50);
        try {
            service.updateResetPass(token, email);
            String reserPasswordLink = Utility.getSiteUrl(request) + "/resetpassword?token=" + token;
            //Chức năng gửi email
            sendEmail(email, reserPasswordLink);   
            model.addAttribute("message", "Chúng tôi đã gửi đường link để reset mật khẩu tới email của bạn! Vui lòng kiểm tra.");
        } catch (CustomerNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        } catch (UnsupportedEncodingException e) {
            model.addAttribute("error", "Lỗi trong quá trình gửi email");
        } catch (MessagingException e) {
            model.addAttribute("error", "Lỗi trong quá trình gửi email");
        }
        return "forgotpassword";
    }
    private void sendEmail(String email, String resetPasswordLink) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("19a10010039@students.hou.edu.vn", "EMS Support");
        helper.setTo(email);

        String subject = "Đây là đường link để reset lại mật khẩu của bạn!";
        String content = "<p>Xin chào,</p>"
        + "<p>Bạn đã yêu cầu reset mật khẩu của bạn.</p>"
        + "<p>Nhấn vào đường link bên dưới để thay đổi mật khẩu của bạn:</p>"
        + "<p><a href=\"" + resetPasswordLink + "\">Thay đổi mật khẩu</a></p>"
        + "<br>"
        + "<p>Bỏ qua email này nếu bạn vẫn nhớ mật khẩu, "
        + "hoặc là bạn không gửi yêu cầu.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }
    @GetMapping("/resetpassword")
    public String showResetPassForm(@Param(value = "token")String token, Model model){
        User user = service.get(token);
        if(user == null){
            model.addAttribute("error", "Token không hợp lê!");
        }
        model.addAttribute("token", token);
        return "resetpassword";
    }
    @PostMapping("/upresetpassword")
    public String processResetPass(HttpServletRequest request, Model model){
        String token = request.getParameter("token");
        String pass = request.getParameter("password");
        
        User user = service.get(token);
        if(user == null){
            model.addAttribute("error", "Token không hợp lê!");
        }else{
            service.updatePassword(user, pass);
            model.addAttribute("message", "Bạn đã thay đổi mật khẩu thành công!");
        }
        return "login";
    }
}
