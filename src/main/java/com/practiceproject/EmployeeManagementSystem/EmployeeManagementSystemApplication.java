package com.practiceproject.EmployeeManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeManagementSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementSystemApplication.class, args);
	}
	/*Lỗi ở 2 phần sửa và chuyển trang của phòng ban 
	 * Sửa lại 2 phần lỗi trên và làm phần trang chi tiết nhân viên.
	 * Phần tìm kiếm ở nhân viên đã chạy ổn nhưng những trang khác thì không sử dụng đc.
	 * Cần sửa lại nhưng ít nhất là nút reset cũng hoạt động đc.
	 */
}
