package com.practiceproject.EmployeeManagementSystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

    @OneToOne(mappedBy = "iduser")
    @JsonManagedReference
    private Employee idnv;
    
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
    public Employee getIdnv() {
        return idnv;
    }
    public void setIdnv(Employee idnv) {
        this.idnv = idnv;
    }
}
