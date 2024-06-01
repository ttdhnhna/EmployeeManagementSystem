package com.practiceproject.EmployeeManagementSystem.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.practiceproject.EmployeeManagementSystem.entity.Salary;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.repository.SalaryRepository;

@Service
public class SalaryService {
    @Autowired
    SalaryRepository repository;
    @Autowired
    AccountService aService;
    //Hien ds luong
    public List<Salary> getSalaries(){
        return repository.findAll();
    }
    //Hien ds luong theo id nguoi dang nhap
    public List<Salary> getSalarybyUser(long id){
        List<Salary> listsalary = new ArrayList<>();
        for(Salary s : repository.findAll()){
            if(s.getIduser().getIduser() == id){
                listsalary.add(s);
            }
        }
        return listsalary;
    }

    //Luu luong
    public void saveSalary(Salary salary){
        User iduser = aService.getUserByID(Utility.getCurrentUserId());
        salary.setIduser(iduser);
        this.repository.save(salary);
    }
    //Tim id luong
    public Salary getSalaryID(long id){
        Optional<Salary> optional=repository.findById(id);
        Salary salary=null;
        if(optional.isPresent()){
            salary=optional.get();
        }else{
            throw new RuntimeException("Không tìm thấy id lương: "+id);
        }
        return salary;
    }
    //Xoa
    public void deleteSalarybyID(long id){
        this.repository.deleteById(id);
    }
    //Phan trang va sap xep
    public Page<Salary> findPaginated(int pageNo, int pageSize, String sortField, String sortDir, Long iduser){
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
            Sort.by(sortField).descending();
        Pageable pageable=PageRequest.of(pageNo-1, pageSize, sort);
        User user = aService.getUserByID(iduser);
        return this.repository.findAllByiduser(user, pageable);
    }
    //Chức năng tìm kiếm theo keyword
    public List<Salary>findAllSalaries(String keyword, Long iduser){
        User user = aService.getUserByID(iduser);
        if(keyword!=null){
            return repository.findAllSalaries(user.getIduser(), keyword);
        }
        return Collections.emptyList();
    }
}
