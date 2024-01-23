package com.practiceproject.EmployeeManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.practiceproject.EmployeeManagementSystem.entity.CustomUserDetail;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.repository.UserRepository;

public class CustomUserDetailService implements UserDetailsService{

    @Autowired 
    UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=repository.findbyEmail(email);
        if(user==null){
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetail(user);
    }
    
}
