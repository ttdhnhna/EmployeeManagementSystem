package com.practiceproject.EmployeeManagementSystem.service;

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
import com.practiceproject.EmployeeManagementSystem.entity.Salary;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.entity.AuditLog.Act;
import com.practiceproject.EmployeeManagementSystem.entitydto.SalaryDto;
import com.practiceproject.EmployeeManagementSystem.repository.SalaryRepository;

@Service
public class SalaryService {
    @Autowired
    SalaryRepository repository;
    @Autowired
    EntityChangesService eService;
    @Autowired
    AccountService uService;
    @Autowired
    MessageSource messageSource;

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
        SalaryDto oldSalary = getSalaryDto(getSalaryID(salary.getIdluong()));
        User iduser = uService.getUserByID(Utility.getCurrentUserId());
        float tl = (Salary.getLuongcb() * salary.getHsl() + salary.getPhucap()) - salary.getBaohiem() - salary.getTruluong();
        if(tl<=0){
            salary.setTongluong(0);
            salary.setTienno(0-tl);;
        }else{
            salary.setTongluong(tl);
            salary.setTienno(0);;
        }
        Salary savedSalary = this.repository.save(salary);
        SalaryDto newSalary = getSalaryDto(savedSalary);
        AuditLog auditLog = eService.updateAuditOperation(iduser, null, savedSalary.getIdluong(), null, Act.UPDATE);
        eService.trackChanges(oldSalary, newSalary, auditLog);
    }

    //Tim id luong
    public Salary getSalaryID(long id){
        Optional<Salary> optional=repository.findById(id);
        Salary salary=null;
        String mess = messageSource.getMessage("cantfindidluong", new Object[] { id }, LocaleContextHolder.getLocale());
        
        if(optional.isPresent()){
            salary=optional.get();
        }else{
            throw new IllegalStateException(mess);
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

    public SalaryDto getSalaryDto(Salary salary){
        SalaryDto salaryDto = new SalaryDto();
        salaryDto.setHsl(salary.getHsl());
        salaryDto.setPhucap(salary.getPhucap());
        salaryDto.setBaohiem(salary.getBaohiem());
        salaryDto.setTruluong(salary.getTruluong());
        return salaryDto;
    }
}
