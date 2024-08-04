package com.example.duanmau.model;

public class Sach {
    private int masach;
    private String tensach;
    private String tacgia;
    private int giathue;
    private int maloai;
    private String tenloai;
    private int soluongdamuon;
    private int trangthai = 1;
    private String urlHinh; // Thêm trường urlHinh
    private boolean isChecked;


    public Sach() {
    }

    public Sach(int masach, String tensach, String tacgia, int giathue, int maloai, String tenloai, int trangthai, String urlHinh) {
        this.masach = masach;
        this.tensach = tensach;
        this.tacgia = tacgia;
        this.giathue = giathue;
        this.maloai = maloai;
        this.tenloai = tenloai;
        this.trangthai = trangthai;
        this.urlHinh = urlHinh;
    }

    public Sach(int masach, String tensach, String tacgia, int giathue, int maloai, int trangthai, String tenloai, String urlHinh) {
        this.masach = masach;
        this.tensach = tensach;
        this.tacgia = tacgia;
        this.giathue = giathue;
        this.maloai = maloai;
        this.trangthai = trangthai;
        this.tenloai = tenloai;
        this.urlHinh = urlHinh;
    }

    public Sach(int masach, String tensach, String tacgia, int giathue, int trangthai,  int maloai,String urlHinh) {
        this.masach = masach;
        this.tensach = tensach;
        this.tacgia = tacgia;
        this.giathue = giathue;
        this.trangthai = trangthai;
        this.maloai = maloai;
        this.urlHinh = urlHinh;
    }


    public Sach(int masach, String tensach, int soluongdamuon) {
        this.masach = masach;
        this.tensach = tensach;
        this.soluongdamuon = soluongdamuon;
    }

    public Sach(int masach, String tensachnew, String tacgiasach, int giabansach) {
    }

    public int getSoluongdamuon() {
        return soluongdamuon;
    }

    public void setSoluongdamuon(int soluongdamuon) {
        this.soluongdamuon = soluongdamuon;
    }

    public int getMasach() {
        return masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public int getGiathue() {
        return giathue;
    }

    public void setGiathue(int giathue) {
        this.giathue = giathue;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public String getUrlHinh() {
        return urlHinh;
    }

    public String setUrlHinh(String urlHinh) {
        this.urlHinh = urlHinh;
        return urlHinh;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
