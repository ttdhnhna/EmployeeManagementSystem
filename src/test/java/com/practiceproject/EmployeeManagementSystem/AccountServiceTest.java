// package com.practiceproject.EmployeeManagementSystem;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// // import java.util.Optional;

// import javax.transaction.Transactional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// import com.practiceproject.EmployeeManagementSystem.entity.User;
// import com.practiceproject.EmployeeManagementSystem.repository.UserRepository;
// import com.practiceproject.EmployeeManagementSystem.service.AccountService;

// @SpringBootTest
// public class AccountServiceTest {
//     @Mock
//     private UserRepository repository;

//     @InjectMocks
//     private AccountService serivce;

//     private BCryptPasswordEncoder passwordEncoder;

//     @BeforeEach
//     void SetUp(){
//         MockitoAnnotations.openMocks(this);
//         passwordEncoder=new BCryptPasswordEncoder();
//     }

//     @Test
//     @Transactional
//     void testChangepassword(){
//         User user = new User();
//         user.setEmail("vidu@gmail.com");
//         user.setHoten("dat");
//         String encodedPass = passwordEncoder.encode("123"); 
//         user.setPassword(encodedPass);

//         when(repository.save(any(User.class))).thenReturn(user); // 
//         // when(repository.findById(any(Long.class))).thenReturn(Optional.of(user)); //Được sử dụng với giả định rằng chức năng mình đang test có tìm kiếm user theo ID

//         serivce.changePassword("123", "256", "256", user);

//         verify(repository).save(user);//Được sử dụng để đảm bảo rằng 1 phương thức nào đó(ở đây là repository) ở một đối tượng mock được gọi thực hiện các chức năng cụ thể trong bài test
//         assertEquals("vidu@gmail.com", user.getEmail());
//         assertEquals("dat", user.getHoten());
//         assertEquals(true, passwordEncoder.matches("256", user.getPassword()));
//     }
// }
