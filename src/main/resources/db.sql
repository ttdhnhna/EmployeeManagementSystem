DROP TABLE tblEmployee;

CREATE TABLE tblEmployee(
    idnv int not null,
    Hoten varchar(50),
    Ngaysinh datetime,
    Quequan varchar(50),
    Dantoc varchar(30),
    SDT varchar(15),
    Email varchar(50),
    Chucvu varchar(50)
);
-- Không cần thiết cho lắm nhưng cứ thêm vào cho chắc
INSERT INTO tblEmployee
VALUES('Dat', '2001-10-24', 'Ha Noi', 'Kinh', '0982576215','123@gmail.com', 'Quan ly'),
('Ha', '2001-2-15', 'Ha Noi', 'Kinh', '0952588962','456@gmail.com', 'Nhan vien');