package com.practiceproject.EmployeeManagementSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// import java.io.IOException;
import java.util.Arrays;
// import java.util.Base64;
import java.util.List;
import java.util.Optional;

// import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
// import org.mockito.MockedStatic;
// import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.practiceproject.EmployeeManagementSystem.entity.Department;
import com.practiceproject.EmployeeManagementSystem.entity.Employee;
import com.practiceproject.EmployeeManagementSystem.entity.EmployeeDto;
import com.practiceproject.EmployeeManagementSystem.entity.Salary;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.repository.DepartmentRepository;
import com.practiceproject.EmployeeManagementSystem.repository.EmployeeRepository;
import com.practiceproject.EmployeeManagementSystem.repository.SalaryRepository;
import com.practiceproject.EmployeeManagementSystem.repository.UserRepository;
import com.practiceproject.EmployeeManagementSystem.service.AccountService;
import com.practiceproject.EmployeeManagementSystem.service.DepartmentService;
import com.practiceproject.EmployeeManagementSystem.service.EmployeeService;
import com.practiceproject.EmployeeManagementSystem.service.Utility;

@SpringBootTest
public class EmployeeServiceTest {
    @Mock//Tạo mock đối tượng repository
    private EmployeeRepository repository;
    @Mock
    private DepartmentRepository drepository;
    @Mock
    private UserRepository urepository;
    @Mock
    private SalaryRepository sRepository;
    @Mock
    private DepartmentService dservice;
    @Mock
    private AccountService uservice;
    @Mock
    private Utility utility;

    @InjectMocks//Tạo đối tượng này để các đối tượng mock có thể tác động tới đối tượng này(Dùng để tự động inject các mock đã tạo vào đối tượng mà bạn muốn kiểm thử).
    private EmployeeService service;

    private Employee employee;
    private Employee employee2;
    private Department department;
    private User user;
    private Salary salary;
    private MultipartFile file;

    @BeforeEach//Thiết lập các mock trước mỗi lần kiểm thử
    void setUp(){
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setIduser(1L);

        department = new Department();
        department.setIdpb(1L);
        department.setIduser(user);

        employee = new Employee();
        employee.setHoten("Dat");
        employee.setNgaysinh("24-10-2001");
        employee.setQuequan("Hanoi");
        employee.setGt("Nam");
        employee.setDantoc("Kinh");
        employee.setSdt("0928789025");
        employee.setEmail("123@gmail.com");
        employee.setChucvu("Quan ly");
        employee.setIdpb(department);
        file = new MockMultipartFile("file", "test.png", "image/png", "image content".getBytes());

        employee2 = new Employee();
        employee.setHoten("Ngan");
        employee.setNgaysinh("12-5-2003");
        employee.setQuequan("Hanoi");
        employee.setGt("Nu");
        employee.setDantoc("Kinh");
        employee.setSdt("0995637875");
        employee.setEmail("456@gmail.com");
        employee.setChucvu("Nhan vien");
        employee.setIdpb(department);

        salary = new Salary();
    }
    // @Test
    // @Transactional
    // void testSaveEmployee() throws IOException{
    //     try (MockedStatic<Utility> utilityMockedStatic = Mockito.mockStatic(Utility.class)) {
    //         utilityMockedStatic.when(Utility::getCurrentUserId).thenReturn(1L);
    //         float hsl = 10;
    //         float phucap = 100000;

    //         when(repository.save(any(Employee.class))).thenReturn(employee);
    //         when(drepository.save(any(Department.class))).thenReturn(department);
    //         when(urepository.save(any(User.class))).thenReturn(user);
    //         when(sRepository.save(any(Salary.class))).thenReturn(salary);

    //         service.saveEmployee(employee, file, hsl, phucap);

    //         assertNotNull(employee);
    //         assertEquals("Dat", employee.getHoten());
    //         assertEquals("24-10-2001", employee.getNgaysinh());
    //         assertEquals("Hanoi", employee.getQuequan());
    //         assertEquals("Nam", employee.getGt());
    //         assertEquals("Kinh", employee.getDantoc());
    //         assertEquals("0928789025", employee.getSdt());
    //         assertEquals("123@gmail.com", employee.getEmail());
    //         assertEquals("Quan ly", employee.getChucvu());
    //         assertEquals(Base64.getEncoder().encodeToString(file.getBytes()), employee.getAnh());

    //         verify(repository, times(1));
    //     }
    // }

    @Test
    //loi
    void testDeleteEmployee(){
        User user = new User();
        user.setEmail("vidu@gmail.com");
        user.setHoten("dat");
        user.setPassword("123");
        user = urepository.save(user);

        Department department = new Department();
        department.setTenpb("vidu1");
        department.setDiachi("hanoi");
        department.setIduser(user);
        department = drepository.save(department);

        Employee employee = new Employee();
        employee.setIdnv(1L);
        employee.setAnh(null);
        employee.setHoten("Dat");
        employee.setNgaysinh("24-10-2001");
        employee.setQuequan("Hanoi");
        employee.setGt("Nam");
        employee.setDantoc("Kinh");
        employee.setSdt("0928789025");
        employee.setEmail("123@gmail.com");
        employee.setChucvu("Quan ly");
        employee.setIdpb(department);
        // employee.setIduser(user);

        when(repository.findById(1L)).thenReturn(Optional.of(employee));
        doNothing().when(repository).delete(employee);
        service.deleteEmployeebyID(1L);
        verify(repository, times(1));
    }

    @Test
    void updatedEmployee(){
        User user = new User();
        user.setIduser(1L);
        Department department = new Department();
        department.setIdpb(1L);
        department.setIduser(user);
        Employee employee = new Employee();
        employee.setIdnv(1L);

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setHoten("John Doe");
        employeeDto.setNgaysinh("2000-01-01");
        employeeDto.setQuequan("Hanoi");
        employeeDto.setGt("Male");
        employeeDto.setDantoc("Kinh");
        employeeDto.setSdt("0123456789");
        employeeDto.setEmail("john.doe@example.com");
        employeeDto.setChucvu("Developer");
        employeeDto.setIdpb(1L);
        employeeDto.setAnh(null);
        
        when(dservice.getDepartmentID(1L)).thenReturn(department);
        when(uservice.getUserByID(1L)).thenReturn(user);
        // when(utility.getCurrentUserId()).thenReturn(1L);

        service.updateEmployee(employee, employeeDto);

        verify(repository).save(employee);
        assertEquals("John Doe", employee.getHoten());
        assertEquals("2000-01-01", employee.getNgaysinh());
        assertEquals("Hanoi", employee.getQuequan());
        assertEquals("Male", employee.getGt());
        assertEquals("Kinh", employee.getDantoc());
        assertEquals("0123456789", employee.getSdt());
        assertEquals("john.doe@example.com", employee.getEmail());
        assertEquals("Developer", employee.getChucvu());
        assertEquals(department, employee.getIdpb());
        // assertEquals(user, employee.getIduser());
    }

    @Test
    void testSearchfunc_shouldreturnEmployeeinfor(){
        String keyword = "Ngan";
        Long iduser = 1L;
        List<Employee> expectedList = Arrays.asList(employee2);
        
        List<Employee> actualList = service.findAll(keyword, iduser);

        assertEquals(expectedList, actualList);
        verify(repository, times(1)).findAllbyiduser(iduser, keyword);
    }
}
