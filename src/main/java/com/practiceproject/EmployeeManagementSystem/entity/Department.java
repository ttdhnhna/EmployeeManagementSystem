package com.practiceproject.EmployeeManagementSystem.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tblDepartment")
public class Department {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_pb")
    private Long idpb;
    @OneToMany(mappedBy = "idpb", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Employee> idnv;
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
    public Set<Employee> getIdnv() {
        return idnv;
    }
    public void setIdnv(Set<Employee> idnv) {
        this.idnv.clear();
        this.idnv.addAll(idnv);
        //this.idnv=idnv;
    }
    
}
