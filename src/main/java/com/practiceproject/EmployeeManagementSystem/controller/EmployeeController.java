package com.practiceproject.EmployeeManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.practiceproject.EmployeeManagementSystem.service.EmployeeService;

@Controller//Thay bang controller vi restcontroller thi khong hien dc trang chu
public class EmployeeController {
    @Autowired 
    //Điều này có nghĩa là ta sẽ lấy được bean đc tạo tự động bởi Spring
    EmployeeService service;
    //Hiển thị trang chủ
    @GetMapping("/")
    // Điều này có nghĩa là phương thức này sẽ được thực hiện khi người dùng gửi yêu cầu GET tới '/'
	// Trong TH này là: "http://localhost:8080/" (Trang chủ)
    public String getEmployees(Model model){
        model.addAttribute("ListEmployees", service.getEmployees());
        return "homepage";
    }
}
