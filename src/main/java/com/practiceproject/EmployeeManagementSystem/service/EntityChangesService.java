package com.practiceproject.EmployeeManagementSystem.service;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practiceproject.EmployeeManagementSystem.entity.AuditLog;
import com.practiceproject.EmployeeManagementSystem.entity.EntityChanges;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.entity.AuditLog.Act;
import com.practiceproject.EmployeeManagementSystem.repository.AuditLogRepository;
import com.practiceproject.EmployeeManagementSystem.repository.EntityChangesRepository;

import javax.transaction.Transactional;

@Service
public class EntityChangesService {
    @Autowired 
    EntityChangesRepository repository;
    @Autowired
    AuditLogRepository aRepository;

    public void trackChanges(Object oldEntity, Object newEntity, AuditLog idlog){
        Class<?> clasz = oldEntity.getClass();
        Field[] fields = clasz.getDeclaredFields();

        for(Field field:fields){
            field.setAccessible(true);
            try {
                Object oldValue = field.get(oldEntity);
                Object newValue = field.get(newEntity);
                
                if(oldValue!=null && !oldValue.equals(newValue)){  
                    EntityChanges entityChanges = new EntityChanges();        
                    entityChanges.setEntityType(clasz.getSimpleName());
                    entityChanges.setFieldName(field.getName());
                    entityChanges.setOldValue(oldValue.toString());
                    entityChanges.setNewValue(newValue.toString());
                    entityChanges.setIdlog(idlog);
                    repository.save(entityChanges);
                }
            } catch (IllegalAccessException e) {
                 e.printStackTrace();
            }
        }
    }

    public void logAuditOperation(User user, Long employee, Long salary, Long department, Act action){
        AuditLog auditLog = new AuditLog();
        auditLog.setIduser(user);
        auditLog.setIdnv(employee);
        auditLog.setIdluong(salary);
        auditLog.setIdpb(department);
        auditLog.setAct(action);
        aRepository.save(auditLog);
    }

    @Transactional
    public void updateAuditOperation(User user, Long employee, Long salary, Long department, Act action, Object oldEntity, Object newEntity){
        AuditLog auditLog = new AuditLog();
        auditLog.setIduser(user);
        auditLog.setIdnv(employee);
        auditLog.setIdluong(salary);
        auditLog.setIdpb(department);
        auditLog.setAct(action);
        AuditLog savedLog = aRepository.save(auditLog);
        trackChanges(oldEntity, newEntity, savedLog);
    }

}
