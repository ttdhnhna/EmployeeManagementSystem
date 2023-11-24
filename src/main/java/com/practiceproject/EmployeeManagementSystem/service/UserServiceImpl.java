package com.practiceproject.EmployeeManagementSystem.service;

import java.util.Arrays;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.practiceproject.EmployeeManagementSystem.entity.Roles;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.entity.UserRegistrationDto;
import com.practiceproject.EmployeeManagementSystem.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository repository; 

    
    public UserServiceImpl(UserRepository repository) {
        super();
        this.repository = repository;
    }


    @Override
    public User save(UserRegistrationDto registrationDto) {
        User user=new User(registrationDto.getName(), registrationDto.getPassword(), 
        Arrays.asList(new Roles("ROLE_USER")) , registrationDto.getEmail());
        
        return repository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
        return null;
    }
    
}
