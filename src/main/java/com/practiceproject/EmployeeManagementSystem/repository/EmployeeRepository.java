package com.practiceproject.EmployeeManagementSystem.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.practiceproject.EmployeeManagementSystem.entity.Employee;
import com.practiceproject.EmployeeManagementSystem.entity.User;

@Repository //@Repository được sử dụng để chỉ ra rằng lớp này cung cấp chức năng lưu trữ, truy xuất, tìm kiếm, cập nhật và xóa hoạt động trên các đối tượng
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
//Lớp này được dùng để quản lý dữ liệu quan hệ trong các ứng dụng Java, cho phép ta truy cập và lưu trữ dữ liệu giữa các đối tượng Java và CSDL.
    @Query("SELECT p FROM Employee p WHERE p.hoten LIKE %?1%"
    + "OR p.idnv LIKE %?1%"
    + "OR p.chucvu LIKE %?1%")
    public List<Employee> findAll(String keywords);

    public Page<Employee> findAllByiduser(User iduser, Pageable pageable);
}
