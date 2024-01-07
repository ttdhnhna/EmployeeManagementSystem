package com.practiceproject.EmployeeManagementSystem.entity;

import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tblDepartment")
public class Department {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_pb")
    private Long idpb;
    @OneToMany(mappedBy = "idpb", cascade = CascadeType.ALL)
    @JsonManagedReference
    private HashSet<Employee> idnv;
    private String tenpb;
    private String diachi;
    private String sdt;
    public Department() {
    }
    public Long getIdpb() {
        return idpb;
    }
    public void setIdpb(Long idpb) {
        this.idpb = idpb;
    }
    public String getTenpb() {
        return tenpb;
    }
    public void setTenpb(String tenpb) {
        this.tenpb = tenpb;
    }
    public String getDiachi() {
        return diachi;
    }
    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
    public String getSdt() {
        return sdt;
    }
    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
    public HashSet<Employee> getIdnv() {
        return idnv;
    }
    public void setIdnv(HashSet<Employee> idnv) {
        this.idnv = idnv;
    }
    
}
