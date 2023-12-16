package com.practiceproject.EmployeeManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practiceproject.EmployeeManagementSystem.entity.Employee;

@Repository //@Repository được sử dụng để chỉ ra rằng lớp này cung cấp chức năng lưu trữ, truy xuất, tìm kiếm, cập nhật và xóa hoạt động trên các đối tượng
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
//Lớp này được dùng để quản lý dữ liệu quan hệ trong các ứng dụng Java, cho phép ta truy cập và lưu trữ dữ liệu giữa các đối tượng Java và CSDL.
}
