package com.example.duanmau.model;

public class LoaiSach {
    private int maloai;
    private String tenloai;
    private String urlHinh; // Thêm trường urlHinh


    public LoaiSach(int maloai, String tenloai) {
        this.maloai = maloai;
        this.tenloai = tenloai;
    }

    public LoaiSach(int maloai, String tenloai, String urlHinh) {
        this.maloai = maloai;
        this.tenloai = tenloai;
        this.urlHinh = urlHinh; // Khởi tạo trường urlHinh
    }

    public LoaiSach(String tenloai, String urlHinh) {
        this.tenloai = tenloai;
        this.urlHinh = urlHinh;
    }

    // Getter và Setter cho urlHinh
    public String getUrlHinh() {
        return urlHinh;
    }

    public String setUrlHinh(String urlHinh) {
        this.urlHinh = urlHinh;
        return urlHinh;
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


}
