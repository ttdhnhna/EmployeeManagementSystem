package com.practiceproject.EmployeeManagementSystem.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.practiceproject.EmployeeManagementSystem.entity.CustomUserDetail;

public class Utility {
    public static String getSiteUrl(HttpServletRequest request){
        String siteUrl = request.getRequestURL().toString();
        return siteUrl.replace(request.getServletPath(), "");
    }

    public static Long getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null && authentication.getPrincipal() instanceof CustomUserDetail){
            CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
            return userDetail.getUserId();
        }
        return null;
    }
}
