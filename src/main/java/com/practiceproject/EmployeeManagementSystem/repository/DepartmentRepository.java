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
    @Query(value = "SELECT * FROM tbl_department d WHERE d.id_user LIKE ?1 "
    + " AND (d.tenpb LIKE %?2% "
    + " OR d.id_pb LIKE %?2%);", nativeQuery = true)
    public List<Department> findAllDepartments(Long iduser, String keyword);

    public Page<Department> findAllByiduser(User iduser, Pageable pageable);
}
