package com.practiceproject.EmployeeManagementSystem.service;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.practiceproject.EmployeeManagementSystem.entity.Department;
import com.practiceproject.EmployeeManagementSystem.entity.Employee;
import com.practiceproject.EmployeeManagementSystem.entity.EmployeeDto;
import com.practiceproject.EmployeeManagementSystem.entity.Salary;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.repository.EmployeeRepository;
import com.practiceproject.EmployeeManagementSystem.repository.SalaryRepository;
import com.practiceproject.EmployeeManagementSystem.repository.UserRepository;


@Service//Nó được sử dụng để đánh dấu lớp là nhà cung cấp dịch vụ
//Hay có thể nói là nó đánh dấu lớp nào sẽ thực hiện việc xử lý các hoạt động
public class EmployeeService {
    @Autowired //Được sử dụng để tự động Dependency Injection
    EmployeeRepository repository;//Hay có thể nói cách khác là giống như kế thừa các thuộc tính của lớp EmployeeRepository vào repository  
    //Để có thể sử dụng các chức năng của nó trong service.
    @Autowired
    SalaryRepository sRepository;
    @Autowired
    UserRepository uRepository;
    @Autowired
    DepartmentService dService;
    @Autowired
    SalaryService sService;
    @Autowired
    AccountService uService;

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
    User iduser,
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
        iduser = uService.getUserByID(Utility.getCurrentUserId());
        employee.setHoten(hoten);
        employee.setNgaysinh(ngaysinh);
        employee.setQuequan(quequan);
        employee.setGt(gt);
        employee.setDantoc(dantoc);
        employee.setSdt(sdt);
        employee.setEmail(email);
        employee.setChucvu(chucvu);
        if (idpb.getIduser().getIduser().equals(Utility.getCurrentUserId()) && !idpb.equals(null)) {
            employee.setIdpb(idpb);
        } else {
            throw new IllegalStateException("ID phòng ban vừa nhập không tồn tại!");
        }
        if (idluong.getIduser().getIduser().equals(Utility.getCurrentUserId()) && idluong.getIdnv().getIdnv() == null) {
            employee.setIdluong(idluong);
        } else if(idluong.getIdnv().getIdnv() != null){
            throw new IllegalStateException("ID lương vừa nhập đã được sử dụng!");
        } else {
            throw new IllegalStateException("ID lương vừa nhập không tồn tại!");
        }
        employee.setIduser(iduser);

        this.repository.save(employee);
    }
    
    //Cập nhật nhân viên
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
        User iduser = uService.getUserByID(Utility.getCurrentUserId());
        Salary idluong  = sService.getSalaryID(employeeDto.getIdluong()); 
        if (idpb.getIduser().getIduser().equals(Utility.getCurrentUserId())) {
            employee.setIdpb(idpb);
        } else {
            throw new IllegalStateException("ID phòng ban vừa nhập không tồn tại!");
        }
        if (idluong != null && idluong.getIduser() != null && idluong.getIduser().getIduser().equals(Utility.getCurrentUserId())) {
            if (idluong.getIdnv() == null || idluong.getIdnv().getIdnv() == null || idluong.getIdnv().getIdnv().equals(employee.getIdnv())) {
                employee.setIdluong(idluong);
            } else {
                throw new IllegalStateException("ID lương vừa nhập đã được sử dụng!");
            }
        } else {
            throw new IllegalStateException("ID lương vừa nhập không tồn tại!");
        }
        employee.setIduser(iduser);

        this.repository.save(employee); 
    }
    //Tìm nhân viên bằng id
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
    public void deleteEmployeebyID(long id){
        this.repository.deleteById(id);
    }
    //Phân trang và sắp xếp
    public Page<Employee> findPaginated(int pageNo,  int pageSize, String sortField, String sortDirection, Long iduser){
        Sort sort=sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
            Sort.by(sortField).descending();
        Pageable pageable=PageRequest.of(pageNo-1, pageSize, sort);
        User user = uService.getUserByID(iduser);
        return this.repository.findAllByiduser(user, pageable);
    }
    //Chức năng tìm kiếm theo keyword
    public List<Employee> findAll(String keyword, Long iduser){
        if(keyword!=null){
            return repository.findAllbyiduser(iduser, keyword);
        }
        return Collections.emptyList();
    }

    //Chức năng lấy thông tin lương cho nhân viên.
    public Salary getsalaryInfo(long id){
        Salary salaryinfo=new Salary();
        Long iduser = Utility.getCurrentUserId();
        Employee e = getEmployeebyID(id);
        for(Salary s : sRepository.findAll()){
            if(s.getIdluong().equals(e.getIdluong().getIdluong()) && s.getIduser().getIduser().equals(iduser)){
                salaryinfo=s;
            }
        }
        return salaryinfo;
    }

    public List<Employee> getEmployeebyUser(long id){
        List<Employee> lemployee = new ArrayList<>();
        for(Employee e : repository.findAll()){
            if(e.getIduser().getIduser()==id){
                lemployee.add(e);
            }
        }
        return lemployee;
    }
}

