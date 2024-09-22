package com.practiceproject.EmployeeManagementSystem.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * CustomUserDetail
 */

public class CustomUserDetail implements UserDetails{

    Account account;

    public CustomUserDetail(Account account) {
        this.account = account;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<Account> accs=new ArrayList<>();
        return accs.stream().map(user->new SimpleGrantedAuthority(account.getEmail())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }

    public Long getUserId(){
        return account.getUser().getIduser();
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