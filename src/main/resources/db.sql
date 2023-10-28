DROP TABLE tblEmployee;

CREATE TABLE tblEmployee(
    idnv int identity(1,1) not null,
    Hoten nvarchar(50),
    Ngaysinh datetime,
    Quequan nvarchar(50),
    Dantoc nvarchar(30),
    SDT varchar(15),
    Email nvarchar(50),
    Chucvu nvarchar(50)
);
-- Không cần thiết cho lắm nhưng cứ thêm vào cho chắc
INSERT INTO tblEmployee
VALUES(N'Dương Thành Đạt', '2001-10-24', N'Hà Nội', N'Kinh', '0982576215','123@gmail.com', 'Quản lý'),
(N'Nguyễn Thu Hà', '2001-2-15', N'Hà Nội', N'Kinh', '0952588962','456@gmail.com', 'Nhân viên');