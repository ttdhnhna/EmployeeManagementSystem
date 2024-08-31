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

@Service
public class EntityChangesService {
    @Autowired 
    EntityChangesRepository repository;
    @Autowired
    AuditLogRepository aRepository;

    public void logAuditOperation(User user, Long employee, Long salary, Long department, Act action){
        AuditLog auditLog = new AuditLog();
        auditLog.setIduser(user);
        auditLog.setIdnv(employee);
        auditLog.setIdluong(salary);
        auditLog.setIdpb(department);
        auditLog.setAct(action);
        aRepository.save(auditLog);
    }

    public AuditLog updateAuditOperation(User user, Long employee, Long salary, Long department, Act action){
        AuditLog auditLog = new AuditLog();
        auditLog.setIduser(user);
        auditLog.setIdnv(employee);
        auditLog.setIdluong(salary);
        auditLog.setIdpb(department);
        auditLog.setAct(action);
        AuditLog saveAuditLog = aRepository.save(auditLog);
        return saveAuditLog;
    }

    public void trackChanges(Object oldEntity, Object newEntity, AuditLog idlog){
        Class<?> clazz = oldEntity.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for(Field field : fields){
            field.setAccessible(true);

            try {
                Object oldValue = field.get(oldEntity);
                Object newValue = field.get(newEntity);

                if(oldValue != null && !oldValue.equals(newValue)){
                    EntityChanges change = new EntityChanges();
                    change.setEntityType(clazz.getSimpleName());
                    change.setFieldName(field.getName());
                    change.setNewValue(newValue.toString());
                    change.setOldValue(oldValue.toString());
                    change.setIdlog(idlog);
                    System.out.println("Saving change for field: " + field.getName() + 
                    " Old Value: " + oldValue + 
                    " New Value: " + newValue);

                    repository.save(change);
                    repository.flush();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
