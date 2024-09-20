package com.practiceproject.EmployeeManagementSystem.repository;

// import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.practiceproject.EmployeeManagementSystem.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    // @EntityGraph(attributePaths = {"idnv","idpb"})
    @Query("SELECT u FROM User u WHERE u.email=?1")
//    @Query("SELECT u FROM User u JOIN FETCH u.idpb d JOIN FETCH d.idnv e WHERE u.email = :email")
    User findbyEmail(String email);

}
