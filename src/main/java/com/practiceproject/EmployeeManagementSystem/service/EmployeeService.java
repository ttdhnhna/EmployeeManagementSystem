package com.practiceproject.EmployeeManagementSystem.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practiceproject.EmployeeManagementSystem.entity.Employee;
import com.practiceproject.EmployeeManagementSystem.repository.EmployeeRepository;

@Service
//Được sử dụng với các lớp cung cấp các chức năng business
public class EmployeeService {
    @Autowired //Được sử dụng để tự động Dependency Injection
    EmployeeRepository repository;//Hay có thể nói cách khác là giống như kế thừa các thuộc tính của lớp EmployeeRepository vào repository  
    //Để có thể sử dụng các chức năng của nó trong service.
    public List<Employee> getEmployees(){
        return repository.findAll();
    }
    //Luu nhan vien
    public void saveEmployee(Employee employee){
        this.repository.save(employee);
    }
    //Tim nhan vien bang id
    public Employee getEmployeebyID(long id){
        Optional<Employee> optional=repository.findById(id);
        Employee employee=null;
        if(optional.isPresent()){
            employee=optional.get();
        }else{
            throw new RuntimeException("Khong tim thay id nhan vien: "+id);
        }
        return employee;
    }
    //Xoa nhan vien bang id
    public void deleteEmployeebyID(long id){
        this.repository.deleteById(id);
    }
}
/*public Employee getEmployeebyID(long id){
        Employee emp =repository.getById(null);
        if(emp==null){
            throw new RuntimeException("Khong tim thay id nhan vien: "+id);
        }
        return emp;
    }
*/
