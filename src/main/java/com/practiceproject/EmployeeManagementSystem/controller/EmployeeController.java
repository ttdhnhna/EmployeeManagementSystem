package com.practiceproject.EmployeeManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.practiceproject.EmployeeManagementSystem.entity.Department;
import com.practiceproject.EmployeeManagementSystem.entity.Employee;
import com.practiceproject.EmployeeManagementSystem.entity.Salary;
import com.practiceproject.EmployeeManagementSystem.entity.User;
import com.practiceproject.EmployeeManagementSystem.service.EmployeeService;


import org.springframework.web.multipart.MultipartFile;

@Controller//chỉ ra rằng một lớp cụ thể đóng vai trò của bộ điều khiển
/*Hay có thể nói dễ hiểu hơn là lớp này sẽ là lớp được dùng để liên kết và lấy nhưng logic toán học được tạo ra trong service
 * và đưa ra cho người dùng khi họ tương tác 
*/
public class EmployeeController {
    @Autowired 
    //Điều này có nghĩa là ta sẽ lấy được bean đc tạo tự động bởi Spring
    EmployeeService service;
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
        Employee employee=new Employee();
        model.addAttribute("employee", employee);
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
        @RequestParam("idluong") Salary idluong,
        @RequestParam("anh")MultipartFile anh){
        //@ModelAttribute là chú thích liên kết tham số phương thức hoặc giá trị trả về của phương thức với thuộc tính mô hình được đặt tên và sau đó hiển thị nó ở chế độ xem web. 
        //Lưu vào csdl
        // service.saveEmployee(employee, multipartFile);
        service.saveEmployee(hoten, ngaysinh, quequan, gt, dantoc, sdt, email, chucvu, idpb, idluong, anh);
        return "redirect:/";
    }

    @GetMapping("/updateEmployee/{id}")
    public String updateEmployee(@PathVariable(value = "id") long id, Model model){
        //@PathVariable được dùng để xử lý các biểu mẫu trong ánh xạ URI được yêu cầu và đặt làm tham số
        //Hay đơn giản hơn là nó lấy id có được khi ta nhấn nút sửa và gán nó vào id vừa khai báo ở trên
        //Lay du lieu nhan vien tu service
        Employee employee=service.getEmployeebyID(id);
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

        Page<Employee> page=service.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Employee> ListEmployees=page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("ListEmployees", ListEmployees); 
        return "homepage";
    }

    //Xem chi tiết hồ sơ nhân viên
    @GetMapping("/profileemployee/{id}")
    public String viewProfileEmployee(@PathVariable(value = "id") long id, Model model){
        Employee employee=service.getEmployeebyID(id);
        model.addAttribute("employee", employee);
        Salary salary=service.getsalaryInfo(id);
        model.addAttribute("salary", salary);
        User user = service.getuserInfo(id);
        model.addAttribute("user", user);
        return "employeeviewprofile";
    }

    //Tìm kiếm
    @GetMapping("/findemployee")
    public String findEmployees(Model model, @Param("keyword") String keyword){
        List<Employee> ListEmployees=service.findAll(keyword);
        model.addAttribute("ListEmployees", ListEmployees);
        return "homepage";
    }
}
