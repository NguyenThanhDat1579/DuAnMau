package com.example.duanmau.model;

public class PhieuMuon {
    private int mapm;
    private int matv;
    private String matt;
    private int masach;
    private String ngaymuon;
    private String ngaytra;
    private int trangthai;
    private int tienthue;
    private String tentv;
    private String tentt;
    private  String tensach;


    //pm.mapm, pm.matv, tv.tentv, pm.matt, tt.hotentt, pm.masach, sc.tensach, pm.ngaymuon, pm.ngaytra, pm.trangthai, pm.tienthue

    public PhieuMuon(int mapm, int matv, String tentv, String matt, String tentt, int masach, String tensach, String ngaymuon, String ngaytra, int trangthai, int tienthue  ) {
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

    public PhieuMuon(int matv, String matt, int masach,  String ngaymuon, String ngaytra, int trangthai, int tienthue  ) {
        this.matv = matv;
        this.matt = matt;
        this.masach = masach;
        this.ngaymuon = ngaymuon;
        this.ngaytra = ngaytra;
        this.trangthai = trangthai;
        this.tienthue = tienthue;
    }

    public PhieuMuon( String tentt,String tentv, String tensach, String ngaymuon, String ngaytra) {
        this.tentt = tentt;
        this.tentv = tentv;
        this.tensach = tensach;
        this.ngaymuon = ngaymuon;
        this.ngaytra = ngaytra;
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

    public String getMatt() {
        return matt;
    }

    public void setMatt(String matt) {
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
