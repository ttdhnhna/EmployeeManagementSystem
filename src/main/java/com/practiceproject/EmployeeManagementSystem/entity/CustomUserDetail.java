package com.practiceproject.EmployeeManagementSystem.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
// import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * CustomUserDetail
 */

public class CustomUserDetail implements UserDetails{

    User user;

    public CustomUserDetail(User user) {
        this.user = user;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Collection<User> users=new ArrayList<>();
        // return users.stream().map(user->new SimpleGrantedAuthority(user.getRole().toString())).collect(Collectors.toList());
        //Đoạn ở trên có thể sẽ chỉ cần thay đổi getEmail bằng getRole
        
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
        return authorities;
         
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}