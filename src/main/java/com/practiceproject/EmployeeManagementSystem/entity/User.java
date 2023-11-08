package com.practiceproject.EmployeeManagementSystem.entity;

import java.util.Collection;

import javax.management.relation.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tblUser")
public class User {
    //User POJO
    private String name;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;
    public User(String name, String password, Collection<Role> roles) {
        super();
        this.name = name;
        this.password = password;
        this.roles = roles;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Collection<Role> getRoles() {
        return roles;
    }
    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
    
}
