package com.practiceproject.EmployeeManagementSystem.service;

import com.practiceproject.EmployeeManagementSystem.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.entity.AuditLog.Act;

@Component
public class LoginListener implements ApplicationListener<AuthenticationSuccessEvent>{
    @Autowired
    private EntityChangesService eService;
    @Autowired
    private AccountService uService;
    @Autowired
    private LoginAccountService aService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        Account account = aService.getAccountByEmail(authentication.getName());
        User user = uService.getAccountByEmail(authentication.getName());

        eService.logAuditOperation(user, null, null, null, Act.LOGIN);
    }
}
