package com.practiceproject.EmployeeManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    private double luongcb;
    private double hsl;
    private double phucap;
    private double baohiem;
    private double truluong;
    private double tongluong;
    
    public Salary() {
    }

    public Long getIdluong() {
        return idluong;
    }

    public void setIdluong(Long idluong) {
        this.idluong = idluong;
    }

    public double getLuongcb() {
        return luongcb;
    }

    public void setLuongcb(double luongcb) {
        this.luongcb = luongcb;
    }

    public double getHsl() {
        return hsl;
    }

    public void setHsl(double hsl) {
        this.hsl = hsl;
    }

    public double getPhucap() {
        return phucap;
    }

    public void setPhucap(double phucap) {
        this.phucap = phucap;
    }

    public double getBaohiem() {
        return baohiem;
    }

    public void setBaohiem(double baohiem) {
        this.baohiem = baohiem;
    }

    public double getTruluong() {
        return truluong;
    }

    public void setTruluong(double truluong) {
        this.truluong = truluong;
    }

    public double getTongluong() {
        tongluong=luongcb*hsl+phucap-baohiem-truluong;
        return tongluong;
    }

    public void setTongluong(double tongluong) {
        this.tongluong = tongluong;
    }

    public Employee getIdnv() {
        return idnv;
    }

    public void setIdnv(Employee idnv) {
        this.idnv = idnv;
    }
}