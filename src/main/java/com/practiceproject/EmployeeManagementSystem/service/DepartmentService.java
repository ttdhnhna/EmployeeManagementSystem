package com.practiceproject.EmployeeManagementSystem.service;

// import java.util.HashSet;
import java.util.List;
// import java.util.Map;
import java.util.Optional;
// import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.practiceproject.EmployeeManagementSystem.entity.Department;
// import com.practiceproject.EmployeeManagementSystem.entity.Employee;
import com.practiceproject.EmployeeManagementSystem.repository.DepartmentRepository;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository repository;
    public List<Department> getDepartments(){
        return repository.findAll();
    }

    public void saveDepartment(Department department){
        this.repository.save(department);
    }

    public Department getDepartmentID(long id){
        Optional<Department> optional=repository.findById(null);
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

    // public Map<Long,List<Employee>> getNVInformationbyID(){
    //     return repository.getNVInformationbyID();
    // }

    // public void updateIdNV(long id) {
    //     Department department = repository.findById(id).orElse(null);
    //     if (department != null) {
    //         Set<Long> listidnv = new HashSet<>();
    //         for (Employee employee : department.getIdnv()) {
    //             listidnv.add(employee.getIdnv());
    //         }
    //         department.setIdnv(listidnv);
    //         repository.save(department);
    //     }
    // }
}
