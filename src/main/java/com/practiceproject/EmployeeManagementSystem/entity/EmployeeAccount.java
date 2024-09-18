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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "tblEmployeeAccount")
public class EmployeeAccount {
    @Id
    @Column(name = "id_acc")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idacc;
    private String email; 
    private String password;

    @Column(name = "reset_password_token")
    private String resetPassToken;
    
    @OneToOne(mappedBy = "idacc", fetch = FetchType.LAZY)
    @BatchSize(size = 16)
    @JsonManagedReference
    private Employee idnv;

    @OneToMany(mappedBy = "idacc",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 16)
    @JsonManagedReference
    private Set<Attendance> idatt = new HashSet<>();

    public EmployeeAccount() {
    }

    public Long getIdacc() {
        return idacc;
    }

    public void setIdacc(Long idacc) {
        this.idacc = idacc;
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

    public Employee getIdnv() {
        return idnv;
    }

    public void setIdnv(Employee idnv) {
        this.idnv = idnv;
    }

    public Set<Attendance> getIdatt() {
        return idatt;
    }

    public void setIdatt(Set<Attendance> idatt) {
        this.idatt = idatt;
    } 
}
