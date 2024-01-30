package com.practiceproject.EmployeeManagementSystem.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.practiceproject.EmployeeManagementSystem.entity.Department;
import com.practiceproject.EmployeeManagementSystem.entity.Employee;
import com.practiceproject.EmployeeManagementSystem.repository.DepartmentRepository;
import com.practiceproject.EmployeeManagementSystem.repository.EmployeeRepository;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository repository;
    @Autowired
    EmployeeRepository eRepository;

    public List<Department> getDepartments(){
        return repository.findAll();
    }

    public void saveDepartment(Department department){
        this.repository.save(department);
    }

    public Department getDepartmentID(long id){
        Optional<Department> optional=repository.findById(id);
        Department department=null;
        if(optional.isPresent()){
            department=optional.get();
        }else{
            throw new RuntimeException("Khong tim thay id phong ban: "+id);
        }
        return department;
    }

    public void deleteDepartmentID(long id){
        this.repository.deleteById(id);
    }

    public Page<Department> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection){
        Sort sort=sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
            Sort.by(sortField).descending();
        Pageable pageable=PageRequest.of(pageNo-1, pageSize, sort);
        return this.repository.findAll(pageable);
    }

    //Chức năng tìm kiếm theo keyword
    public List<Department> findDepartments(String keyword){
        if(keyword!=null){
            return repository.findAllDepartments(keyword);
        }
        return repository.findAll();
    }

    //Tìm kiếm danh sách nhân viên theo idpb
    public List<Employee> getNVInformationbyID(long id){
        List<Employee> ListEmployees=new ArrayList<>();
        for(Employee employee : eRepository.findAll()){
            if(employee.getIdpb().getIdpb()==id){
                ListEmployees.add(employee);
            }
        }
        return ListEmployees;
    }
}
