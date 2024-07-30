package com.practiceproject.EmployeeManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="tblSalary")
public class Salary {
    @Id
    @Column(name = "id_luong")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idluong;
    
    @JsonIgnore
    @OneToOne(mappedBy = "idluong", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private Employee idnv;

    private static float luongcb = 1350000;
    private float hsl;
    private float phucap;
    private float baohiem;
    private float truluong;
    private float tongluong;
    private float tienno;
    
    public Salary() {
    }

    public Long getIdluong() {
        return idluong;
    }

    public void setIdluong(Long idluong) {
        this.idluong = idluong;
    }

    public float getHsl() {
        return hsl;
    }

    public void setHsl(float hsl) {
        this.hsl = hsl;
    }

    public float getTongluong() {
        return tongluong;
    }

    public void setTongluong(float tongluong) {
        this.tongluong = tongluong;
    }

    public float getPhucap() {
        return phucap;
    }

    public void setPhucap(float phucap) {
        this.phucap = phucap;
    }

    public float getBaohiem() {
        return baohiem;
    }

    public void setBaohiem(float baohiem) {
        this.baohiem = baohiem;
    }

    public float getTruluong() {
        return truluong;
    }

    public void setTruluong(float truluong) {
        this.truluong = truluong;
    }

    public Employee getIdnv() {
        return idnv;
    }

    public void setIdnv(Employee idnv) {
        this.idnv = idnv;
    }

    public static float getLuongcb() {
        return luongcb;
    }

    // public User getIduser() {
    //     return iduser;
    // }

    // public void setIduser(User iduser) {
    //     this.iduser = iduser;
    // }

    public float getTienno() {
        return tienno;
    }

    public void setTienno(float tienno) {
        this.tienno = tienno;
    }
    
}