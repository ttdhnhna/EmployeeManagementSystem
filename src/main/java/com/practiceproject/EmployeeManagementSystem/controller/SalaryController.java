package com.practiceproject.EmployeeManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.practiceproject.EmployeeManagementSystem.entity.AuditLog;
import com.practiceproject.EmployeeManagementSystem.entity.Salary;
import com.practiceproject.EmployeeManagementSystem.service.AuditLogService;
import com.practiceproject.EmployeeManagementSystem.service.SalaryService;
import com.practiceproject.EmployeeManagementSystem.service.Utility;

@Controller
public class SalaryController {
    @Autowired
    SalaryService service;
    @Autowired
    AuditLogService aService;
    @Autowired
    MessageSource messageSource;

    @GetMapping("/salaries")
    public String getSalaries(Model model){
        return findPaginated(1, "idluong", "asc", model);
    }

    @PostMapping("/saveSalary")
    public String saveSalary(@ModelAttribute("salary") Salary salary){
        service.saveSalary(salary);
        return "redirect:/salaries";
    }

    @PostMapping("/updateSalary")
    public String updateSalary(@ModelAttribute("salary") Salary salary){
        service.updateSalary(salary);
        return "redirect:/salaries";
    }

    // @GetMapping("/addSalary")
    // public String addSalary(Model model){
    //     Salary salary=new Salary();
    //     model.addAttribute("salary", salary);
    //     return "newsalary";
    // }

    @GetMapping("/updateSalary/{id}")
    public String updateEmployee(@PathVariable(value = "id") long id, Model model){
        List<AuditLog> ListLogs = aService.getListLogs();
        model.addAttribute("ListLogs", ListLogs); 
        int unreadCount = aService.getUnreadLog(Utility.getCurrentUserId());
        model.addAttribute("unreadCount", unreadCount);
        Salary salary=service.getSalaryID(id);
        model.addAttribute("salary", salary);
        return "updatesalary";
    }

    // @GetMapping("/deleteSalary/{id}")
    // public String deleteSalary(@PathVariable(value = "id") long id){
    //     this.service.deleteSalarybyID(id);
    //     return "redirect:/salaries";
    // }

    @GetMapping("/pageSalary/{pageSalaryNo}")
    public String findPaginated(@PathVariable(value = "pageSalaryNo") int pageNo,
    @RequestParam("sortField") String sortField,
    @RequestParam("sortDir") String sortDir, Model model){
       int pageSize=10;
       Long iduser = Utility.getCurrentUserId();

       Page<Salary> page=service.findPaginated(pageNo, pageSize, sortField, sortDir, iduser);
       List<Salary> ListSalaries= page.getContent();
       List<AuditLog> ListLogs = aService.getListLogs();
       int unreadCount = aService.getUnreadLog(iduser);

       model.addAttribute("ListLogs", ListLogs); 
       model.addAttribute("currentPage", pageNo);
       model.addAttribute("totalPages", page.getTotalPages());
       model.addAttribute("totalItems", page.getTotalElements());

       model.addAttribute("sortField", sortField);
       model.addAttribute("sortDir", sortDir);
       model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

       model.addAttribute("ListSalaries", ListSalaries);
       model.addAttribute("isSearch", false); 
       model.addAttribute("unreadCount", unreadCount);

       return "salariespage";
   }
    //Tìm kiếm
    @GetMapping("/findSalary")
    public String findSalaries(Model model, @Param("keyword") String keyword){
        Long iduser = Utility.getCurrentUserId();
        List<Salary> ListSalaries=service.findAllSalaries(keyword, iduser);
        List<AuditLog> ListLogs = aService.getListLogs();
        int unreadCount = aService.getUnreadLog(iduser);
        String mess = messageSource.getMessage("cantfindluong", null, LocaleContextHolder.getLocale());
        
        model.addAttribute("ListLogs", ListLogs); 
        model.addAttribute("ListSalaries", ListSalaries);
        model.addAttribute("isSearch", true); 
        model.addAttribute("unreadCount", unreadCount);

        if(ListSalaries.isEmpty()){
            model.addAttribute("errorMess", mess);
        }
        return "salariespage";
    } 
}
