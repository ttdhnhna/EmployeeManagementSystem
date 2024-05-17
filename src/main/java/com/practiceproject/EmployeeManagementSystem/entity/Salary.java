package com.practiceproject.EmployeeManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Salary
 */
@Entity
@Table(name="tblSalary")
public class Salary {
    @Id
    @Column(name = "id_luong")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idluong;
    
    @OneToOne(mappedBy = "idluong")
    @JsonManagedReference
    private Employee idnv;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false, referencedColumnName = "id_user")
    @JsonBackReference
    private User iduser;

    private static float luongcb = 1350000;
    private float hsl;
    private float phucap;
    private float baohiem;
    private float truluong;
    private float tongluong;
    
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
        tongluong=luongcb*hsl+phucap-baohiem-truluong;
        return tongluong;
    }

    public void setTongluong(Float tongluong) {
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
    
}