package com.practiceproject.EmployeeManagementSystem.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.practiceproject.EmployeeManagementSystem.entity.AuditLog;
import com.practiceproject.EmployeeManagementSystem.entity.Department;
import com.practiceproject.EmployeeManagementSystem.entity.Employee;
import com.practiceproject.EmployeeManagementSystem.entitydto.EmployeeDto;
import com.practiceproject.EmployeeManagementSystem.service.AuditLogService;
import com.practiceproject.EmployeeManagementSystem.service.EmployeeService;
import com.practiceproject.EmployeeManagementSystem.service.Utility;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller//chỉ ra rằng một lớp cụ thể đóng vai trò của bộ điều khiển
/*Hay có thể nói dễ hiểu hơn là lớp này sẽ là lớp được dùng để liên kết và lấy nhưng logic toán học được tạo ra trong service
 * và đưa ra cho người dùng khi họ tương tác 
*/
public class EmployeeController {
    @Autowired 
    //Điều này có nghĩa là ta sẽ lấy được bean đc tạo tự động bởi Spring
    EmployeeService service;
    @Autowired
    AuditLogService aService;
    @Autowired
    MessageSource messageSource;

    //Hiển thị trang chủ
    @GetMapping("/")
    // Điều này có nghĩa là phương thức này sẽ được thực hiện khi người dùng gửi yêu cầu GET tới '/'
	// Trong TH này là: "http://localhost:8080/" (Trang chủ)
    public String getEmployees(Model model){
        // Chúng ta sử dụng Interface Model để truyền dữ liệu từ Controller sang View để hiển thị
        return findPaginated(1, "idnv", "asc", model);
    }
    @GetMapping("/addEmployee")
    public String addEmployee(Model model){
        List<AuditLog> ListLogs = aService.getListLogs();
        EmployeeDto employeeDto=new EmployeeDto();
        int unreadCount = aService.getUnreadLog(Utility.getCurrentUserId());
        model.addAttribute("unreadCount", unreadCount);
        model.addAttribute("employee", employeeDto);
        model.addAttribute("ListLogs", ListLogs); 
        /*Phương thức addAttribute ở đây sẽ giúp ta truyền nhưng dữ liệu mà đối tượng employee có
         * và truyền vào employee dưới dạng html để ta có thể dùng để hiển thị và chỉnh sửa
         * trên trang web
        */
        return "newemployee";
    }
    @PostMapping("/saveEmployee")
    public String saveEmployee(@RequestParam("hoten") String hoten,
        @RequestParam("ngaysinh") String ngaysinh,
        @RequestParam("quequan") String quequan,
        @RequestParam("gt") String gt,
        @RequestParam("dantoc") String dantoc,
        @RequestParam("sdt") String sdt,
        @RequestParam("email") String email,
        @RequestParam("chucvu") String chucvu,
        @RequestParam("idpb") Department idpb,
        @RequestParam("anh")MultipartFile anh,
        @RequestParam("hsl") float hsl,
        @RequestParam("phucap") float phucap,
        Model model){
        //@ModelAttribute là chú thích liên kết tham số phương thức hoặc giá trị trả về của phương thức với thuộc tính mô hình được đặt tên và sau đó hiển thị nó ở chế độ xem web. 
        //Lưu vào csdl
        try {
            service.saveEmployee(hoten, ngaysinh, quequan, gt, dantoc, sdt, email, chucvu, idpb, anh, hsl, phucap);
        } catch (IllegalStateException e) {
            model.addAttribute("alertMessage", e.getMessage());
            return "newemployee";
        }
        return "redirect:/";
    }

