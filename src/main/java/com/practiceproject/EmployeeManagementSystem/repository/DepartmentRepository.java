package com.practiceproject.EmployeeManagementSystem.repository;

import java.util.List;
// import java.util.Map;

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
    @Query("SELECT p FROM Department p WHERE p.tenpb LIKE %?1%"
    + "OR p.idpb LIKE %?1%"
    + "OR p.diachi LIKE %?1%"
    + "OR p.sdt LIKE %?1%")
    public List<Department> findAllDepartments(String keyword);

    public Page<Department> findAllByiduser(User iduser, Pageable pageable);
}
