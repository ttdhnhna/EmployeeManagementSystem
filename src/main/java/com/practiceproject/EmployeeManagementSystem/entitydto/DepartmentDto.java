package com.practiceproject.EmployeeManagementSystem.entitydto;

public class DepartmentDto {
    private Long idpb;
    private String tenpb;
    private String diachi;
    private String sdt;
    
    public DepartmentDto() {
    }

    public DepartmentDto(Long idpb, String tenpb, String diachi, String sdt) {
        this.idpb = idpb;
        this.tenpb = tenpb;
        this.diachi = diachi;
        this.sdt = sdt;
    }

    public Long getIdpb() {
        return idpb;
    }

    public void setIdpb(Long idpb) {
        this.idpb = idpb;
    }

    public String getTenpb() {
        return tenpb;
    }

    public void setTenpb(String tenpb) {
        this.tenpb = tenpb;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    
}
