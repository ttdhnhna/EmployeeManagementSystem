package com.practiceproject.EmployeeManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practiceproject.EmployeeManagementSystem.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
    
}