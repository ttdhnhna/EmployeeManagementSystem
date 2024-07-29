package com.practiceproject.EmployeeManagementSystem.service;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.practiceproject.EmployeeManagementSystem.entity.Department;
import com.practiceproject.EmployeeManagementSystem.entity.Employee;
import com.practiceproject.EmployeeManagementSystem.entity.EmployeeDto;
import com.practiceproject.EmployeeManagementSystem.entity.Salary;
import com.practiceproject.EmployeeManagementSystem.entity.User;
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
    @Autowired
    DepartmentService dService;
    @Autowired
    AccountService uService;

    //Chức năng hiện tất cả nhân viên
    // @Transactional(readOnly = true)
    // public List<Employee> getEmployees(){
    //     return repository.findAll();
    // }
    
    //Lưu nhân viên
    @SuppressWarnings("null")
    public void saveEmployee(String hoten, String ngaysinh, 
    String quequan, String gt, String dantoc, String sdt,
    String email, String chucvu,
    Department idpb,
    MultipartFile file,
    float hsl,
    float phucap){
        Employee employee=new Employee();
        Salary salary=new Salary();

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
        if (idpb.getIduser().getIduser().equals(Utility.getCurrentUserId()) || idpb.equals(null)) {
            employee.setIdpb(idpb);
        } else {
            throw new IllegalStateException("ID phòng ban vừa nhập không tồn tại!");
        }

        salary.setHsl(hsl);
        salary.setPhucap(phucap);
        Salary savedSalary = sRepository.save(salary);
        
        employee.setIdluong(savedSalary);
        Employee savedEmployee =  this.repository.save(employee);

        savedSalary.setIdnv(savedEmployee);
        sRepository.save(savedSalary);
    }
    
    //Cập nhật nhân viên
    @Transactional
    public void updateEmployee(Employee employee, EmployeeDto employeeDto){
        MultipartFile file = employeeDto.getAnh();
        if (file != null && !file.isEmpty()) {
            @SuppressWarnings("null")
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            if (filename.contains("..")) {
                throw new IllegalStateException("File không hợp lệ!");
            }
            try {
                employee.setAnh(Base64.getEncoder().encodeToString(file.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        employee.setHoten(employeeDto.getHoten());
        employee.setNgaysinh(employeeDto.getNgaysinh());
        employee.setQuequan(employeeDto.getQuequan());
        employee.setGt(employeeDto.getGt());
        employee.setDantoc(employeeDto.getDantoc());
        employee.setSdt(employeeDto.getSdt());
        employee.setEmail(employeeDto.getEmail());
        employee.setChucvu(employeeDto.getChucvu());
        Department idpb = dService.getDepartmentID(employeeDto.getIdpb());
        if (idpb.getIduser().getIduser().equals(Utility.getCurrentUserId())) {
            employee.setIdpb(idpb);
        } else {
            throw new IllegalStateException("ID phòng ban vừa nhập không tồn tại!");
        }

        this.repository.save(employee); 
    }
    
    //Tìm nhân viên bằng id
    @Transactional(readOnly = true)
    public Employee getEmployeebyID(long id){
        Optional<Employee> optional=repository.findById(id);
        Employee employee=null;
        if(optional.isPresent()){
            employee=optional.get();
        }else{
            throw new IllegalStateException("Không tìm thấy ID nhân viên: "+id);
        }
        return employee;
    }

    //Xóa nhân viên bằng id
    @Transactional
    public void deleteEmployeebyID(long id){
        Employee employee = getEmployeebyID(id);
        sRepository.deleteById(employee.getIdluong().getIdluong());
        this.repository.deleteById(id);
    }

    //Phân trang và sắp xếp
    @Transactional(readOnly = true)
    public Page<Employee> findPaginated(int pageNo,  int pageSize, String sortField, String sortDirection, Long iduser){
        Sort sort=sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
            Sort.by(sortField).descending();
        Pageable pageable=PageRequest.of(pageNo-1, pageSize, sort);
        User user = uService.getUserByID(iduser);
        return this.repository.findAllByIdpbIduser(user, pageable);
    }

    //Chức năng tìm kiếm theo keyword
    @Transactional(readOnly = true)
    public List<Employee> findAll(String keyword, Long iduser){
        if(keyword!=null){
            return repository.findAllbyiduser(iduser, keyword);
        }
        return Collections.emptyList();
    }
}

