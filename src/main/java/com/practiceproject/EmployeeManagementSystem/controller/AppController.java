package com.practiceproject.EmployeeManagementSystem.controller;


import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        try {
            service.saveRegistration(user);
            model.addAttribute("successRegismess", "Bạn đã đăng ký tài khoản thành công!");
            return "login";
        } catch (IllegalStateException e) {
            model.addAttribute("alertMessage", e.getMessage());
            return "registration";
        }
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/accounts")
    public String showAccountPage(Model model){
        Long iduser = Utility.getCurrentUserId();
        User user = service.getUserByID(iduser);
        model.addAttribute("user", user);
        return "accountspage";
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
    public String savechangePassword(@RequestParam(value = "currentpassword", required = false) String currentpass,
    @RequestParam(value = "newpassword") String newpass,
    @RequestParam(value = "confirmpassword") String confirmpass,
    @ModelAttribute("user") User user, RedirectAttributes redirect,
    Model model){
        User fullUser = service.getUserByID(user.getIduser());
        try {
            service.changePassword(currentpass, newpass, confirmpass, fullUser);
            redirect.addFlashAttribute("message", "Bạn đã thay đổi mật khẩu thành công cho tài khoản có ID: " + fullUser.getIduser() + "!");
            return "redirect:/accounts";
        } catch (IllegalStateException | IllegalArgumentException e) {
            model.addAttribute("alertMessage", e.getMessage());
            return "changepassword"; 
        }
    }

    //Chức năng quên mật khẩu
    @GetMapping("/forgotpassword")
    public String showForgotPassForm(Model model){
        // model.addAttribute("pageTitle", "Forgot Password");
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
