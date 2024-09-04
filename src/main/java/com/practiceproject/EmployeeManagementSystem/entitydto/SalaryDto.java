package com.practiceproject.EmployeeManagementSystem.entitydto;

public class SalaryDto {
    private Long idluong;
    private float hsl;
    private float phucap;
    private float baohiem;
    private float truluong;
    
    public SalaryDto() {
    }

    public SalaryDto(Long idluong, float hsl, float phucap, float baohiem, float truluong) {
        this.idluong = idluong;
        this.hsl = hsl;
        this.phucap = phucap;
        this.baohiem = baohiem;
        this.truluong = truluong;
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
}
