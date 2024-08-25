package com.practiceproject.EmployeeManagementSystem.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class EntityChanges {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_change")
    private Long idchange;

    private String entityType; //Employee, Department, Salary
    private String fieldName; 
    private String oldValue; 
    private String newValue;

    @Column(name = "ngayth", updatable = false)
    private LocalDateTime ngayth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_log", nullable = false, referencedColumnName = "id_log")
    @BatchSize(size = 16)
    @JsonBackReference
    private AuditLog idlog;

    @PrePersist
    protected void onCreate(){
        ngayth = LocalDateTime.now();
    }

    public EntityChanges() {
    }

    public Long getIdchange() {
        return idchange;
    }

    public void setIdchange(Long idchange) {
        this.idchange = idchange;
    }

    public AuditLog getIdlog() {
        return idlog;
    }

    public void setIdlog(AuditLog idlog) {
        this.idlog = idlog;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public LocalDateTime getNgayth() {
        return ngayth;
    }

}
