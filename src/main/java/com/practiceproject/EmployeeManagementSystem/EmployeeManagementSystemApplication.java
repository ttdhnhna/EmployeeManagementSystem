package com.practiceproject.EmployeeManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeManagementSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementSystemApplication.class, args);
		//TODO: Chạy thử chức năng đăng nhập
		/*Chạy thử trước xem có vấn đề gì liên quan đến các dependencies security hay ko? Nếu
		 * không thì xóa cái core đi còn nếu có thì thử thêm nó vào và làm tương tự với cái 
		 * security web.
		 * Xong rồi sẽ thêm cái tomcat vào xem có chạy đc chương trình ko 
		 */
	}
}
