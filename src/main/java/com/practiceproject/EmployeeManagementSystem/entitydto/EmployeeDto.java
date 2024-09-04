package com.practiceproject.EmployeeManagementSystem.entitydto;

import org.springframework.web.multipart.MultipartFile;

/**
 * EmployeeDto
 */
public class EmployeeDto {
    private Long idnv;
    private String hoten;
    private String ngaysinh;
    private String quequan;
    private String gt;
    private String dantoc;
    private String sdt;
    private String email;
    private String chucvu;
    private Long idpb;
    private Long idluong;
    private MultipartFile anh;
    
    public EmployeeDto() {
    }

    public EmployeeDto(Long idnv, String hoten, String ngaysinh, String quequan, String gt, String dantoc, String sdt,
            String email, String chucvu, Long idpb, Long idluong, MultipartFile anh) {
        this.idnv = idnv;
        this.hoten = hoten;
        this.ngaysinh = ngaysinh;
        this.quequan = quequan;
        this.gt = gt;
        this.dantoc = dantoc;
        this.sdt = sdt;
        this.email = email;
        this.chucvu = chucvu;
        this.idpb = idpb;
        this.idluong = idluong;
        this.anh = anh;
    }

    public Long getIdnv() {
        return idnv;
    }

    public void setIdnv(Long idnv) {
        this.idnv = idnv;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getQuequan() {
        return quequan;
    }

    public void setQuequan(String quequan) {
        this.quequan = quequan;
    }

    public String getGt() {
        return gt;
    }

    public void setGt(String gt) {
        this.gt = gt;
    }

    public String getDantoc() {
        return dantoc;
    }

    public void setDantoc(String dantoc) {
        this.dantoc = dantoc;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChucvu() {
        return chucvu;
    }

    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }

    public Long getIdpb() {
        return idpb;
    }

    public void setIdpb(Long idpb) {
        this.idpb = idpb;
    }

    public Long getIdluong() {
        return idluong;
    }

    public void setIdluong(Long idluong) {
        this.idluong = idluong;
    }

    public MultipartFile getAnh() {
        return anh;
    }

    public void setAnh(MultipartFile anh) {
        this.anh = anh;
    }

    
}