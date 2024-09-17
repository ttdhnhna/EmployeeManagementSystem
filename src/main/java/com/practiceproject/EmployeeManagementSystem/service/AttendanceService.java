package com.practiceproject.EmployeeManagementSystem.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.practiceproject.EmployeeManagementSystem.entity.Attendance;
import com.practiceproject.EmployeeManagementSystem.repository.AttendanceRepository;

@Service
public class AttendanceService {
    @Autowired
    AttendanceRepository repository;
    @Autowired
    MessageSource messageSource;

    @Transactional
    public void saveAttendance(Attendance att){
        this.repository.save(att);
    }

    @Transactional 
    public void deleteAttbyID(long id){
        this.repository.deleteById(id);
    }

    public Attendance getAttbyID(long id){
        String mess = messageSource.getMessage("null", null, LocaleContextHolder.getLocale());
        Optional<Attendance> optional = repository.findById(id);
        Attendance att = null;
        if(optional.isPresent()){
            att = optional.get();
        }else{
            throw new IllegalAccessError(mess);
        }
        return att;
    }
}
