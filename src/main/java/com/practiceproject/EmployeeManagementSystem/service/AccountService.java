package com.practiceproject.EmployeeManagementSystem.service;

import java.util.List;
import java.util.Optional;

// import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    public void changePassword(String currentpass, String newpass, String comfirm, User user){
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        if (currentpass == null) {
            throw new IllegalArgumentException("Không lấy được Mật khẩu cũ");
        }else if (newpass == null) {
            throw new IllegalArgumentException("Không lấy được Mật khẩu mới");
        }else if (comfirm == null) {
            throw new IllegalArgumentException("Không lấy được Mật khẩu nhắc lại");
        }
        if(!passwordEncoder.matches(currentpass, user.getPassword())){
            throw new IllegalStateException("Sai mật khẩu");
        }
        if(passwordEncoder.matches(newpass, user.getPassword())){
            throw new IllegalStateException("Mật khẩu mới phải khác mật khẩu cũ");
        }
        String encodedPass = passwordEncoder.encode(newpass); 
        user.setPassword(encodedPass);
        repository.save(user);
    }

    public void updateResetPass(String tokem, String email) throws CustomerNotFoundException{
        User user = repository.findbyEmail(email);
        if(user != null){
            user.setResetPassToken(tokem);
            repository.save(user);
        }else{
            throw new CustomerNotFoundException ("Không thể tìm thấy người dùng với email: "  + email);
        }
    }

    public User get(String resetPassToken){
        return repository.findByResetPassToken(resetPassToken);
    }

    public void updatePassword(User user, String newPass){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPass = passwordEncoder.encode(newPass); 

        user.setPassword(encodedPass);
        user.setResetPassToken(null);

        repository.save(user);
    }
}
