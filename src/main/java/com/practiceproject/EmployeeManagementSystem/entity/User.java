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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

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
    private String password;

    @Column(name = "reset_password_token")
    private String resetPassToken;

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

    public enum Role{
        MANAGER, EMPLOYEE
    }

    private Role role;

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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getResetPassToken() {
        return resetPassToken;
    }
    public void setResetPassToken(String resetPassToken) {
        this.resetPassToken = resetPassToken;
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
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
}
