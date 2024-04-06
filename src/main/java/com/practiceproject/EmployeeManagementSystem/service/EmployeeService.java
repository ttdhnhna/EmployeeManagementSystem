package com.practiceproject.EmployeeManagementSystem.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.practiceproject.EmployeeManagementSystem.entity.Employee;
import com.practiceproject.EmployeeManagementSystem.repository.EmployeeRepository;


@Service//Nó được sử dụng để đánh dấu lớp là nhà cung cấp dịch vụ
//Hay có thể nói là nó đánh dấu lớp nào sẽ thực hiện việc xử lý các hoạt động
public class EmployeeService {
    @Autowired //Được sử dụng để tự động Dependency Injection
    EmployeeRepository repository;//Hay có thể nói cách khác là giống như kế thừa các thuộc tính của lớp EmployeeRepository vào repository  
    //Để có thể sử dụng các chức năng của nó trong service.
    //Chức năng hiện tất cả nhân viên
    public List<Employee> getEmployees(){
        return repository.findAll();
    }
    //Lưu nhân viên
    public void saveEmployee(Employee employee, MultipartFile file){
        @SuppressWarnings("null")
        String filename=StringUtils.cleanPath(file.getOriginalFilename());
        if(filename.contains("..")){
            System.out.println("File không hợp lệ!");
        }
        try {
            employee.setAnh(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.repository.save(employee);
    }
    //Tìm nhân viên bằng id
    public Employee getEmployeebyID(long id){
        Optional<Employee> optional=repository.findById(id);
        Employee employee=null;
        if(optional.isPresent()){
            employee=optional.get();
        }else{
            throw new RuntimeException("Không tìm thấy id nhân viên: "+id);
        }
        return employee;
    }
    //Xóa nhân viên bằng id
    public void deleteEmployeebyID(long id){
        this.repository.deleteById(id);
    }
    //Phân trang và sắp xếp
    public Page<Employee> findPaginated(int pageNo,  int pageSize, String sortField, String sortDirection){
        Sort sort=sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
            Sort.by(sortField).descending();
        Pageable pageable=PageRequest.of(pageNo-1, pageSize, sort);
        return this.repository.findAll(pageable);
    }
    //Chức năng tìm kiếm theo keyword
    public List<Employee> findAll(String keyword){
        if(keyword!=null){
            return repository.findAll(keyword);
        }
        return repository.findAll();
    }
}

