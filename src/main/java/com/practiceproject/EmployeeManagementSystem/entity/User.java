package com.practiceproject.EmployeeManagementSystem.entity;

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

import org.hibernate.annotations.Fetch;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "tblUser")
public class User {
    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iduser;
    private String hoten;
    @Column(nullable = false, unique = true)
    private String email; 
    private String password;

    @Column(name = "reset_password_token")
    private String resetPassToken;

    @OneToMany(mappedBy = "iduser", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JsonManagedReference
    private Set<Employee> idnv;

    @OneToMany(mappedBy = "iduser", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JsonManagedReference
    private Set<Department> idpb;

    @OneToMany(mappedBy = "iduser", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    @JsonManagedReference
    private Set<Salary> idluong;
    
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
    
}
