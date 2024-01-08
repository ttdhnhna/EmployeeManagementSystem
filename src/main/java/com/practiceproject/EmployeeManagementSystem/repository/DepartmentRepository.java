package com.practiceproject.EmployeeManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.practiceproject.EmployeeManagementSystem.entity.Department;
import com.practiceproject.EmployeeManagementSystem.entity.Employee;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    //Tìm kiếm
    @Query("SELECT p FROM Department p WHERE p.tenpb LIKE %?1%"
    + "OR p.idpb LIKE %?1%"
    + "OR p.diachi LIKE %?1%"
    + "OR p.sdt LIKE %?1%")
    public List<Department> findAllDepartments(String keyword);

    //Lấy id nv và cho vào list
    @Query(value = "SELECT e.idnv FROM Employee e"
    +"INNER JOIN Department d"
    +"ON d.idpb=e.idpb", nativeQuery = true)
    public List<Employee> findAllIDNV();

    //Lấy thông tin danh sách nhân viên: id, hoten, sdt, chuc vu
//    @Query("SELECT e.idnv, e.hoten, e.sdt, e.chucvu FROM Employee e"
//    +"INNER JOIN Department d "
//    +"ON d.idpb=e.idpb")
//    public List<Employee> getNVInformationbyID();
//@Query("SELECT new com.roytuts.spring.data.jpa.left.right.inner.cross.join.dto.DeptEmpDto(d.name, e.name, e.email, e.address) "
// + "FROM Department d LEFT JOIN d.employees e")
// List<DeptEmpDto> fetchEmpDeptDataLeftJoin();

}
