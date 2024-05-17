package com.practiceproject.EmployeeManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity//Chỉ định rằng lớp là một thực thể và được ánh xạ tới bảng cơ sở dữ liệu
@Table(name = "tblEmployee")//Chỉ định tên của bảng cơ sở dữ liệu sẽ được sử dụng để ánh xạ
public class  Employee {
    @Id//Chỉ định khóa chính của một thực thể
    @GeneratedValue (strategy = GenerationType.IDENTITY)//Cung cấp thông số kỹ thuật của strategy tạo các giá trị cho khóa chính
    //Để tự động tạo các giá trị duy nhất cho các cột khóa chính trong các bảng cơ sở dữ liệu của chúng ta.
    private Long idnv;
    
    @ManyToOne
    @JoinColumn(name = "id_pb", nullable = false, referencedColumnName = "id_pb")
    @JsonBackReference
    private Department idpb;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_luong", referencedColumnName = "id_luong")
    @JsonBackReference
    private Salary idluong;
    
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false, referencedColumnName = "id_user")
    @JsonBackReference
    private User iduser;

    private String hoten;
    private String ngaysinh;
    private String quequan;
    private String gt;
    private String dantoc;
    private String sdt;
    private String email;
    private String chucvu;
    
    @Lob
    private String anh;
    
    //Constructor được sử dụng để khởi tạo đối tượng 
    public Employee() {
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

    public Department getIdpb() {
        return idpb;
    }
    public void setIdpb(Department idpb) {
        this.idpb = idpb;
    }

    public Salary getIdluong() {
        return idluong;
    }

    public void setIdluong(Salary idluong) {
        this.idluong = idluong;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public User getIduser() {
        return iduser;
    }

    public void setIduser(User iduser) {
        this.iduser = iduser;
    }

    
}
