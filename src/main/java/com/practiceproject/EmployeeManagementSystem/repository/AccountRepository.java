package com.practiceproject.EmployeeManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.practiceproject.EmployeeManagementSystem.entity.Account;
import com.practiceproject.EmployeeManagementSystem.entity.User;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
    @Query("SELECT a FROM Account a WHERE a.email=?1")
    public Account findByEmail(String email);

    public Account findByResetPassToken(String token);

    public User findByiduser(Account account);
}
