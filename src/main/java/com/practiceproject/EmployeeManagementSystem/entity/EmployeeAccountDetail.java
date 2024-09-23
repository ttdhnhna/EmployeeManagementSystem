package com.practiceproject.EmployeeManagementSystem.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class EmployeeAccountDetail implements UserDetails{
    Account account;

    public EmployeeAccountDetail(Account account){
        this.account=account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> accs = new ArrayList<>();
        if(account.getRole().equals(com.practiceproject.EmployeeManagementSystem.entity.Account.Role.EMPLOYEE)){
            accs.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
        }else if(account.getRole().equals(com.practiceproject.EmployeeManagementSystem.entity.Account.Role.MANAGER)){
            accs.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        }
        return accs;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }

    public Long getAccId(){
        return account.getIdacc();
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
