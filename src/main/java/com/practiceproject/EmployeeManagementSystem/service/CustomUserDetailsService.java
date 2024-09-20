package com.practiceproject.EmployeeManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.practiceproject.EmployeeManagementSystem.entity.CustomUserDetail;
import com.practiceproject.EmployeeManagementSystem.entity.Account;
import com.practiceproject.EmployeeManagementSystem.entity.EmployeeAccountDetail;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.repository.AccountRepository;
import com.practiceproject.EmployeeManagementSystem.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    UserRepository repository;
    @Autowired
    AccountRepository eRepository;
    @Autowired
    MessageSource messageSource;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=repository.findbyEmail(username);
        Account acc = eRepository.findByEmail(username);
        String mess = messageSource.getMessage("cantfinduser", null, LocaleContextHolder.getLocale());
        
        if(user!=null){
            return new CustomUserDetail(user);
        }
        if(acc!=null){
            return new EmployeeAccountDetail(acc);
        }
        throw new UsernameNotFoundException(mess);
    }
    
}
