package com.practiceproject.EmployeeManagementSystem.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.practiceproject.EmployeeManagementSystem.entity.AuditLog;
import com.practiceproject.EmployeeManagementSystem.entity.EntityChanges;
import com.practiceproject.EmployeeManagementSystem.service.AuditLogService;
import com.practiceproject.EmployeeManagementSystem.service.Utility;

@Controller
public class AuditlogController {
    @Autowired
    AuditLogService service;
    @Autowired
    MessageSource messageSource;

    @GetMapping("/auditlogs")
    public String getLogs(Model model){
        return findPaginated(1, "idlog", "desc", model);
    }

    @GetMapping("/pageLog/{pageNo}")
    @Transactional
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, 
    @RequestParam("sortField") String sortField,
    @RequestParam("sortDir") String sortDir, Model model){
        int pageSize = 20;
        Long iduser = Utility.getCurrentUserId();

        Page<AuditLog> page = service.findPaginated(pageNo, pageSize, sortField, sortDir, iduser);
        List<AuditLog> ListLogs = page.getContent();
        int unreadCount = service.getUnreadLog(iduser);
//        service.autoDeletelog();

        model.addAttribute("unreadCount", unreadCount);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("ListLogs", ListLogs); 
        model.addAttribute("isSearch", false); 
        return "auditlogpage";
    }

    @GetMapping("/findlog")
    public String findLogs(Model model, @Param("keyword") String keyword){
        Long iduser = Utility.getCurrentUserId();
        List<AuditLog> ListLogs = service.findAll(iduser, keyword);
        int unreadCount = service.getUnreadLog(iduser);
        model.addAttribute("unreadCount", unreadCount);
        model.addAttribute("ListLogs", ListLogs); 
        model.addAttribute("isSearch", true); 
        String mess = messageSource.getMessage("cantfindlog", null, LocaleContextHolder.getLocale());
        
        if(ListLogs.isEmpty()){
            model.addAttribute("errorMess", mess);
        }
        return "auditlogpage";
    }

    @GetMapping("/detaillog/{id}")
    public String detailLog(@PathVariable(value = "id") long id, Model model){
        AuditLog auditLog = service.getLogByID(id);
        auditLog.setRead(true);
        service.saveLog(auditLog);
        List<EntityChanges> entityChanges = service.getDetailLog(auditLog);
        List<AuditLog> ListLogs = service.getListLogs();
        int unreadCount = service.getUnreadLog(Utility.getCurrentUserId());
        model.addAttribute("unreadCount", unreadCount);
        model.addAttribute("ListLogs", ListLogs); 
        model.addAttribute("auditLog", auditLog);
        model.addAttribute("entityChanges", entityChanges);
        return "detaillog";
    }

    @GetMapping("/readall")
    public String readAll(){
        service.readAllLog();
        return "redirect:/auditlogs";
    }
}
