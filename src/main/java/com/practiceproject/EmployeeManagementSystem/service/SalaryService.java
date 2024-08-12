package com.practiceproject.EmployeeManagementSystem.service;

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
    // @Transactional(readOnly = true)
    // public List<Salary> getSalaries(){
    //     return repository.findAll();
    // }

    //Luu luong
    public void saveSalary(Salary salary){
        float tl = (Salary.getLuongcb() * salary.getHsl() + salary.getPhucap()) - salary.getBaohiem() - salary.getTruluong();
        if(tl<=0){
            salary.setTongluong(0);
            salary.setTienno(0-tl);;
        }else{
            salary.setTongluong(tl);
            salary.setTienno(0);;
        }
        this.repository.save(salary);
    }

    //Tim id luong
    public Salary getSalaryID(long id){
        Optional<Salary> optional=repository.findById(id);
        Salary salary=null;
        if(optional.isPresent()){
            salary=optional.get();
        }else{
            throw new IllegalStateException("Không tìm thấy ID lương: "+id);
        }
        return salary;
    }

    //Phan trang va sap xep
    public Page<Salary> findPaginated(int pageNo, int pageSize, String sortField, String sortDir, Long iduser){
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
            Sort.by(sortField).descending();
        Pageable pageable=PageRequest.of(pageNo-1, pageSize, sort);
        User user = aService.getUserByID(iduser);
        return this.repository.findAllByIdnvIduser(user, pageable);
    }

    //Chức năng tìm kiếm theo keyword
    public List<Salary>findAllSalaries(String keyword, Long iduser){
        if(keyword!=null){
            return repository.findAllSalaries(iduser, keyword);
        }
        return Collections.emptyList();
    }
}
