package com.practiceproject.EmployeeManagementSystem.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.practiceproject.EmployeeManagementSystem.entity.AuditLog;
import com.practiceproject.EmployeeManagementSystem.entity.Department;
import com.practiceproject.EmployeeManagementSystem.entity.Employee;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.entity.AuditLog.Act;
import com.practiceproject.EmployeeManagementSystem.repository.AuditLogRepository;
import com.practiceproject.EmployeeManagementSystem.repository.DepartmentRepository;
import com.practiceproject.EmployeeManagementSystem.repository.EmployeeRepository;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository repository;
    @Autowired
    EmployeeRepository eRepository;
    @Autowired
    AuditLogRepository aRepository;
    @Autowired
    EmployeeService eService;
    @Autowired
    AccountService aService;

    // @Transactional(readOnly = true)
    // public List<Department> getDepartments(){
    //     return repository.findAll();
    // }
    @Transactional
    public void saveDepartment(Department department){
        User idUser = aService.getUserByID(Utility.getCurrentUserId());
        department.setIduser(idUser);
        Department savedDepartment = this.repository.save(department);
        logAuditOperation(idUser, savedDepartment.getIdpb(), Act.ADD);
    }

    @Transactional
    public void updateDepartment(Department department){
        User idUser = aService.getUserByID(Utility.getCurrentUserId());
        department.setIduser(idUser);
        Department savedDepartment = this.repository.save(department);
        logAuditOperation(idUser, savedDepartment.getIdpb(), Act.UPDATE);
    }

    public Department getDepartmentID(long id){
        Optional<Department> optional=repository.findById(id);
        Department department=null;
        if(optional.isPresent()){
            department=optional.get();
        }else{
            throw new IllegalStateException("Không tìm thấy ID phòng ban: "+id);
        }
        return department;
    }

    @Transactional
    public void deleteDepartmentID(long id){
        User idUser = aService.getUserByID(Utility.getCurrentUserId());
        List<Employee> list = getNVInformationbyID(id);
        if(list != null){
            for(Employee e : list){
                eService.deleteEmployeebyID(e.getIdnv());
            }
        }
        this.repository.deleteById(id);
        logAuditOperation(idUser, id, Act.DELETE);
    }

    public Page<Department> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, Long iduser){
        Sort sort=sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
            Sort.by(sortField).descending();
        Pageable pageable=PageRequest.of(pageNo-1, pageSize, sort);
        User user = aService.getUserByID(iduser);
        return this.repository.findAllByiduser(user,pageable);
    }

    //Chức năng tìm kiếm theo keyword
    public List<Department> findDepartments(String keyword, Long iduser){
        if(keyword!=null){
            return repository.findAllDepartments(iduser, keyword);
        }
        return Collections.emptyList();
    }

    //Chức năng lấy thông tin nhân viên có cùng id phòng ban
    public List<Employee> getNVInformationbyID(long id){
        List<Employee> ListEmployees=new ArrayList<>();
        for(Employee employee : eRepository.findAll()){
            if(employee.getIdpb().getIdpb()==id){
                ListEmployees.add(employee);
            }
        }
        return ListEmployees;
    }

    public void logAuditOperation(User user, Long department, Act action){
        AuditLog auditLog = new AuditLog();
        auditLog.setIduser(user);
        auditLog.setIdpb(department);
        auditLog.setAct(action);
        aRepository.save(auditLog);
    }
}
