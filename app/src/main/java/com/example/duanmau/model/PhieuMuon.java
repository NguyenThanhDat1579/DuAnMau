package com.example.duanmau.model;

public class PhieuMuon {
    private int mapm;
    private int matv;
    private int matt;
    private int masach;
    private String ngaymuon;
    private String ngaytra;
    private int trangthai;
    private int tienthue;
    private String tentv;
    private String tentt;
    private  String tensach;

    //pm.mapm, pm.matv, tv.tentv, pm.matt, tt.hotentt, pm.masach, sc.tensach, pm.ngaymuon, pm.ngaytra, pm.trangthai, pm.tienthue

    public PhieuMuon(int mapm, int matv,String tentv, int matt,String tentt, int masach, String tensach, String ngaymuon, String ngaytra, int trangthai, int tienthue  ) {
        this.mapm = mapm;
        this.matv = matv;
        this.matt = matt;
        this.masach = masach;
        this.ngaymuon = ngaymuon;
        this.ngaytra = ngaytra;
        this.trangthai = trangthai;
        this.tienthue = tienthue;
        this.tentv = tentv;
        this.tentt = tentt;
        this.tensach = tensach;
    }

    public int getMapm() {
        return mapm;
    }

    public void setMapm(int mapm) {
        this.mapm = mapm;
    }

    public int getMatv() {
        return matv;
    }

    public void setMatv(int matv) {
        this.matv = matv;
    }

    public int getMatt() {
        return matt;
    }

    public void setMatt(int matt) {
        this.matt = matt;
    }

    public int getMasach() {
        return masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }

    public String getNgaymuon() {
        return ngaymuon;
    }

    public void setNgaymuon(String ngaymuon) {
        this.ngaymuon = ngaymuon;
    }

    public String getNgaytra() {
        return ngaytra;
    }

    public void setNgaytra(String ngaytra) {
        this.ngaytra = ngaytra;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public int getTienthue() {
        return tienthue;
    }

    public void setTienthue(int tienthue) {
        this.tienthue = tienthue;
    }

    public String getTentv() {
        return tentv;
    }

    public void setTentv(String tentv) {
        this.tentv = tentv;
    }

    public String getTentt() {
        return tentt;
    }

    public void setTentt(String tentt) {
        this.tentt = tentt;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }
}
