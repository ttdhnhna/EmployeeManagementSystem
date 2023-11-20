package com.practiceproject.EmployeeManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.practiceproject.EmployeeManagementSystem.entity.Employee;
import com.practiceproject.EmployeeManagementSystem.service.EmployeeService;

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
        model.addAttribute("ListEmployees", service.getEmployees());
        /*Phương thức addAttribute ở đây sẽ giúp ta truyền nhưng dữ liệu mà ta lấy được bằng service.getEmployees()
         * và truyền vào ListEmployees để ta có thể dùng để hiển thị trên trang web
        */
        return "homepage";
    }
    @GetMapping("/addEmployee")
    public String addEmployee(Model model){
        Employee employee=new Employee();
        model.addAttribute("employee", employee);
        return "newemployee";
    }
    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee){
        //@ModelAttribute là chú thích liên kết tham số phương thức hoặc giá trị trả về của phương thức với thuộc tính mô hình được đặt tên và sau đó hiển thị nó ở chế độ xem web. 
        //Lưu vào csdl
        service.saveEmployee(employee);
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
}
