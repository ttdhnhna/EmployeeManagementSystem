package com.practiceproject.EmployeeManagementSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Salary
 */
@Entity
@Table(name="tblSalary")
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idluong;

    private Long idnv;
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

    public Long getIdnv() {
        return idnv;
    }

    public void setIdnv(Long idnv) {
        this.idnv = idnv;
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
}