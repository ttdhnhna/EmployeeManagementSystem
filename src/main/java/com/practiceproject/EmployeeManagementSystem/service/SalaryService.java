package com.practiceproject.EmployeeManagementSystem.service;

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
import com.practiceproject.EmployeeManagementSystem.entity.Salary;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.entity.AuditLog.Act;
import com.practiceproject.EmployeeManagementSystem.repository.SalaryRepository;

@Service
public class SalaryService {
    @Autowired
    SalaryRepository repository;
    @Autowired
    EntityChangesService eService;
    @Autowired
    AccountService uService;

    //Hien ds luong
    // @Transactional(readOnly = true)
    // public List<Salary> getSalaries(){
    //     return repository.findAll();
    // }

    //Luu luong
    @Transactional
    public void saveSalary(Salary salary){
        User iduser = uService.getUserByID(Utility.getCurrentUserId());
        float tl = (Salary.getLuongcb() * salary.getHsl() + salary.getPhucap()) - salary.getBaohiem() - salary.getTruluong();
        if(tl<=0){
            salary.setTongluong(0);
            salary.setTienno(0-tl);;
        }else{
            salary.setTongluong(tl);
            salary.setTienno(0);;
        }
        Salary saveSalary = this.repository.save(salary);

        eService.logAuditOperation(iduser, null, saveSalary.getIdluong(), null, Act.ADD);
    }

    @Transactional
    public void updateSalary(Salary salary){
        Salary oldSalary = getSalaryID(salary.getIdluong());
        User iduser = uService.getUserByID(Utility.getCurrentUserId());
        float tl = (Salary.getLuongcb() * salary.getHsl() + salary.getPhucap()) - salary.getBaohiem() - salary.getTruluong();
        if(tl<=0){
            salary.setTongluong(0);
            salary.setTienno(0-tl);;
        }else{
            salary.setTongluong(tl);
            salary.setTienno(0);;
        }
        Salary saveSalary = this.repository.save(salary);

        AuditLog savedLog = eService.updateAuditOperation(iduser, null, saveSalary.getIdluong(), null, Act.UPDATE);
        eService.trackChanges(oldSalary, saveSalary, savedLog);
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
        User user = uService.getUserByID(iduser);
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
