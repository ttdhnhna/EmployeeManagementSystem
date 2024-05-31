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
    @Query("SELECT s FROM Salary s WHERE s.idluong LIKE %?1%")
    public List<Salary> findAllSalaries(String keyword);
    public Page<Salary> findAllByiduser(User iduser, Pageable pageable);
}
