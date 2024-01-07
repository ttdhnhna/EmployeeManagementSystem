package com.practiceproject.EmployeeManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.practiceproject.EmployeeManagementSystem.entity.Salary;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long>{
    @Query("SELECT s FROM Salary s WHERE s.hsl LIKE %?1%"
    + "OR s.idluong LIKE %?1%"
    + "OR s.idnv LIKE %?1%"
    + "OR s.phucap LIKE %?1%"
    + "OR s.baohiem LIKE %?1%")
    public List<Salary> findAllSalaries(String keyword);
}
