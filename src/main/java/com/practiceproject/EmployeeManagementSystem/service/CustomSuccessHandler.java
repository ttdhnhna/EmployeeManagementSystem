package com.practiceproject.EmployeeManagementSystem.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String redirectUrl = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(role -> role.equals("MANAGER") || role.equals("EMPLOYEE"))
                .findFirst()
                .map(role -> role.equals("MANAGER") ? "/" : "/employee/home")
                .orElse("/login?error");
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    
}