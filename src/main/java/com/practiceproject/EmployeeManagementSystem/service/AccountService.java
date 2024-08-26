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

import com.practiceproject.EmployeeManagementSystem.repository.UserRepository;
import com.practiceproject.EmployeeManagementSystem.entity.AuditLog;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.entity.AuditLog.Act;

@Service
public class AccountService {
    @Autowired
    UserRepository repository;

    @Autowired
    AuditLogService aService;

    @Autowired
    JavaMailSender mailSender;

    // @Transactional(readOnly = true)
    // public List<User> getAccounts(){
    //     return repository.findAll();
    // }

    public User getUserByEmail(String email){
        User user=repository.findbyEmail(email);
        if(user==null){
            throw new IllegalStateException("Không tìm thấy tài khoản: "+email);
        }
        return user;
    }

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

    @Transactional
    public void saveAccount(User user){
        User oldUser = getUserByID(user.getIduser());
        User newUser =  this.repository.save(user);
        AuditLog savedLog = aService.updateAuditOperation(user, null, null, null, Act.UPDATE);
        aService.trackChanges(oldUser, newUser, savedLog);
    }

    @Transactional
    public void saveRegistration(User user){
        if(user.equals(null)){
            throw new IllegalStateException("Thông tin không được bỏ trống");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String ePass = passwordEncoder.encode(user.getPassword());
        user.setPassword(ePass);
        User userDto = this.repository.save(user);

        aService.logAuditOperation(userDto, null, null, null, Act.ADD);
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
        User userDto = this.repository.save(user);

        aService.logAuditOperation(userDto, null, null, null, Act.CHANGEPASS);
    }

    public void updateResetPass(String token, String email) throws CustomerNotFoundException{
        User user = repository.findbyEmail(email);
        if(user != null){
            user.setResetPassToken(token);
            repository.save(user);
        }else{
            throw new CustomerNotFoundException ("Không thể tìm thấy người dùng với email: "  + email);
        }
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

        User userDto = this.repository.save(user);

        aService.logAuditOperation(userDto, null, null, null, Act.CHANGEPASS);
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
}
