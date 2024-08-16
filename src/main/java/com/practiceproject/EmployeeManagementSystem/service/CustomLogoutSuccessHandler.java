package com.practiceproject.EmployeeManagementSystem.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.practiceproject.EmployeeManagementSystem.entity.AuditLog;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.entity.AuditLog.Act;
import com.practiceproject.EmployeeManagementSystem.repository.AuditLogRepository;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler{
    @Autowired
    private AuditLogRepository aRepository;
    @Autowired
    private AccountService uService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        if(authentication!=null && authentication.getName()!=null){
            User user = uService.getUserByEmail(authentication.getName());

            AuditLog auditLog = new AuditLog();
            auditLog.setIduser(user);
            auditLog.setAct(Act.LOGOUT);
            aRepository.save(auditLog);
        }
        response.sendRedirect("/login?logout");
    }
    
}
