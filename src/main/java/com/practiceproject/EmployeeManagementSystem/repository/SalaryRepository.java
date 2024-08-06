package com.practiceproject.EmployeeManagementSystem.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.practiceproject.EmployeeManagementSystem.entity.Salary;
import com.practiceproject.EmployeeManagementSystem.entity.User;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long>{
    // @Query(value = "SELECT s.* FROM tbl_salary s INNER JOIN tbl_employee e "
    // + " ON s.id_luong = e.id_luong" 
    // + " WHERE e.id_user = ?1 "
    // + " AND (s.hsl LIKE %?2% "
    // + " OR CAST(s.id_luong AS TEXT) LIKE %?2%);", nativeQuery = true)
    @Query(value = "SELECT s.* FROM tbl_salary s INNER JOIN tbl_employee e "
    + " ON s.id_luong = e.id_luong" 
    + " WHERE e.id_user = ?1 "
    + " AND (s.hsl LIKE %?2% "
    + " OR s.id_luong LIKE %?2%);", nativeQuery = true)
    public List<Salary> findAllSalaries(Long iduser, String keyword);

//    @Query(value = "SELECT s FROM Salary s JOIN FETCH s.idnv e JOIN FETCH e.iduser u WHERE u = :iduser",
//        countQuery = "SELECT COUNT(s) FROM Salary s JOIN s.idnv e WHERE e.iduser = :iduser")
    // @EntityGraph(attributePaths = {"idnv"})
    public Page<Salary> findAllByIdnvIduser(User iduser, Pageable pageable);
}
