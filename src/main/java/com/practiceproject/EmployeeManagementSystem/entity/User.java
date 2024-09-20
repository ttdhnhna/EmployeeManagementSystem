package com.practiceproject.EmployeeManagementSystem.entity;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "tblUser")
public class User {
    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iduser;
    private String hoten;
    private String email; 

    @OneToMany(mappedBy = "iduser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 16)
    @JsonManagedReference
    private Set<Employee> idnv = new HashSet<>();

    @OneToMany(mappedBy = "iduser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 16)
    @JsonManagedReference
    private Set<Department> idpb = new HashSet<>();

    @OneToMany(mappedBy = "iduser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 16)
    @JsonManagedReference
    private Set<AuditLog> idlog = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_acc", referencedColumnName = "id_acc")
    @BatchSize(size = 16)
    @JsonBackReference
    private Account idacc;

    public User() {
    }
    public Long getIduser() {
        return iduser;
    }
    public void setIduser(Long iduser) {
        this.iduser = iduser;
    }
    public String getHoten() {
        return hoten;
    }
    public void setHoten(String hoten) {
        this.hoten = hoten;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Set<Employee> getIdnv() {
        return idnv;
    }
    public void setIdnv(Set<Employee> idnv) {
        this.idnv = idnv;
    }
    public Set<Department> getIdpb() {
        return idpb;
    }
    public void setIdpb(Set<Department> idpb) {
        this.idpb = idpb;
    }
    public Set<AuditLog> getIdlog() {
        return idlog;
    }
    public void setIdlog(Set<AuditLog> idlog) {
        this.idlog = idlog;
    }
    public Account getIdacc() {
        return idacc;
    }
    public void setIdacc(Account idacc) {
        this.idacc = idacc;
    }
}
