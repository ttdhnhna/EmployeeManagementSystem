package com.practiceproject.EmployeeManagementSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.practiceproject.EmployeeManagementSystem.entity.Department;
import com.practiceproject.EmployeeManagementSystem.entity.Employee;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.repository.DepartmentRepository;
import com.practiceproject.EmployeeManagementSystem.repository.EmployeeRepository;
import com.practiceproject.EmployeeManagementSystem.repository.UserRepository;
import com.practiceproject.EmployeeManagementSystem.service.EmployeeService;

@SpringBootTest
public class EmployeeServiceTest {
    @Mock//Tạo mock đối tượng repository
    private EmployeeRepository repository;
    @Mock
    private DepartmentRepository drepository;
    @Mock
    private UserRepository urepository;

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

    @Test
    void testUpdateEmployee() {
        Employee employee = new Employee("John", "Doe");
        employee.setId(1L);
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee updatedEmployee = new Employee("Jane", "Doe");
        updatedEmployee.setId(1L);

        Employee result = employeeService.updateEmployee(1L, updatedEmployee);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        verify(employeeRepository, times(1)).save(updatedEmployee);
    } */
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
        employee.setIduser(user);
        when(repository.save(any(Employee.class))).thenReturn(employee);

        Employee savedEmployee = repository.save(employee);

        assertNotNull(savedEmployee);
        assertEquals("Dat", savedEmployee.getHoten());
        verify(repository, times(1));
    }
}
