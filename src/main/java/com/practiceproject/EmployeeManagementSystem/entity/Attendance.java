package com.practiceproject.EmployeeManagementSystem.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "tblAttendance")
public class Attendance {
    @Id
    @Column(name = "id_att")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idatt;

    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private String ipAddress;
    private Double latitude;
    private Double longitude;
    private String city;
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nv", nullable = false, referencedColumnName = "id_nv")
    @BatchSize(size = 16)
    @JsonBackReference
    private Employee idnv;

    @PrePersist
    protected void onCreate(){
        checkInTime = LocalDateTime.now();
        checkOutTime = LocalDateTime.now();
    }

    public Attendance() {
    }

    public Long getIdatt() {
        return idatt;
    }

    public void setIdatt(Long idatt) {
        this.idatt = idatt;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }  

    public Employee getIdnv() {
        return idnv;
    }

    public void setIdnv(Employee idnv) {
        this.idnv = idnv;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }   
}
