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
        String tThanhVien = "CREATE TABLE THANHVIEN (matv INTEGER PRIMARY KEY AUTOINCREMENT, tentv TEXT, email TEXT, tendangnhap TEXT, matkhau TEXT)";
        db.execSQL(tThanhVien);

        //tạo bảng loại sách
        String tLoaiSach = "CREATE TABLE LOAISACH (maloai INTEGER PRIMARY KEY AUTOINCREMENT, tenloai TEXT, urlHinh TEXT)";
        db.execSQL(tLoaiSach);

        //tạo bảng sách
        String tSach = "CREATE TABLE SACH (masach INTEGER PRIMARY KEY AUTOINCREMENT, tensach TEXT, tacgia TEXT, giathue INTEGER,trangthai INTEGER,maloai INTEGER REFERENCES LOAISACH(maloai))";
        db.execSQL(tSach);

        //tạo bảng phiếu mượn
        String tPhieuMuon = "CREATE TABLE PHIEUMUON (mapm INTEGER PRIMARY KEY AUTOINCREMENT, matv INTEGER REFERENCES THANHVIEN(matv), matt TEXT REFERENCES THUTHU(matt), masach INTEGER REFERENCES SACH(masach), ngaymuon TEXT, ngaytra TEXT, trangthai TEXT, tienthue INTEGER)";
        db.execSQL(tPhieuMuon);

        //tạo bảng đánh dấu
        String tDanhDau = "CREATE TABLE DANHDAU (masach INTEGER REFERENCES SACH(masach) , matv INTEGER REFERENCES THANHVIEN(matv))";
        db.execSQL(tDanhDau);


        //data mẫu
        db.execSQL("INSERT INTO LOAISACH VALUES " +
                "(1, 'Kinh tế', 'https://res.cloudinary.com/ddkqz5udn/image/upload/v1721305272/ic_kinhte.png')," +
                "(2, 'Ngoại Ngữ', 'https://res.cloudinary.com/ddkqz5udn/image/upload/v1721729873/66facf81-346b-45bd-b8cc-4af810862e53_lafvqt.png')," +
                "(3, 'Công nghệ thông tin', 'https://res.cloudinary.com/ddkqz5udn/image/upload/v1721730154/7e903bdb-68fa-4454-81c9-fb2be95aed69_eopx7q.png')," +
                "(4, 'Ẩm thực', 'https://res.cloudinary.com/ddkqz5udn/image/upload/v1721730184/2883686a-f82d-4690-98e1-46f2e3ea33ff_v3crje.png')," +
                "(5, 'Sức Khoẻ', 'https://res.cloudinary.com/ddkqz5udn/image/upload/v1721730204/ab970fe5-e77a-4159-8e3e-4e5b164db774_l9xrvl.png')");

        db.execSQL("INSERT INTO SACH VALUES (1, 'Kinh điển về khởi nghiệp', 'Bill Aulet', 10000,1, 1), (2, 'Bí mật tư duy triệu phú', 'T.Harv Eker', 12000,1, 1), (3, 'Bí quyết gây dựng cơ nghiệp bạc tỷ', 'Adam Khoo', 15000,1, 1), " +
                "(4, 'Life Of Pi', 'Yann Martel', 10000,1, 2), (5, 'The Alchemist', 'Paulo Coelho', 15000,1, 2), (6, 'Harry Potter', 'J. K. Rowling', 20000,1, 2), " +
                "(7, 'The Industries of the Future', 'Alec Ross', 10000,1, 3), (8, 'Lập trình và Cuộc sống', 'Jeff Atwood', 12000,1, 3), (9, 'Thinking In Java','Bruce Eckel', 15000,1, 3)," +
                " (10,'Món Ăn Việt Nam','Triệu Thị Chơi - Nguyễn Thị Phụng', 10000,1, 4), (11, 'Món xưa vị nay','Võ Hoàng Nhân', 12000,1, 4),(12,'Món ăn thuần Việt: 60 món mặn','Nguyễn Thị Diệu Thảo', 15000,1, 4)," +
                " (13,'Nhân tố enzyme', 'Hiromi Shinya', 10000,1, 5), (14,'Cơ thể 4 giờ','Timothy Ferris',12000,1,5),(15,'Grain Brain','David Perlmutter',15000,1,5)");

        db.execSQL("INSERT INTO THUTHU VALUES('admin01','Nguyễn Nhật Thuý','thuy123','thuy123@gmail.com','admin'),('admin02','Nguyễn Thành Đạt','dat123','dat123@gmail.com','admin'),('thuthu01','Phạm Thanh Toàn','toan123','toan123@gmail.com','thuthu'),('thuthu02','Nguyễn Quang Vinh','vinh123','vinh123@gmail.com','thuthu'),('thuthu03','Lê Hữu Nhân','nhan123','nhan123@gmail.com','thuthu')");

        db.execSQL("INSERT INTO THANHVIEN VALUES(1,'Nguyễn Thành Đạt','dat@gmail.com','dat123','123'),(2,'Phạm Thanh Toàn','toanptps37329@gmail.com','toanpt','123')");

        db.execSQL("INSERT INTO PHIEUMUON VALUES " +
                "('1','1','thuthu01','1','11/11/2011','11/11/2012','1', '12000')," +
                "('2','1','thuthu01','1','11/11/2022','11/11/2024','0', '15000')");


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
