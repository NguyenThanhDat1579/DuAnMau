package com.example.duanmau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "QUANLYTHUVIEN", null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //tạo bảng thủ thư
        String tThuThu = "CREATE TABLE THUTHU (matt TEXT PRIMARY KEY, hotentt TEXT, matkhau TEXT, email TEXT, loaitk TEXT)";
        db.execSQL(tThuThu);

        //tạo bảng thành viên
        String tThanhVien = "CREATE TABLE THANHVIEN (matv INTEGER PRIMARY KEY AUTOINCREMENT, tentv TEXT, email TEXT, sdt TEXT, diachi TEXT, tendangnhap TEXT, matkhau TEXT)";
        db.execSQL(tThanhVien);

        //tạo bảng loại sách
        String tLoaiSach = "CREATE TABLE LOAISACH (maloai INTEGER PRIMARY KEY AUTOINCREMENT, tenloai TEXT)";
        db.execSQL(tLoaiSach);

        //tạo bảng sách
        String tSach = "CREATE TABLE SACH (masach INTEGER PRIMARY KEY AUTOINCREMENT, tensach TEXT, tacgia TEXT, giathue INTEGER, maloai INTEGER REFERENCES LOAISACH(maloai))";
        db.execSQL(tSach);

        //tạo bảng phiếu mượn
        String tPhieuMuon = "CREATE TABLE PHIEUMUON (mapm INTEGER PRIMARY KEY AUTOINCREMENT, matv INTEGER REFERENCES THANHVIEN(matv), matt TEXT REFERENCES THUTHU(matt), masach INTEGER REFERENCES SACH(masach), ngaymuon TEXT, ngaytra TEXT, trangthai TEXT, tienthue INTEGER)";
        db.execSQL(tPhieuMuon);

        //tạo bảng đánh dấu
        String tDanhDau = "CREATE TABLE DANHDAU (masach INTEGER REFERENCES SACH(masach) , matv INTEGER REFERENCES THANHVIEN(matv))";
        db.execSQL(tDanhDau);


        //data mẫu
        db.execSQL("INSERT INTO LOAISACH VALUES (1, 'Kinh tế'),(2, 'Ngoại Ngữ'),(3, 'Công nghệ thông tin'),(4, 'Ẩm thực'),(5, 'Sức Khoẻ')");
        db.execSQL("INSERT INTO SACH VALUES (1, 'Kinh điển về khởi nghiệp', 'Bill Aulet', 10000, 1), (2, 'Bí mật tư duy triệu phú', 'T.Harv Eker', 12000, 1), (3, 'Bí quyết gây dựng cơ nghiệp bạc tỷ', 'Adam Khoo', 15000, 1), " +
                "(4, 'Life Of Pi', 'Yann Martel', 10000, 2), (5, 'The Alchemist', 'Paulo Coelho', 15000, 2), (6, 'Harry Potter', 'J. K. Rowling', 20000, 2), " +
                "(7, 'The Industries of the Future', 'Alec Ross', 10000, 3), (8, 'Lập trình và Cuộc sống', 'Jeff Atwood', 12000, 3), (9, 'Thinking In Java','Bruce Eckel', 15000, 3)," +
                " (10,'Món Ăn Việt Nam','Triệu Thị Chơi - Nguyễn Thị Phụng', 10000, 4), (11, 'Món xưa vị nay','Võ Hoàng Nhân', 12000, 4),(12,'Món ăn thuần Việt: 60 món mặn','Nguyễn Thị Diệu Thảo', 15000, 4)," +
                " (13,'Nhân tố enzyme', 'Hiromi Shinya', 10000, 5), (14,'Cơ thể 4 giờ','Timothy Ferris',12000,5),(15,'Grain Brain','David Perlmutter',15000,5)");

        db.execSQL("INSERT INTO THUTHU VALUES('thuthu01','Nguyễn Nhật Thuý','thuy123','thuy123@gmail.com','Admin'),('thuthu02','Phạm Thanh Toàn','toan123','toan123@gmail.com','Thành viên')");


        db.execSQL("INSERT INTO THANHVIEN VALUES(01,'thanhdat','dat@gmail.com','1234567890','TienGiang','dat123','123')");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS THUTHU");
            db.execSQL("DROP TABLE IF EXISTS THANHVIEN");
            db.execSQL("DROP TABLE IF EXISTS LOAISACH");
            db.execSQL("DROP TABLE IF EXISTS SACH");
            db.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
            db.execSQL("DROP TABLE IF EXISTS DANHDAU");
            onCreate(db);
        }
    }
}
