package com.practiceproject.EmployeeManagementSystem.service;

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

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        User user = uService.getUserByEmail(authentication.getName());

        eService.logAuditOperation(user, null, null, null, Act.LOGIN);
    }
}
