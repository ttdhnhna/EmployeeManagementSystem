package com.practiceproject.EmployeeManagementSystem.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.practiceproject.EmployeeManagementSystem.repository.UserRepository;
import com.practiceproject.EmployeeManagementSystem.entity.AuditLog;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.entity.AuditLog.Act;
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

    public User getAccountByEmail(String email){
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

    public UserDto getUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setHoten(user.getHoten());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
