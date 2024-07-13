package com.practiceproject.EmployeeManagementSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.repository.UserRepository;
import com.practiceproject.EmployeeManagementSystem.service.AccountService;

@SpringBootTest
public class AccountServiceTest {
    @Mock
    private UserRepository repository;

    @InjectMocks
    private AccountService serivce;

    @BeforeEach
    void SetUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Transactional
    void testChangepassword(){
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        User user = new User();
        user.setEmail("vidu@gmail.com");
        user.setHoten("dat");
        String encodedPass = passwordEncoder.encode("123"); 
        user.setPassword(encodedPass);
        repository.save(user);

        when(repository.save(any(User.class))).thenReturn(user);
        serivce.changePassword("123", "256", "256", user);

        // verify(repository).save(user);
        assertEquals("vidu@gmail.com", user.getEmail());
        assertEquals("dat", user.getHoten());
        assertEquals(true, passwordEncoder.matches("256", user.getPassword()));
    }
}
