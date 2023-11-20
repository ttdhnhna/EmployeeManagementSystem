package com.practiceproject.EmployeeManagementSystem.entity;

// import jakarta.persistence.Column;
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
    //Để tự động tạo các giá trị duy nhất cho các cột khóa chính trong các bảng cơ sở dữ liệu của chúng ta.
    private Long idnv;

    // @Column(name = "Hoten")
    private String Hoten;
    // @Column(name = "Ngaysinh")
    private String Ngaysinh;
    // @Column(name = "Quequan")
    private String Quequan;
    // @Column(name = "GT")
    private String GT;
    // @Column(name = "Dantoc")
    private String Dantoc;
    // @Column(name = "SDT")
    private String SDT;
    // @Column(name = "Email")
    private String Email;
    // @Column(name = "Chucvu")
    private String Chucvu;
    
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

    public String getGT() {
        return GT;
    }

    public void setGT(String gT) {
        GT = gT;
    }
}
