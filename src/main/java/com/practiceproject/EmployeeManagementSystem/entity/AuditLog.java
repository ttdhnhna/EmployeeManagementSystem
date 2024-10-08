package com.practiceproject.EmployeeManagementSystem.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    
    @OneToMany(mappedBy = "idlog", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @BatchSize(size = 16)
    @JsonManagedReference
    private Set<EntityChanges> idchange = new HashSet<>();

    public enum Act{
        LOGIN, LOGOUT, ADD, UPDATE, DELETE, CHANGEPASS
    }

    @Column(name = "ngayth", updatable = false)
    private LocalDateTime ngayth; 
    @Column(nullable = true)
    private Long idnv;
    @Column(nullable = true)
    private Long idpb;
    @Column(nullable = true)
    private Long idluong;

    private Act act;
    private boolean isRead = false;

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
     
    public Set<EntityChanges> getIdchange() {
        return idchange;
    }

    public void setIdchange(Set<EntityChanges> idchange) {
        this.idchange = idchange;
    }

    public Long getIdnv() {
        return idnv;
    }

    public void setIdnv(Long idnv) {
        this.idnv = idnv;
    }

    public Long getIdpb() {
        return idpb;
    }

    public void setIdpb(Long idpb) {
        this.idpb = idpb;
    }

    public Long getIdluong() {
        return idluong;
    }

    public void setIdluong(Long idluong) {
        this.idluong = idluong;
    }

    public Act getAct() {
        return act;
    }

    public void setAct(Act act) {
        this.act = act;
    }

    public LocalDateTime getNgayth() {
        return ngayth;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }
}
