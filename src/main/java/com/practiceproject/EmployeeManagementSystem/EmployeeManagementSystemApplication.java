package com.practiceproject.EmployeeManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeManagementSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementSystemApplication.class, args);
	}
	//TODO: Nếu hiển thị được dữ liệu thì sẽ bổ sung thêm phần giới tính cho Nhân viên.
	/*Đã thử xóa cái phần spring.datasource.driver trong phần properties và có xóa đi mấy thuộc tính
	 * private của service vs controller
	 * Bổ sung thêm phần tên cột vào phần thuộc tính.
	 * Và sửa lại phần dữ liệu
	*/
}
