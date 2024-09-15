package com.practiceproject.EmployeeManagementSystem.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
import com.practiceproject.EmployeeManagementSystem.entitydto.DepartmentDto;
import com.practiceproject.EmployeeManagementSystem.repository.DepartmentRepository;
import com.practiceproject.EmployeeManagementSystem.repository.EmployeeRepository;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository repository;
    @Autowired
    EmployeeRepository eRepository;
    @Autowired
    EntityChangesService eService;
    @Autowired
    AccountService aService;
    @Autowired
    MessageSource messageSource;

    // @Transactional(readOnly = true)
    // public List<Department> getDepartments(){
    //     return repository.findAll();
    // }
    @Transactional
    public void saveDepartment(Department department){
        User idUser = aService.getUserByID(Utility.getCurrentUserId());
        department.setIduser(idUser);
        Department savedDepartment = this.repository.save(department);
        eService.logAuditOperation(idUser, null, null, savedDepartment.getIdpb(), Act.ADD);
    }

    @Transactional
    public void updateDepartment(Department department){
        User idUser = aService.getUserByID(Utility.getCurrentUserId());
        DepartmentDto oldDepartment = getDepartmentDto(getDepartmentID(department.getIdpb()));
        department.setIduser(idUser);
        Department savedDepartment = this.repository.save(department);
        DepartmentDto newDepartment = getDepartmentDto(savedDepartment);
        AuditLog savedAuditlog = eService.updateAuditOperation(idUser, null, null, savedDepartment.getIdpb(), Act.UPDATE);
        eService.trackChanges(oldDepartment, newDepartment, savedAuditlog);
    }

    public Department getDepartmentID(long id){
        Optional<Department> optional=repository.findById(id);
        Department department=null;
        String mess = messageSource.getMessage("cantfindidpb", new Object[] { id }, LocaleContextHolder.getLocale());
       
        if(optional.isPresent()){
            department=optional.get();
        }else{
            throw new IllegalStateException(mess);
        }
        return department;
    }

    @Transactional
    public void deleteDepartmentID(long id){
        User idUser = aService.getUserByID(Utility.getCurrentUserId());
        List<Employee> list = getNVInformationbyID(id);
        if(list != null){
            for(Employee e : list){
                eService.logAuditOperation(idUser, e.getIdnv(), null, null, Act.DELETE);
                eService.logAuditOperation(idUser, null, e.getIdluong().getIdluong(), null, Act.DELETE);
                eRepository.delete(e); 
            }
        }
        this.repository.deleteById(id);
        eService.logAuditOperation(idUser, null, null, id, Act.DELETE);
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

    public DepartmentDto getDepartmentDto(Department department){
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setTenpb(department.getTenpb());
        departmentDto.setDiachi(department.getDiachi());
        departmentDto.setSdt(department.getSdt());
        return departmentDto;
    }
}
