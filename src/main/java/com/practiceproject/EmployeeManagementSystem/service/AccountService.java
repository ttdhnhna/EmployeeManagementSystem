package com.practiceproject.EmployeeManagementSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.practiceproject.EmployeeManagementSystem.repository.UserRepository;
import com.practiceproject.EmployeeManagementSystem.entity.User;

@Service
public class AccountService {
    @Autowired
    UserRepository repository;

    public List<User> getAccounts(){
        return repository.findAll();
    }

    public User getUserByID(long id){
        Optional<User> optional=repository.findById(id);
        User user=null;
        if(optional.isPresent()){
            user=optional.get();
        }else{
            throw new RuntimeException("Không tìm thấy id tài khoản: "+id);
        }
        return user;
    }

    @SuppressWarnings("null")
    public void saveAccount(User user){
        this.repository.save(user);
    }

    public void deleteAccountById(long id){
        this.repository.deleteById(id);
    }

    public List<User> findAllUsers(String keyword){
        if(keyword!=null){
            return repository.findAllUsers(keyword);
        }
        return repository.findAll();
    }

    public Page<User> findPaginatedAcc(int pageNo, int pageSize, String sortField, String sortDirection){
        Sort sort=sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
            Sort.by(sortField).descending();
        Pageable pageable=PageRequest.of(pageNo-1, pageSize, sort);
        return this.repository.findAll(pageable);
    }
}
