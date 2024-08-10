package com.practiceproject.EmployeeManagementSystem.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.practiceproject.EmployeeManagementSystem.entity.Department;
import com.practiceproject.EmployeeManagementSystem.entity.User;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    //Tìm kiếm
    // @Query(value = "SELECT * FROM tbl_department d WHERE d.id_user = ?1 "
    // + " AND (d.tenpb LIKE %?2% "
    // + " OR CAST(d.id_pb AS TEXT) LIKE %?2%);", nativeQuery = true)
    @Query(value = "SELECT * FROM tbl_department d WHERE d.id_user = ?1 "
    + " AND (d.tenpb LIKE %?2% "
    + " OR d.id_pb LIKE %?2%);", nativeQuery = true)
    public List<Department> findAllDepartments(Long iduser, String keyword);

    // @EntityGraph(attributePaths = {"idnv", "iduser"})
//    @Query(value = "SELECT d FROM Department d JOIN FETCH d.idnv e JOIN FETCH e.iduser u WHERE u = :iduser",
//        countQuery = "SELECT COUNT(d) FROM Department d WHERE d.iduser = :iduser")
    public Page<Department> findAllByiduser(User iduser, Pageable pageable);

    @Query(value = "SELECT d FROM Department d WHERE d.tenpb = :tenpb")
    public Department findByTenpb(String tenpb);
}
