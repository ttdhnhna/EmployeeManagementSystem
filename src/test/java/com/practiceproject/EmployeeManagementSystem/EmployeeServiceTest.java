package com.practiceproject.EmployeeManagementSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.practiceproject.EmployeeManagementSystem.entity.Department;
import com.practiceproject.EmployeeManagementSystem.entity.Employee;
import com.practiceproject.EmployeeManagementSystem.entity.EmployeeDto;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.repository.DepartmentRepository;
import com.practiceproject.EmployeeManagementSystem.repository.EmployeeRepository;
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
    private DepartmentService dservice;
    @Mock
    private AccountService uservice;
    @Mock
    private Utility utility;

    @InjectMocks//Tạo đối tượng này để các đối tượng mock có thể tác động tới đối tượng này(Dùng để tự động inject các mock đã tạo vào đối tượng mà bạn muốn kiểm thử).
    private EmployeeService service;

    @BeforeEach//Thiết lập các mock trước mỗi lần kiểm thử
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    /*@Test
    void testCreateEmployee() {
        Employee employee = new Employee("John", "Doe");
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee createdEmployee = employeeService.createEmployee(employee);

        assertNotNull(createdEmployee);
        assertEquals("John", createdEmployee.getFirstName());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testDeleteEmployee() {
        Employee employee = new Employee("John", "Doe");
        employee.setId(1L);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(employee);

        employeeService.deleteEmployee(1L);

        verify(employeeRepository, times(1)).delete(employee);
    }

    void testUpdateEmployee() throws IOException {
        // Prepare test data
        Department department = new Department();
        department.setIdpb(1L);
        User currentUser = new User();
        currentUser.setIduser(1L);
        department.setIduser(currentUser);

        Employee employee = new Employee();
        employee.setId(1L);

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

        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png", "image content".getBytes());
        employeeDto.setAnh(file);

        // Mock dependencies
        when(departmentService.getDepartmentID(1L)).thenReturn(department);
        when(userService.getUserByID(1L)).thenReturn(currentUser);
        when(Utility.getCurrentUserId()).thenReturn(1L);

        // Call the method
        employeeService.updateEmployee(employee, employeeDto);

        // Verify results
        verify(employeeRepository).save(employee);
        assertEquals("John Doe", employee.getHoten());
        assertEquals("2000-01-01", employee.getNgaysinh());
        assertEquals("Hanoi", employee.getQuequan());
        assertEquals("Male", employee.getGt());
        assertEquals("Kinh", employee.getDantoc());
        assertEquals("0123456789", employee.getSdt());
        assertEquals("john.doe@example.com", employee.getEmail());
        assertEquals("Developer", employee.getChucvu());
        assertEquals(department, employee.getIdpb());
        assertEquals(currentUser, employee.getIduser());
        assertEquals(Base64.getEncoder().encodeToString(file.getBytes()), employee.getAnh());
    }*/
    @Test
    @Transactional
    void testSaveEmployee(){
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
        when(repository.save(any(Employee.class))).thenReturn(employee);

        Employee savedEmployee = repository.save(employee);

        assertNotNull(savedEmployee);
        assertEquals("Dat", savedEmployee.getHoten());
        verify(repository, times(1));
    }

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
}
