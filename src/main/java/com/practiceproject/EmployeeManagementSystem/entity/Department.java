package com.practiceproject.EmployeeManagementSystem.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "tblDepartment")
public class Department {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_pb")
    private Long idpb;

    @OneToMany(mappedBy = "idpb", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 16)
    @JsonManagedReference
    private Set<Employee> idnv = new HashSet<>();

    @OneToMany(mappedBy = "idpb", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 16)
    @JsonManagedReference
    private Set<AuditLog> idlog = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @BatchSize(size = 16)
    @JoinColumn(name = "id_user", nullable = false)
    @JsonBackReference
    private User iduser;
    
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
    
    public void setIdnv(Set<Employee> idnv) {
        this.idnv=idnv;
    }
    public Set<Employee> getIdnv() {
        return idnv;
    }
    public User getIduser() {
        return iduser;
    }
    public void setIduser(User iduser) {
        this.iduser = iduser;
    }
    public Set<AuditLog> getIdlog() {
        return idlog;
    }
    public void setIdlog(Set<AuditLog> idlog) {
        this.idlog = idlog;
    }
}
