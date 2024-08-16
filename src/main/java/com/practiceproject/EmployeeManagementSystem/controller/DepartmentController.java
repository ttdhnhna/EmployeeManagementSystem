package com.practiceproject.EmployeeManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.practiceproject.EmployeeManagementSystem.entity.Department;
import com.practiceproject.EmployeeManagementSystem.entity.Employee;
import com.practiceproject.EmployeeManagementSystem.service.DepartmentService;
import com.practiceproject.EmployeeManagementSystem.service.Utility;

@Controller
public class DepartmentController {
    @Autowired
    DepartmentService service;

    @GetMapping("/departments")
    public String getDepartments(Model model){
        return findPaginated(1,"idpb", "asc", model);
    } 

    @GetMapping("/addDepartment")
    public String addDepartment(Model model){
        Department department=new Department();
        model.addAttribute("department", department);
        return "newdepartment";
    }

    @PostMapping("/saveDepartment")
    public String saveDepartment(@ModelAttribute("department") Department department){
        service.saveDepartment(department); 
        return "redirect:/departments";
    }

    @PostMapping("/updateDepartment")
    public String updateDepartment(@ModelAttribute("department") Department department){
        service.updateDepartment(department); 
        return "redirect:/departments";
    }

    @GetMapping("/deleteDepartment/{id}")
    public String deleteDepartment(@PathVariable(value = "id") long id){
        this.service.deleteDepartmentID(id);
        return "redirect:/departments";
    }

    @GetMapping("/updateDepartment/{id}")
    public String updateDepartment(@PathVariable(value = "id") long id, Model model){
        Department department=service.getDepartmentID(id);
        model.addAttribute("department", department);
        return "updatedepartment";
    }

    @GetMapping("/pageDepartment/{pageDepartmentNo}")
    public String findPaginated(@PathVariable(value = "pageDepartmentNo")int pageNo, 
    @RequestParam("sortField") String sortField,
    @RequestParam("sortDir") String sortDir, Model model){
        int pageSize=10;
        Long iduser = Utility.getCurrentUserId();

        Page<Department> page=service.findPaginated(pageNo, pageSize, sortField, sortDir, iduser);
        List<Department> ListDepartments=page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("ListDepartments", ListDepartments);
        return "departmentspage";
    }
    //Tìm kiếm
    @GetMapping("/finddepartment")
    public String findDepartments(Model model, @Param("keyword") String keyword){
        Long iduser = Utility.getCurrentUserId();
        List<Department> ListDepartments=service.findDepartments(keyword, iduser);
        model.addAttribute("ListDepartments", ListDepartments);
        if(ListDepartments.isEmpty()){
            model.addAttribute("errorMess", "Không tìm thấy phòng ban");
        }
        return "departmentspage";
    }

    //Trang chi tiết phòng ban.
    @GetMapping("/viewDepartmentdetail/{id}")
    public String viewDepartmentdetail(@PathVariable(value = "id") long id, Model model){
        Department department=service.getDepartmentID(id);
        model.addAttribute("department", department);
        List<Employee> ListEmployees=service.getNVInformationbyID(id);
        model.addAttribute("ListEmployees", ListEmployees);
        return "departmentviewprofile";
    }
}
