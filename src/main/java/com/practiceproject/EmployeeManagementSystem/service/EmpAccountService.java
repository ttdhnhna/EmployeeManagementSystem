package com.practiceproject.EmployeeManagementSystem.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.practiceproject.EmployeeManagementSystem.entity.Employee;
import com.practiceproject.EmployeeManagementSystem.entity.EmployeeAccount;
import com.practiceproject.EmployeeManagementSystem.repository.EmpAccountRepository;
import com.practiceproject.EmployeeManagementSystem.repository.EmployeeRepository;

@Service
public class EmpAccountService {
    @Autowired
    EmpAccountRepository repository;
    @Autowired
    EmployeeRepository eRepository;
    @Autowired
    MessageSource messageSource;
    @Autowired
    EmployeeService eService;

    @Transactional
    public void saveAccount(EmployeeAccount account){
        this.repository.save(account);
    }

    //Tim kiem dang thieu phan thong bao.
    public EmployeeAccount getAccID(long id){
        Optional<EmployeeAccount> optional = repository.findById(id);
        EmployeeAccount account = null;
        String mess = messageSource.getMessage("null", null, LocaleContextHolder.getLocale());
        if(optional.isPresent()){
            account = optional.get();
        }else{
            throw new IllegalAccessError(mess);
        }
        return account;
    }

    @Transactional
    public void deleteEmpAccbyID(long id){
        this.repository.deleteById(id);
    }

    @Transactional
    public void createEmpAccount(EmployeeAccount account, long id){
        String emailExistsMess = messageSource.getMessage("emailexists", null, LocaleContextHolder.getLocale());
        
        if(account.equals(null)){
            throw new IllegalStateException("null");
        }
        if(repository.findByEmail(account.getEmail()) != null){
            throw new IllegalStateException(emailExistsMess);
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String ePass = passwordEncoder.encode(account.getPassword());
        account.setPassword(ePass);
        Employee idnv = eService.getEmployeebyID(id);
        account.setIdnv(idnv);
        EmployeeAccount savedAccount = this.repository.save(account);

        idnv.setIdacc(savedAccount);
        eRepository.save(idnv);
    }
}
