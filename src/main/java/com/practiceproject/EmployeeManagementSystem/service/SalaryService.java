package com.practiceproject.EmployeeManagementSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.practiceproject.EmployeeManagementSystem.entity.Salary;
import com.practiceproject.EmployeeManagementSystem.repository.SalaryRepository;

@Service
public class SalaryService {
    @Autowired
    SalaryRepository repository;
    //Hien ds luong
    public List<Salary> getSalaries(){
        return repository.findAll();
    }
    //Luu luong
    public void saveSalary(Salary salary){
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
    public Page<Salary> findPaginated(int pageNo, int pageSize, String sortField, String sortDir){
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
            Sort.by(sortField).descending();
        Pageable pageable=PageRequest.of(pageNo-1, pageSize, sort);
        return this.repository.findAll(pageable);
    }
    //Chức năng tìm kiếm theo keyword
    public List<Salary>findAllSalaries(String keyword){
        if(keyword!=null){
            return repository.findAllSalaries(keyword);
        }
        return repository.findAll();
    }
}
