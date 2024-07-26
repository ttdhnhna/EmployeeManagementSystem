package com.practiceproject.EmployeeManagementSystem.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.practiceproject.EmployeeManagementSystem.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
    @Query("SELECT u FROM User u WHERE u.email=?1")
    User findbyEmail(String email);

    @EntityGraph(value = "User.detail", type = EntityGraph.EntityGraphType.LOAD)
    public User findByResetPassToken(String token);
}
