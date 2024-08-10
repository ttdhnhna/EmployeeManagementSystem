package com.practiceproject.EmployeeManagementSystem.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.practiceproject.EmployeeManagementSystem.entity.Employee;
import com.practiceproject.EmployeeManagementSystem.entity.User;

@Repository //@Repository được sử dụng để chỉ ra rằng lớp này cung cấp chức năng lưu trữ, truy xuất, tìm kiếm, cập nhật và xóa hoạt động trên các đối tượng
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
//Lớp này được dùng để quản lý dữ liệu quan hệ trong các ứng dụng Java, cho phép ta truy cập và lưu trữ dữ liệu giữa các đối tượng Java và CSDL.
    // @Query(value = "SELECT e.* FROM tbl_employee e" 
    // + " WHERE e.id_user = ?1 AND (e.hoten LIKE %?2% "
    // + " OR CAST(e.idnv AS TEXT) LIKE %?2% " 
    // + " OR e.chucvu LIKE %?2%);", nativeQuery = true)
    @Query(value = "SELECT e.* FROM tbl_employee e" 
    + " WHERE e.id_user = ?1 AND (e.hoten LIKE %?2% "
    + " OR e.idnv LIKE %?2% " 
    + " OR e.chucvu LIKE %?2%);", nativeQuery = true)
    public List<Employee> findAllbyiduser(Long iduser, String keywords);

    // @EntityGraph(attributePaths = {"idpb", "idluong"})
//    @Query(value = "SELECT e FROM Employee e JOIN FETCH e.idpb d JOIN FETCH d.iduser u JOIN FETCH e.idluong WHERE u = :iduser",
//        countQuery = "SELECT COUNT(e) FROM Employee e WHERE e.iduser = :iduser")
    public Page<Employee> findAllByIduser(User iduser, Pageable pageable);
    
    @Query(value = "SELECT e FROM Employee e WHERE e.hoten = :tennv")
    public Employee findByHoten(String tennv);
}
