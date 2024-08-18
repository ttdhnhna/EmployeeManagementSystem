package com.practiceproject.EmployeeManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.practiceproject.EmployeeManagementSystem.entity.AuditLog;
import com.practiceproject.EmployeeManagementSystem.service.AuditLogService;
import com.practiceproject.EmployeeManagementSystem.service.Utility;

@Controller
public class AuditlogController {
    @Autowired
    AuditLogService service;

    @GetMapping("/auditlogs")
    public String getLogs(Model model){
        return findPaginated(1, "idlog", "asc", model);
    }

    @GetMapping("/pageLog/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, 
    @RequestParam("sortField") String sortField,
    @RequestParam("sortDir") String sortDir, Model model){
        int pageSize = 20;
        Long iduser = Utility.getCurrentUserId();

        Page<AuditLog> page = service.findPaginated(pageNo, pageSize, sortField, sortDir, iduser);
        List<AuditLog> ListLogs = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("ListLogs", ListLogs); 
        return "auditlogpage";
    }
}