package com.practiceproject.EmployeeManagementSystem.entitydto;

public class UserDto {
    private Long iduser;
    private String hoten;
    private String email;
    
    public UserDto() {
    }

    public UserDto(Long iduser, String hoten, String email) {
        this.iduser = iduser;
        this.hoten = hoten;
        this.email = email;
    }

    public Long getIduser() {
        return iduser;
    }

    public void setIduser(Long iduser) {
        this.iduser = iduser;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    } 
    
    
}
