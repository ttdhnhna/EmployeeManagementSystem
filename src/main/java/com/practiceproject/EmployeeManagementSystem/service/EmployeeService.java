package com.practiceproject.EmployeeManagementSystem.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.practiceproject.EmployeeManagementSystem.repository.DepartmentRepository;
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
    DepartmentRepository dRepository;
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
        User iduser = uService.getUserByID(Utility.getCurrentUserId());

        if (idpb == null) {
            throw new IllegalStateException("Phòng ban không tồn tại hoặc không hợp lệ!");
        }  
        if (!idpb.getIduser().getIduser().equals(Utility.getCurrentUserId())) {
            throw new IllegalStateException("ID phòng ban vừa nhập không khớp với người dùng hiện tại!");
        }

        if (file != null && !file.isEmpty()) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            if (filename.contains("..")) {
                throw new IllegalStateException("File không hợp lệ!");
            }
            try {
                employee.setAnh(Base64.getEncoder().encodeToString(file.getBytes()));
            } catch (IOException e) {
                throw new IllegalStateException("Lỗi khi đọc file ảnh", e);
            }
        } else {
            employee.setAnh(null); 
        }

        employee.setHoten(hoten);
        employee.setNgaysinh(ngaysinh);
        employee.setQuequan(quequan);
        employee.setGt(gt);
        employee.setDantoc(dantoc);
        employee.setSdt(sdt);
        employee.setEmail(email);
        employee.setChucvu(chucvu);
        employee.setIduser(iduser);
        employee.setIdpb(idpb);

        salary.setHsl(hsl);
        salary.setPhucap(phucap);
        Salary savedSalary = sRepository.save(salary);
        
        employee.setIdluong(savedSalary);
        Employee savedEmployee =  this.repository.save(employee);

        savedSalary.setIdnv(savedEmployee);
        sRepository.save(savedSalary);
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
        if (idpb.getIduser().getIduser().equals(Utility.getCurrentUserId())) {
            employee.setIdpb(idpb);
        } else {
            throw new IllegalStateException("ID phòng ban vừa nhập không tồn tại!");
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
            throw new IllegalStateException("Không tìm thấy ID nhân viên: "+id);
        }
        return employee;
    }

    //Xóa nhân viên bằng id
    public void deleteEmployeebyID(long id){
        Employee employee = getEmployeebyID(id);
        sRepository.deleteById(employee.getIdluong().getIdluong());
        this.repository.deleteById(id);
    }

    //Phân trang và sắp xếp
    public Page<Employee> findPaginated(int pageNo,  int pageSize, String sortField, String sortDirection, Long iduser){
        Sort sort=sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
            Sort.by(sortField).descending();
        Pageable pageable=PageRequest.of(pageNo-1, pageSize, sort);
        User user = uService.getUserByID(iduser);
        return this.repository.findAllByIduser(user, pageable);
    }

    //Chức năng tìm kiếm theo keyword
    public List<Employee> findAll(String keyword, Long iduser){
        if(keyword!=null){
            return repository.findAllbyiduser(iduser, keyword);
        }
        return Collections.emptyList();
    }

    //Chức năng tải file excel
    public void generateExcel(HttpServletResponse response) throws IOException{
        List<Employee> employees = repository.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Employee Info");
        HSSFRow row = sheet.createRow(0);

        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("Họ tên");
        row.createCell(2).setCellValue("Ngày sinh");
        row.createCell(3).setCellValue("Quê quán");
        row.createCell(4).setCellValue("Giới tính");
        row.createCell(5).setCellValue("Dân tộc");
        row.createCell(6).setCellValue("SDT");
        row.createCell(7).setCellValue("Email");
        row.createCell(8).setCellValue("Chức vụ");

        int dataRowIndex = 1;
        for(Employee e : employees){
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(e.getIdnv());
            dataRow.createCell(1).setCellValue(e.getHoten());
            dataRow.createCell(2).setCellValue(e.getNgaysinh());
            dataRow.createCell(3).setCellValue(e.getQuequan());
            dataRow.createCell(4).setCellValue(e.getGt());
            dataRow.createCell(5).setCellValue(e.getDantoc());
            dataRow.createCell(6).setCellValue(e.getSdt());
            dataRow.createCell(7).setCellValue(e.getEmail());
            dataRow.createCell(8).setCellValue(e.getChucvu());
            dataRowIndex++;
        }
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void uploadExcel(MultipartFile file) throws IOException{
        Long iduser = Utility.getCurrentUserId();
        if(file.isEmpty() || file==null){
            throw new IllegalStateException("Không tìm thấy file. Vui lòng chọn file để tải lên.");
        }
        try (InputStream inputStream = file.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                String hoten = getCellValue(row.getCell(0));
                String ngaysinh = getCellValue(row.getCell(1));
                String quequan = getCellValue(row.getCell(2));
                String gt = getCellValue(row.getCell(3));
                String dantoc = getCellValue(row.getCell(4));

                Cell sdtCell = row.getCell(5);
                String sdt = sdtCell.getCellType() == CellType.NUMERIC ? String.valueOf((long) sdtCell.getNumericCellValue()) : sdtCell.getStringCellValue();
                
                String email = getCellValue(row.getCell(6));
                String chucvu = getCellValue(row.getCell(7));
                String tenpb = getCellValue(row.getCell(8));
                String diachi = getCellValue(row.getCell(9));

                Cell sdtpbCell = row.getCell(10);
                String sdtpb = sdtpbCell.getCellType() == CellType.NUMERIC ? String.valueOf((long) sdtpbCell.getNumericCellValue()) : sdtpbCell.getStringCellValue();
                
                float hsl = (float) row.getCell(11).getNumericCellValue();
                float phucap = (float) row.getCell(12).getNumericCellValue();
                
                if (this.repository.findByHoten(iduser, hoten) != null) {
                    continue; // Nếu đã tồn tại nhân viên, bỏ qua dòng này
                }
                
                Department idpb = dRepository.findByTenpb(iduser, tenpb);
                //Nếu chưa tồn tại phòng ban thì khởi tạo phòng ban mới dựa theo thông tin đưa vào
                if (idpb == null) {
                    idpb = new Department();
                    idpb.setIduser(uService.getUserByID(Utility.getCurrentUserId())); 
                    idpb.setDiachi(diachi);
                    idpb.setSdt(sdtpb);
                    idpb.setTenpb(tenpb);
                    idpb = dRepository.save(idpb);
                    System.out.println("Phòng ban có tên: " + idpb.getTenpb());
                }
                
                saveEmployee(hoten, ngaysinh, quequan, gt, dantoc, sdt, email, chucvu, idpb, null, hsl, phucap);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Lỗi khi đọc file Excel", e);
        }
    }

    private String getCellValue(Cell cell) {//Đề phòng với dữ liệu đầu vào là null thì sẽ lưu dữ liệu vào là rỗng
        return cell != null ? cell.getStringCellValue() : "";
    }
}

