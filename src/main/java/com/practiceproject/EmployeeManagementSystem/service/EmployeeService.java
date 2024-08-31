package com.practiceproject.EmployeeManagementSystem.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

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

import com.practiceproject.EmployeeManagementSystem.entity.AuditLog;
import com.practiceproject.EmployeeManagementSystem.entity.Department;
import com.practiceproject.EmployeeManagementSystem.entity.Employee;
import com.practiceproject.EmployeeManagementSystem.entity.EmployeeDto;
import com.practiceproject.EmployeeManagementSystem.entity.Salary;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.entity.AuditLog.Act;
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
    SalaryService sService;
    @Autowired
    DepartmentService dService;
    @Autowired
    AccountService uService;
    @Autowired
    EntityChangesService eService;

    //Chức năng hiện tất cả nhân viên
    // @Transactional(readOnly = true)
    // public List<Employee> getEmployees(){
    //     return repository.findAll();
    // }
    
    //Lưu nhân viên
    @SuppressWarnings("null")
    @Transactional
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
        sService.saveSalary(savedSalary);

        eService.logAuditOperation(iduser, savedEmployee.getIdnv(), null, null, Act.ADD);
    }
    
    //Cập nhật nhân viên
    @Transactional
    public void updateEmployee(Employee employee, EmployeeDto employeeDto){
        Employee oldEmployee = getEmployeebyID(employee.getIdnv());
        User iduser = uService.getUserByID(Utility.getCurrentUserId());
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

        Employee savedEmployee = this.repository.save(employee); 
        System.out.println("Old Name: " + oldEmployee.getHoten());
        System.out.println("New Name: " + savedEmployee.getHoten());
        AuditLog savedAuditlog = eService.updateAuditOperation(iduser, savedEmployee.getIdnv(), null, null, Act.UPDATE);
        eService.trackChanges(oldEmployee, savedEmployee, savedAuditlog);
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
    @Transactional
    public void deleteEmployeebyID(long id){
        User iduser = uService.getUserByID(Utility.getCurrentUserId());
        Employee employee = getEmployeebyID(id);
        sRepository.deleteById(employee.getIdluong().getIdluong());
        eService.logAuditOperation(iduser, null, employee.getIdluong().getIdluong(), null, Act.DELETE);
        this.repository.deleteById(id);
        eService.logAuditOperation(iduser, id, null, null, Act.DELETE);
    }

    //Phân trang và sắp xếp
    public Page<Employee> findPaginated(int pageNo,  int pageSize, String sortField, String sortDirection, Long iduser){
        Sort sort=sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) 
            ? Sort.by(sortField).ascending() 
            : Sort.by(sortField).descending();
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
        row.createCell(9).setCellValue("ID Phòng ban");
        row.createCell(10).setCellValue("Tên phòng ban");
        row.createCell(11).setCellValue("Địa chỉ");
        row.createCell(12).setCellValue("SĐT");
        row.createCell(13).setCellValue("ID Lương");
        row.createCell(14).setCellValue("Hệ số lương");
        row.createCell(15).setCellValue("Phụ cấp");
        row.createCell(16).setCellValue("Bảo hiểm");
        row.createCell(17).setCellValue("Trừ lương");
        row.createCell(18).setCellValue("Tổng lương");
        row.createCell(19).setCellValue("Nợ");

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
            dataRow.createCell(9).setCellValue(e.getIdpb().getIdpb());
            dataRow.createCell(10).setCellValue(e.getIdpb().getTenpb());
            dataRow.createCell(11).setCellValue(e.getIdpb().getDiachi());
            dataRow.createCell(12).setCellValue(e.getIdpb().getSdt());
            dataRow.createCell(13).setCellValue(e.getIdluong().getIdluong());
            dataRow.createCell(14).setCellValue(e.getIdluong().getHsl());
            dataRow.createCell(15).setCellValue(e.getIdluong().getPhucap());
            dataRow.createCell(16).setCellValue(e.getIdluong().getBaohiem());
            dataRow.createCell(17).setCellValue(e.getIdluong().getTruluong());
            dataRow.createCell(18).setCellValue(e.getIdluong().getTongluong());
            dataRow.createCell(19).setCellValue(e.getIdluong().getTienno());
            dataRowIndex++;
        }
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void uploadExcel(MultipartFile file) throws IOException{
        User iduser = uService.getUserByID(Utility.getCurrentUserId());

        if(file.isEmpty() || file==null){
            throw new IllegalStateException("Không tìm thấy file. Vui lòng chọn file để tải lên.");
        }
        try (InputStream inputStream = file.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell hotenCell = row.getCell(0);
                Cell ngaysinhCell = row.getCell(1);
                Cell quequanCell = row.getCell(2);
                Cell gtCell = row.getCell(3);
                Cell dantocCell = row.getCell(4);
                Cell sdtCell = row.getCell(5);
                Cell emailCell = row.getCell(6);
                Cell chucvuCell = row.getCell(7);
                Cell tenpbCell = row.getCell(8);
                Cell diachiCell = row.getCell(9);
                Cell sdtpbCell = row.getCell(10);
                Cell hslCell = row.getCell(11);
                Cell phucapCell = row.getCell(12);

                if (hotenCell == null || hotenCell.getCellType() != CellType.STRING) {
                    throw new IllegalStateException("Dòng " + (row.getRowNum() + 1) + ": Họ tên không hợp lệ.");
                }else if (ngaysinhCell == null || ngaysinhCell.getCellType() != CellType.STRING) {
                    throw new IllegalStateException("Dòng " + (row.getRowNum() + 1) + ": Ngày sinh không hợp lệ.");
                }else if (quequanCell == null || quequanCell.getCellType() != CellType.STRING) {
                    throw new IllegalStateException("Dòng " + (row.getRowNum() + 1) + ": Quê quán không hợp lệ.");
                }else if (gtCell == null || gtCell.getCellType() != CellType.STRING) {
                    throw new IllegalStateException("Dòng " + (row.getRowNum() + 1) + ": Giới tính không hợp lệ.");
                }else if (dantocCell == null || dantocCell.getCellType() != CellType.STRING) {
                    throw new IllegalStateException("Dòng " + (row.getRowNum() + 1) + ": Dân tộc không hợp lệ.");
                }else if (sdtCell == null || sdtCell.getCellType() != CellType.STRING && sdtCell.getCellType() != CellType.NUMERIC) {
                    throw new IllegalStateException("Dòng " + (row.getRowNum() + 1) + ": SDT không hợp lệ.");
                }else if (emailCell == null || emailCell.getCellType() != CellType.STRING) {
                    throw new IllegalStateException("Dòng " + (row.getRowNum() + 1) + ": Email không hợp lệ.");
                }else if (chucvuCell == null || chucvuCell.getCellType() != CellType.STRING) {
                    throw new IllegalStateException("Dòng " + (row.getRowNum() + 1) + ": Chức vụ không hợp lệ.");
                }else if (tenpbCell == null || tenpbCell.getCellType() != CellType.STRING) {
                    throw new IllegalStateException("Dòng " + (row.getRowNum() + 1) + ": Tên phòng ban không hợp lệ.");
                }else if (diachiCell == null || diachiCell.getCellType() != CellType.STRING) {
                    throw new IllegalStateException("Dòng " + (row.getRowNum() + 1) + ": Địa chỉ không hợp lệ.");
                }else if (sdtpbCell == null || sdtpbCell.getCellType() != CellType.STRING && sdtpbCell.getCellType() != CellType.NUMERIC) {
                    throw new IllegalStateException("Dòng " + (row.getRowNum() + 1) + ": SDT phòng ban không hợp lệ.");
                }else if (hslCell == null || hslCell.getCellType() != CellType.NUMERIC) {
                    throw new IllegalStateException("Dòng " + (row.getRowNum() + 1) + ": Hệ số lương không hợp lệ.");
                }else if (phucapCell == null || phucapCell.getCellType() != CellType.NUMERIC) {
                    throw new IllegalStateException("Dòng " + (row.getRowNum() + 1) + ": Phụ cấp không hợp lệ.");
                }

                String hoten = getCellValue(hotenCell);
                String ngaysinh = getCellValue(ngaysinhCell);
                String quequan = getCellValue(quequanCell);
                String gt = getCellValue(gtCell);
                String dantoc = getCellValue(dantocCell);
                String sdt = sdtCell.getCellType() == CellType.NUMERIC ? String.valueOf((long) sdtCell.getNumericCellValue()) : sdtCell.getStringCellValue();        
                String email = getCellValue(emailCell);
                String chucvu = getCellValue(chucvuCell);
                String tenpb = getCellValue(tenpbCell);
                String diachi = getCellValue(diachiCell);
                String sdtpb = sdtpbCell.getCellType() == CellType.NUMERIC ? String.valueOf((long) sdtpbCell.getNumericCellValue()) : sdtpbCell.getStringCellValue();             
                float hsl = (float) hslCell.getNumericCellValue();
                float phucap = (float) phucapCell.getNumericCellValue();

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
                    eService.logAuditOperation(iduser, null, null, idpb.getIdpb(), Act.ADD);
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

