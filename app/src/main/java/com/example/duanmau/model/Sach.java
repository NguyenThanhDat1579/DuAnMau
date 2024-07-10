package com.example.duanmau.model;

public class Sach {
    private int masach;
    private String tensach;
    private String tacgia;
    private int giathue;
    private int maloai;

    public Sach(int masach, String tensach, String tacgia, int giathue, int maloai) {
        this.masach = masach;
        this.tensach = tensach;
        this.tacgia = tacgia;
        this.giathue = giathue;
        this.maloai = maloai;
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
}
