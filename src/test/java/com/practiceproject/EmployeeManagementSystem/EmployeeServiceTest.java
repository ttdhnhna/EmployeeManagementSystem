package com.practiceproject.EmployeeManagementSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.practiceproject.EmployeeManagementSystem.repository.EmployeeRepository;
import com.practiceproject.EmployeeManagementSystem.service.EmployeeService;

@SpringBootTest
public class EmployeeServiceTest {
    @Mock//Tạo mock đối tượng repository
    private EmployeeRepository repository;

    @InjectMocks//Tạo đối tượng này để các đối tượng mock có thể tác động tới đối tượng này.
    private EmployeeService service;

    @BeforeEach//Thiết lập các mock trước mỗi lần kiểm thử
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveEmployee(){
        
    }
}
