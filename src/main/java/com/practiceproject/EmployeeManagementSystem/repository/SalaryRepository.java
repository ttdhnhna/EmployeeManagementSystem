package com.practiceproject.EmployeeManagementSystem.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.practiceproject.EmployeeManagementSystem.entity.Salary;
import com.practiceproject.EmployeeManagementSystem.entity.User;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long>{
    @Query(value = "SELECT s.* FROM tbl_salary s INNER JOIN tbl_employee e "
    + " ON s.id_luong = e.id_luong INNER JOIN tbl_department d " 
    + " ON e.id_pb = d.id_pb"
    + " WHERE d.id_user = ?1 "
    + " AND (s.hsl LIKE %?2% "
    + " OR CAST(s.id_luong AS TEXT) LIKE %?2%);", nativeQuery = true)
    public List<Salary> findAllSalaries(Long iduser, String keyword);

    @EntityGraph(value = "Salary.detail", type = EntityGraph.EntityGraphType.LOAD)
    public Page<Salary> findAllByIdnvIdpbIduser(User iduser, Pageable pageable);
}
