package com.practiceproject.EmployeeManagementSystem.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.practiceproject.EmployeeManagementSystem.entity.Roles;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.entity.UserRegistrationDto;
import com.practiceproject.EmployeeManagementSystem.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository repository; 
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository repository) {
        super();
        this.repository = repository;
    }
    //Luu nguoi dung
    @Override
    public User save(UserRegistrationDto registrationDto) {
        User user=new User(registrationDto.getName(), bCryptPasswordEncoder.encode(registrationDto.getPassword()), 
        Arrays.asList(new Roles("ROLE_USER")) , registrationDto.getEmail());
        
        return repository.save(user);
    }
    //?
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=repository.findbyUserName(username);
        if(user==null){
            throw new UsernameNotFoundException("Sai tên đăng nhập hoặc mật khẩu");
        }
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Roles> collection){
        return collection.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
