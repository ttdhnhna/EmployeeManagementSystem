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
    @Query(value = "SELECT * FROM tbl_salary s WHERE s.id_user LIKE ?1 "
    + " AND (s.hsl LIKE %?2% "
    + " OR CAST(i.id_luong AS TEXT) LIKE %?2%);", nativeQuery = true)
    public List<Salary> findAllSalaries(Long iduser, String keyword);
    public Page<Salary> findAllByiduser(User iduser, Pageable pageable);
}
