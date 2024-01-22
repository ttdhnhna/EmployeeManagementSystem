package com.practiceproject.EmployeeManagementSystem.repository;

import java.util.List;
// import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.practiceproject.EmployeeManagementSystem.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    //Tìm kiếm
    @Query("SELECT p FROM Department p WHERE p.tenpb LIKE %?1%"
    + "OR p.idpb LIKE %?1%"
    + "OR p.diachi LIKE %?1%"
    + "OR p.sdt LIKE %?1%")
    public List<Department> findAllDepartments(String keyword);

    //Lấy id nv và cho vào list
    @Query(value = "SELECT e.idnv FROM dbo.tbl_employee e "
    + "INNER JOIN dbo.tbl_department d "
    + "ON e.id_pb = %?1%", nativeQuery = true)
    public List<Long> findAllIDNV(long id);

    //Lấy thông tin danh sách nhân viên: id, hoten, sdt, chuc vu
//    @Query(value = "SELECT [id], [hoten], [sdt], [chucvu] FROM Employee "
//    +"INNER JOIN Department [idpb] "
//    +"ON [idpb]=[id]", nativeQuery = true)
//    public Map<Long,List<Employee>> getNVInformationbyID();


}
