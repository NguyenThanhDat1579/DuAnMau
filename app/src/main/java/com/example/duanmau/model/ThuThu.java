package com.example.duanmau.model;

public class ThuThu {
    private String matt;
    private String hotentt;
    private String matkhau;
    private String email;
    private String loaitk;

    public ThuThu(String matt, String hotentt, String matkhau, String email, String loaitk) {
        this.matt = matt;
        this.hotentt = hotentt;
        this.matkhau = matkhau;
        this.email = email;
        this.loaitk = loaitk;
    }

    public String getMatt() {
        return matt;
    }

    public void setMatt(String matt) {
        this.matt = matt;
    }

    public String getHotentt() {
        return hotentt;
    }

    public void setHotentt(String hotentt) {
        this.hotentt = hotentt;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoaitk() {
        return loaitk;
    }

    public void setLoaitk(String loaitk) {
        this.loaitk = loaitk;
    }
}
