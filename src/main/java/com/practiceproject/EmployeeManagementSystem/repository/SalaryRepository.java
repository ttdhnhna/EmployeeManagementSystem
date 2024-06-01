package com.practiceproject.EmployeeManagementSystem.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.practiceproject.EmployeeManagementSystem.entity.Salary;
import com.practiceproject.EmployeeManagementSystem.entity.User;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long>{
    @Query(value = "SELECT s FROM tbl_salary s WHERE s.id_user LIKE ?1 "
    + " AND (s.hsl LIKE %?2% "
    + " OR s.id_luong LIKE %?2% "
    + " OR s.phucap LIKE %?2% "
    + " OR s.baohiem LIKE %?2%);", nativeQuery = true)
    public List<Salary> findAllSalaries(User iduser, String keyword);
    public Page<Salary> findAllByiduser(User iduser, Pageable pageable);
}
