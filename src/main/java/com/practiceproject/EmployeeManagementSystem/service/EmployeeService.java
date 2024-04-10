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

import com.practiceproject.EmployeeManagementSystem.entity.Department;
import com.practiceproject.EmployeeManagementSystem.entity.Employee;
import com.practiceproject.EmployeeManagementSystem.entity.Salary;
import com.practiceproject.EmployeeManagementSystem.repository.EmployeeRepository;
import com.practiceproject.EmployeeManagementSystem.repository.SalaryRepository;


@Service//Nó được sử dụng để đánh dấu lớp là nhà cung cấp dịch vụ
//Hay có thể nói là nó đánh dấu lớp nào sẽ thực hiện việc xử lý các hoạt động
public class EmployeeService {
    @Autowired //Được sử dụng để tự động Dependency Injection
    EmployeeRepository repository;//Hay có thể nói cách khác là giống như kế thừa các thuộc tính của lớp EmployeeRepository vào repository  
    //Để có thể sử dụng các chức năng của nó trong service.
    @Autowired
    SalaryRepository sRepository;

    //Chức năng hiện tất cả nhân viên
    public List<Employee> getEmployees(){
        return repository.findAll();
    }
    //Lưu nhân viên
    @SuppressWarnings("null")
    public void saveEmployee(String hoten, String ngaysinh, 
    String quequan, String gt, String dantoc, String sdt,
    String email, String chucvu,
    Department idpb,
    Salary idluong,
    MultipartFile file){
        Employee employee=new Employee();
        String filename=StringUtils.cleanPath(file.getOriginalFilename());
        if(filename.contains("..")){
            System.out.println("File không hợp lệ!");
        }
        try {
            employee.setAnh(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        employee.setHoten(hoten);
        employee.setNgaysinh(ngaysinh);
        employee.setQuequan(quequan);
        employee.setGt(gt);
        employee.setDantoc(dantoc);
        employee.setSdt(sdt);
        employee.setEmail(email);
        employee.setChucvu(chucvu);
        employee.setIdpb(idpb);
        employee.setIdluong(idluong);
        this.repository.save(employee);
    }
    //Chức năng cập nhật nhân viên riêng.
    public void updateEmployee(Employee employee){
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

    //Chức năng lấy thông tin lương cho nhân viên.
    public Salary getsalaryInfo(long id){
        Salary salaryinfo=new Salary();
        for(Salary s : sRepository.findAll()){
            if(s.getIdnv().getIdnv()==id){
                salaryinfo=s;
            }
        }
        return salaryinfo;
    }
}

