package com.practiceproject.EmployeeManagementSystem.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.practiceproject.EmployeeManagementSystem.entity.Department;
import com.practiceproject.EmployeeManagementSystem.entity.User;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    //Tìm kiếm
    @Query(value = "SELECT d FROM Department d WHERE d.iduser LIKE ?1"
    + "AND (d.tenpb LIKE %?2%"
    + "OR d.idpb LIKE %?2%"
    + "OR d.diachi LIKE %?2%"
    + "OR d.sdt LIKE %?2%)", nativeQuery = true)
    public List<Department> findAllDepartments(User iduser, String keyword);

    public Page<Department> findAllByiduser(User iduser, Pageable pageable);
}