    @PostMapping("/updateEmployee")
    public String updateEmployee(@ModelAttribute("employee") EmployeeDto employeeDto, Model model, RedirectAttributes redirectAttributes) throws IOException{
        try {
            Employee employee = service.getEmployeebyID(employeeDto.getIdnv());
            if(employee!=null){
                service.updateEmployee(employee, employeeDto);
            }
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("alertMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("employee", employeeDto);
            return "redirect:/updateEmployee/" + employeeDto.getIdnv();
        }
        return "redirect:/";
    }

    @GetMapping("/updateEmployee/{id}")
    public String updateEmployee(@PathVariable(value = "id") long id, Model model){
        //@PathVariable được dùng để xử lý các biểu mẫu trong ánh xạ URI được yêu cầu và đặt làm tham số
        //Hay đơn giản hơn là nó lấy id có được khi ta nhấn nút sửa và gán nó vào id vừa khai báo ở trên
        //Lay du lieu nhan vien tu service
        List<AuditLog> ListLogs = aService.getListLogs();
        Employee employee=service.getEmployeebyID(id);
        int unreadCount = aService.getUnreadLog(Utility.getCurrentUserId());
        model.addAttribute("unreadCount", unreadCount);
        model.addAttribute("ListLogs", ListLogs); 
        model.addAttribute("employee", employee);
        return "updateemployee"; 
    }

    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable(value = "id") long id){
        this.service.deleteEmployeebyID(id);
        return "redirect:/";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, 
    @RequestParam("sortField") String sortField,
    @RequestParam("sortDir") String sortDir, Model model){
        int pageSize=10;
        Long iduser = Utility.getCurrentUserId();

        Page<Employee> page=service.findPaginated(pageNo, pageSize, sortField, sortDir, iduser);
        List<Employee> ListEmployees = page.getContent();
        List<AuditLog> ListLogs = aService.getListLogs();
        int unreadCount = aService.getUnreadLog(iduser);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("ListEmployees", ListEmployees); 
        model.addAttribute("ListLogs", ListLogs); 
        model.addAttribute("unreadCount", unreadCount);

        model.addAttribute("isSearch", false); 

        return "homepage";
    }

    //Xem chi tiết hồ sơ nhân viên
    @GetMapping("/profileemployee/{id}")
    public String viewProfileEmployee(@PathVariable(value = "id") long id, Model model){
        Employee employee=service.getEmployeebyID(id);
        List<AuditLog> ListLogs = aService.getListLogs();
        int unreadCount = aService.getUnreadLog(Utility.getCurrentUserId());
        model.addAttribute("unreadCount", unreadCount);
        model.addAttribute("employee", employee);
        model.addAttribute("ListLogs", ListLogs); 
        return "employeeviewprofile";
    }

    //Tìm kiếm
    @GetMapping("/findemployee")
    public String findEmployees(Model model, @Param("keyword") String keyword){
        Long iduser = Utility.getCurrentUserId();
        List<Employee> ListEmployees=service.findAll(keyword, iduser);
        List<AuditLog> ListLogs = aService.getListLogs();
        int unreadCount = aService.getUnreadLog(iduser);
        String mess = messageSource.getMessage("cantfindnv", null, LocaleContextHolder.getLocale());
        
        model.addAttribute("unreadCount", unreadCount);
        model.addAttribute("ListEmployees", ListEmployees);
        model.addAttribute("ListLogs", ListLogs); 
        model.addAttribute("isSearch", true); 

        if (ListEmployees.isEmpty()) {
            model.addAttribute("errorMess", mess);
        }
        return "homepage";
    }

    @GetMapping("/excel")
    public void generateExcel(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=emp.xls";
        response.setHeader(headerKey, headerValue);
        service.generateExcel(response);
    }

    @GetMapping("/uploadexcel")
    public String uploadExcelPage(Model model){
        int unreadCount = aService.getUnreadLog(Utility.getCurrentUserId());
        model.addAttribute("unreadCount", unreadCount);
        List<AuditLog> ListLogs = aService.getListLogs();
        model.addAttribute("ListLogs", ListLogs); 
        return "uploadexcel";
    }
    
    @PostMapping("/uploadexcel")
    public String uploadExcel(@RequestParam("file") MultipartFile file, Model model) throws IOException{
        try {
            service.uploadExcel(file);
        } catch (IllegalStateException e) {
            model.addAttribute("alertMessage", e.getMessage());
            return "uploadexcel";
        }
        return "redirect:/";
    }
}
