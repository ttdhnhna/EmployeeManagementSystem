package com.practiceproject.EmployeeManagementSystem.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class demo1 {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String rPass="123456";
        String encodePass=encoder.encode(rPass);
        System.out.println(encodePass);
    }
}
