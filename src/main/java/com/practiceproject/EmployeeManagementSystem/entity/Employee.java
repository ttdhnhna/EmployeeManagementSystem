package com.practiceproject.EmployeeManagementSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity//Chỉ định rằng lớp là một thực thể và được ánh xạ tới bảng cơ sở dữ liệu
@Table(name = "tblEmployee")//Chỉ định tên của bảng cơ sở dữ liệu sẽ được sử dụng để ánh xạ
public class Employee {
    @Id//Chỉ định khóa chính của một thực thể
    @GeneratedValue (strategy = GenerationType.IDENTITY)//Cung cấp thông số kỹ thuật của strategy tạo các giá trị cho khóa chính
    private Long idnv;
    private String Hoten;
    private String Ngaysinh;
    private String Quequan;
    private String Dantoc;
    private String SDT;
    private String Email;
    private String Chucvu;
    private Long idpb;
    private Long idtdhv;
    private Long idluong;
    private Long idmhd;
    //Constructor được sử dụng để khởi tạo đối tượng 
    public Employee() {
    }
    public Employee(Long idnv, String hoten, String ngaysinh, String quequan, String dantoc, String sDT, String Email, String chucvu,
        Long idpb, Long idtdhv, Long idluong, Long idmhd) {
        this.idnv = idnv;
        this.Hoten = hoten;
        this.Ngaysinh = ngaysinh;
        this.Quequan = quequan;
        this.Dantoc = dantoc;
        this.SDT = sDT;
        this.Email = Email;
        this.Chucvu = chucvu;
        this.idpb = idpb;
        this.idtdhv = idtdhv;
        this.idluong = idluong;
        this.idmhd = idmhd;
    }
    //Các getter và setter này được dùng để giúp các lớn bên ngoài có thể lấy và chỉnh sửa các thuộc tính lớp con kế thừa thuộc tính của lớp này 
    //Lý do cần sử dụng là để giúp các dữ liệu quan trọng như thuộc tính của lớp cha sẽ không thể bị thay đổi bởi người dùng.
    public Long getIdnv() {
        return idnv;
    }
    public void setIdnv(Long idnv) {
        this.idnv = idnv;
    }
    public String getHoten() {
        return Hoten;
    }
    public void setHoten(String hoten) {
        Hoten = hoten;
    }
    public String getNgaysinh() {
        return Ngaysinh;
    }
    public void setNgaysinh(String ngaysinh) {
        Ngaysinh = ngaysinh;
    }
    public String getQuequan() {
        return Quequan;
    }
    public void setQuequan(String quequan) {
        Quequan = quequan;
    }
    public String getDantoc() {
        return Dantoc;
    }
    public void setDantoc(String dantoc) {
        Dantoc = dantoc;
    }
    public String getSDT() {
        return SDT;
    }
    public void setSDT(String sDT) {
        SDT = sDT;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public String getChucvu() {
        return Chucvu;
    }
    public void setChucvu(String chucvu) {
        Chucvu = chucvu;
    }
    public Long getIdpb() {
        return idpb;
    }
    public void setIdpb(Long idpb) {
        this.idpb = idpb;
    }
    public Long getIdtdhv() {
        return idtdhv;
    }
    public void setIdtdhv(Long idtdhv) {
        this.idtdhv = idtdhv;
    }
    public Long getIdluong() {
        return idluong;
    }
    public void setIdluong(Long idluong) {
        this.idluong = idluong;
    }
    public Long getIdmhd() {
        return idmhd;
    }
    public void setIdmhd(Long idmhd) {
        this.idmhd = idmhd;
    }
    
    
}
