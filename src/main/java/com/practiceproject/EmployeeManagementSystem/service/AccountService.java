package com.practiceproject.EmployeeManagementSystem.service;

import java.io.UnsupportedEncodingException;
// import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.practiceproject.EmployeeManagementSystem.repository.AuditLogRepository;
import com.practiceproject.EmployeeManagementSystem.repository.UserRepository;
import com.practiceproject.EmployeeManagementSystem.entity.AuditLog;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.entity.AuditLog.Act;

@Service
public class AccountService {
    @Autowired
    UserRepository repository;

    @Autowired
    AuditLogRepository aRepository;

    @Autowired
    JavaMailSender mailSender;

    // @Transactional(readOnly = true)
    // public List<User> getAccounts(){
    //     return repository.findAll();
    // }

    public User getUserByID(long id){
        Optional<User> optional=repository.findById(id);
        User user=null;
        if(optional.isPresent()){
            user=optional.get();
        }else{
            throw new IllegalStateException("Không tìm thấy id tài khoản: "+id);
        }
        return user;
    }

    public void saveAccount(User user){
        this.repository.save(user);
    }

    @Transactional
    public void saveRegistration(User user){
        if(user.equals(null)){
            throw new IllegalStateException("Thông tin không được bỏ trống");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String ePass = passwordEncoder.encode(user.getPassword());
        user.setPassword(ePass);
        this.repository.save(user);

        logAuditOperation(user, Act.ADD);
    }

    @Transactional
    public void changePassword(String currentpass, String newpass, String comfirm, User user){
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        if(!passwordEncoder.matches(currentpass, user.getPassword())){
            throw new IllegalStateException("Sai mật khẩu");
        }
        if(passwordEncoder.matches(newpass, user.getPassword())){
            throw new IllegalStateException("Mật khẩu mới phải khác mật khẩu cũ");
        }
        String encodedPass = passwordEncoder.encode(newpass); 
        user.setPassword(encodedPass);
        repository.save(user);

        logAuditOperation(user, Act.UPDATE);
    }

    @Transactional
    public void updateResetPass(String tokem, String email) throws CustomerNotFoundException{
        User user = repository.findbyEmail(email);
        if(user != null){
            user.setResetPassToken(tokem);
            repository.save(user);
        }else{
            throw new CustomerNotFoundException ("Không thể tìm thấy người dùng với email: "  + email);
        }
        logAuditOperation(user, Act.UPDATE);
    }

    public User get(String resetPassToken){
        return repository.findByResetPassToken(resetPassToken);
    }

    @Transactional
    public void updatePassword(User user, String newPass){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPass = passwordEncoder.encode(newPass); 

        user.setPassword(encodedPass);
        user.setResetPassToken(null);

        repository.save(user);

        logAuditOperation(user, Act.UPDATE);
    }

    public void sendEmail(String email, String resetPasswordLink) throws UnsupportedEncodingException, MessagingException {
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

    public void logAuditOperation(User user, Act action){
        AuditLog auditLog = new AuditLog();
        auditLog.setIduser(user);
        auditLog.setAct(action);
        aRepository.save(auditLog);
    }
}
