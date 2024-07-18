package com.example.duanmau.model;

public class ThanhVien {
    private int matv;
    private String tentv;
    private String email;
    private String tendangnhap;
    private String matkhau;

    public ThanhVien(int matv, String tentv, String email, String tendangnhap, String matkhau) {
        this.matv = matv;
        this.tentv = tentv;
        this.email = email;
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
    }

    public int getMatv() {
        return matv;
    }

    public void setMatv(int matv) {
        this.matv = matv;
    }

    public String getTentv() {
        return tentv;
    }

    public void setTentv(String tentv) {
        this.tentv = tentv;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }
}
