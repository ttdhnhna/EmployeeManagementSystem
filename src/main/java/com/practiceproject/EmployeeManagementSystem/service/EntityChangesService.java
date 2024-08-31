package com.practiceproject.EmployeeManagementSystem.service;


import java.util.List;

import org.javers.core.Javers;
import org.javers.core.diff.Change;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.repository.jql.QueryBuilder;
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
    @Autowired
    Javers javers;

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

    public void updatelogAuditOperation(AuditLog idlog, Object entityType){
        List<Change> changes = javers.findChanges(
            QueryBuilder.byInstanceId(entityType, entityType.getClass()).build()
        );
        for(Change c : changes){
            if(c instanceof ValueChange){
                ValueChange valueChange = (ValueChange) c;
                EntityChanges entityChanges = new EntityChanges();  
                entityChanges.setEntityType(entityType.toString());
                entityChanges.setFieldName(valueChange.getPropertyName());
                entityChanges.setOldValue(valueChange.getLeft().toString());
                entityChanges.setNewValue(valueChange.getRight().toString());
                entityChanges.setIdlog(idlog);
                repository.save(entityChanges);
            }
        }
    }
}
