package com.practiceproject.EmployeeManagementSystem.service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.practiceproject.EmployeeManagementSystem.repository.UserRepository;
import com.practiceproject.EmployeeManagementSystem.entity.AuditLog;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.entity.AuditLog.Act;
import com.practiceproject.EmployeeManagementSystem.entity.User.Role;
import com.practiceproject.EmployeeManagementSystem.entitydto.UserDto;

@Service
public class AccountService {
    @Autowired
    UserRepository repository;
    @Autowired
    EntityChangesService eService;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    MessageSource messageSource;

    // @Transactional(readOnly = true)
    // public List<User> getAccounts(){
    //     return repository.findAll();
    // }

    public User getUserByEmail(String email){
        User user=repository.findbyEmail(email);
        String mess = messageSource.getMessage("cantfindemailacc", new Object[] { email }, LocaleContextHolder.getLocale());
        if(user==null){
            throw new IllegalStateException(mess);
        }
        return user;
    }

    public User getUserByID(long id){
        Optional<User> optional=repository.findById(id);
        User user=null;
        String mess = messageSource.getMessage("cantfindidacc", new Object[] { id }, LocaleContextHolder.getLocale());
        if(optional.isPresent()){
            user=optional.get();
        }else{
            throw new IllegalStateException(mess);
        }
        return user;
    }

    @Transactional
    public void saveAccount(User user){
        UserDto oldUser = getUserDto(getUserByID(user.getIduser()));
        User savedUser =  this.repository.save(user);
        UserDto newUser = getUserDto(savedUser);
        AuditLog savedAuditlog = eService.updateAuditOperation(savedUser, null, null, null, Act.UPDATE);
        eService.trackChanges(oldUser, newUser, savedAuditlog);
    }

    @Transactional
    public void saveRegistration(User user){
        String mess = messageSource.getMessage("musthaveinfo", null, LocaleContextHolder.getLocale());
        String emailExistsMess = messageSource.getMessage("emailexists", null, LocaleContextHolder.getLocale());
        if(user==null){
            throw new IllegalStateException(mess);
        }
        if(repository.findbyEmail(user.getEmail())!=null){
            throw new IllegalStateException(emailExistsMess);
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String ePass = passwordEncoder.encode(user.getPassword());
        user.setPassword(ePass);
        user.setRole(Role.MANAGER);
        User userDto = this.repository.save(user);

        eService.logAuditOperation(userDto, null, null, null, Act.ADD);
    }

    @Transactional
    public void changePassword(String currentpass, String newpass, String comfirm, User user){
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String mess1 = messageSource.getMessage("wrongpass", null, LocaleContextHolder.getLocale());
        String mess2 = messageSource.getMessage("newpassmustdiff", null, LocaleContextHolder.getLocale());

        if(!passwordEncoder.matches(currentpass, user.getPassword())){
            throw new IllegalStateException(mess1);
        }
        if(passwordEncoder.matches(newpass, user.getPassword())){
            throw new IllegalStateException(mess2);
        }
        String encodedPass = passwordEncoder.encode(newpass); 
        user.setPassword(encodedPass);
        User userDto = this.repository.save(user);

        eService.logAuditOperation(userDto, null, null, null, Act.CHANGEPASS);
    }

    public void updateResetPass(String token, String email) throws CustomerNotFoundException{
        User user = repository.findbyEmail(email);
        String mess = messageSource.getMessage("cantfindemailacc", new Object[] { email }, LocaleContextHolder.getLocale());
        if(user != null){
            user.setResetPassToken(token);
            repository.save(user);
        }else{
            throw new CustomerNotFoundException (mess);
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

        eService.logAuditOperation(userDto, null, null, null, Act.CHANGEPASS);
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

    public UserDto getUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setHoten(user.getHoten());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
