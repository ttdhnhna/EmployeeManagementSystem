package com.practiceproject.EmployeeManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.practiceproject.EmployeeManagementSystem.entity.Employee;
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
    @GetMapping("/addEmployee")
    public String addEmployee(Model model){
        Employee employee=new Employee();
        model.addAttribute("employee", employee);
        return "newemployee";
    }
    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee){
        //Luu vao csdl
        service.saveEmployee(employee);
        return "redirect:/";
    }
    @GetMapping("/updateEmployee/{id}")
    public String updateEmployee(@PathVariable(value = "id") long id, Model model){
        //Lay du lieu nhan vien tu service
        Employee employee=service.getEmployeebyID(id);
        model.addAttribute("employee", employee);
        return "updateemployee"; 
    }
    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable(value = "id") long id){
        this.service.deleteEmployeebyID(id);
        return "redirect:/";
    }
    //Sắp xếp

}
