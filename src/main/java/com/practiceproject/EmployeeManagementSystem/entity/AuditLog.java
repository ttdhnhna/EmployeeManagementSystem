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
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log")
    private Long idlog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false, referencedColumnName = "id_user")
    @BatchSize(size = 16)
    @JsonBackReference
    private User iduser;
    
    public enum Act{
        LOGIN, LOGOUT, ADD, UPDATE, DELETE
    }

    @Column(name = "ngayth", updatable = false)
    private LocalDateTime ngayth; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idnv", nullable = true, referencedColumnName = "idnv")
    @BatchSize(size = 16)
    @JsonBackReference
    private Employee idnv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pb", nullable = true, referencedColumnName = "id_pb")
    @BatchSize(size = 16)
    @JsonBackReference
    private Department idpb;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_luong", nullable = true, referencedColumnName = "id_luong")
    @BatchSize(size = 16)
    @JsonBackReference
    private Salary idluong;

    private Act act;

    @PrePersist
    protected void onCreate() {
        ngayth = LocalDateTime.now();
    }

    public AuditLog() {
    }

    public Long getIdlog() {
        return idlog;
    }

    public void setIdlog(Long idlog) {
        this.idlog = idlog;
    }

    public User getIduser() {
        return iduser;
    }

    public void setIduser(User iduser) {
        this.iduser = iduser;
    }

    public Employee getIdnv() {
        return idnv;
    }

    public void setIdnv(Employee idnv) {
        this.idnv = idnv;
    }

    public Department getIdpb() {
        return idpb;
    }

    public void setIdpb(Department idpb) {
        this.idpb = idpb;
    }

    public Salary getIdluong() {
        return idluong;
    }

    public void setIdluong(Salary idluong) {
        this.idluong = idluong;
    }

    public Act getAct() {
        return act;
    }

    public void setAct(Act act) {
        this.act = act;
    }

}
