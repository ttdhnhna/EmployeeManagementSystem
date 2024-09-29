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

import com.practiceproject.EmployeeManagementSystem.entity.Employee;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.entity.Account;
import com.practiceproject.EmployeeManagementSystem.entity.Account.Role;
import com.practiceproject.EmployeeManagementSystem.entity.AuditLog.Act;
import com.practiceproject.EmployeeManagementSystem.repository.AccountRepository;
import com.practiceproject.EmployeeManagementSystem.repository.EmployeeRepository;
import com.practiceproject.EmployeeManagementSystem.repository.UserRepository;

@Service
public class LoginAccountService {
    @Autowired
    AccountRepository repository;
    @Autowired
    EmployeeRepository eRepository;
    @Autowired
    UserRepository uRepository;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    MessageSource messageSource;
    @Autowired
    EmployeeService eService;
    @Autowired
    EntityChangesService cService;

    @Transactional
    public void saveAccount(Account account){
        this.repository.save(account);
    }

    //Tim kiem dang thieu phan thong bao.
    public Account getAccID(long id){
        Optional<Account> optional = repository.findById(id);
        Account account = null;
        String mess = messageSource.getMessage("null", null, LocaleContextHolder.getLocale());
        if(optional.isPresent()){
            account = optional.get();
        }else{
            throw new IllegalAccessError(mess);
        }
        return account;
    }

    @Transactional
    public void deleteEmpAccbyID(long id){
        this.repository.deleteById(id);
    }

    @Transactional
    public void createEmpAccount(Account account, long id){
        String emailExistsMess = messageSource.getMessage("emailexists", null, LocaleContextHolder.getLocale());
        
        if(account.equals(null)){
            throw new IllegalStateException("null");
        }
        if(repository.findByEmail(account.getEmail()) != null){
            throw new IllegalStateException(emailExistsMess);
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String ePass = passwordEncoder.encode(account.getPassword());
        account.setPassword(ePass);
        Employee idnv = eService.getEmployeebyID(id);
        account.setIdnv(idnv);
        account.setRole(Role.EMPLOYEE);
        Account savedAccount = this.repository.save(account);

        idnv.setIdacc(savedAccount);
        eRepository.save(idnv);
    }

    @Transactional
    public void saveRegistration(Account account, String hoten){
        String mess = messageSource.getMessage("musthaveinfo", null, LocaleContextHolder.getLocale());
        String emailExistsMess = messageSource.getMessage("emailexists", null, LocaleContextHolder.getLocale());
        if(account==null){
            throw new IllegalStateException(mess);
        }
        if(repository.findByEmail(account.getEmail())!=null){
            throw new IllegalStateException(emailExistsMess);
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String ePass = passwordEncoder.encode(account.getPassword());
        account.setPassword(ePass);
        account.setRole(Role.MANAGER);
        Account savedAccount = this.repository.save(account);

        User user = new User();
        user.setEmail(savedAccount.getEmail());
        user.setHoten(hoten);
        user.setIdacc(savedAccount);
        User savedUser = uRepository.save(user);

        savedAccount.setUser(savedUser);
        savedAccount.setIdnv(null);
        this.repository.save(savedAccount);
        
        cService.logAuditOperation(savedUser, null, null, null, Act.ADD);
    }

    @Transactional
    public void changePassword(String currentpass, String newpass, String comfirm, User user){
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String mess1 = messageSource.getMessage("wrongpass", null, LocaleContextHolder.getLocale());
        String mess2 = messageSource.getMessage("newpassmustdiff", null, LocaleContextHolder.getLocale());
        String mess3 = messageSource.getMessage("cantfindemailacc", new Object[] { user.getEmail() }, LocaleContextHolder.getLocale());
        
        Account acc = repository.findByEmail(user.getEmail());
        if(acc == null){
            throw new IllegalStateException(mess3);
        }
        if(!passwordEncoder.matches(currentpass, acc.getPassword())){
            throw new IllegalStateException(mess1);
        }
        if(passwordEncoder.matches(newpass, acc.getPassword())){
            throw new IllegalStateException(mess2);
        }
        String encodedPass = passwordEncoder.encode(newpass); 
        acc.setPassword(encodedPass);
        this.repository.save(acc);
        User userDto = uRepository.save(user);

        cService.logAuditOperation(userDto, null, null, null, Act.CHANGEPASS);
    }

    public void updateResetPass(String token, String email) throws CustomerNotFoundException{
        Account account = repository.findByEmail(email);
        String mess = messageSource.getMessage("cantfindemailacc", new Object[] { email }, LocaleContextHolder.getLocale());
        if(account != null){
            account.setResetPassToken(token);
            repository.save(account);
        }else{
            throw new CustomerNotFoundException (mess);
        }
    }

    public Account get(String resetPassToken){
        return repository.findByResetPassToken(resetPassToken);
    }

    @Transactional
    public void updatePassword(Account account, String newPass){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPass = passwordEncoder.encode(newPass); 

        account.setPassword(encodedPass);
        account.setResetPassToken(null);
        Account savedAcc = this.repository.save(account);

        User userDto = this.repository.findByiduser(savedAcc);

        cService.logAuditOperation(userDto, null, null, null, Act.CHANGEPASS);
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
