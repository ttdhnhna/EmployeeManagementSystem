package com.practiceproject.EmployeeManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeManagementSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementSystemApplication.class, args);
	}
	//TODO: Nếu hiển thị được dữ liệu thì sẽ bổ sung thêm phần giới tính cho Nhân viên.
	/* Có vẻ là có lỗi: 
	 * Đầu tiên là về phần sửa nhân viên thì sau khi sửa xong nó sẽ gần như tạo ra 1 nhân viên mới chứ nó không
	 * thay thế cái phần mình sửa
	 * Tiếp theo là phần xóa thì có vẻ là nó chỉ xóa phần hiện ra thôi còn phần dữ liệu thì nó vẫn lưu ở trong 
	 * CSDL nên sẽ cần phải xem và sửa lại
	 * 
	*/
}
