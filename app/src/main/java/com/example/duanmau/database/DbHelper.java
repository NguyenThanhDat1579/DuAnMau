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
        String tSach = "CREATE TABLE SACH (masach INTEGER PRIMARY KEY AUTOINCREMENT, tensach TEXT, tacgia TEXT, giathue INTEGER,trangthai INTEGER,maloai INTEGER REFERENCES LOAISACH(maloai), urlHinh TEXT)";
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

        db.execSQL("INSERT INTO SACH VALUES (1, 'Kinh điển về khởi nghiệp', 'Bill Aulet', 10000,1, 1, 'https://res.cloudinary.com/ddkqz5udn/image/upload/v1722160083/kinhdienvekhoinghiep_iby1lf.png'), " +
                "(2, 'Bí mật tư duy triệu phú', 'T.Harv Eker', 12000,1, 1, 'https://res.cloudinary.com/ddkqz5udn/image/upload/v1722160298/bi-mat-tu-duy-trieu-phu_cwof1a.jpg'), " +
                "(3, 'Bí quyết gây dựng cơ nghiệp bạc tỷ', 'Adam Khoo', 15000,1, 1, 'https://res.cloudinary.com/ddkqz5udn/image/upload/v1722160423/76c135e40a7ae7b059fe167ce3ad71ff_hbg2im.jpg'), " +
                "(4, 'Life Of Pi', 'Yann Martel', 10000,1, 2, 'https://res.cloudinary.com/ddkqz5udn/image/upload/v1722160488/image_98348_xkmqpi.webp'), " +
                "(5, 'The Alchemist', 'Paulo Coelho', 15000,1, 2, 'https://res.cloudinary.com/ddkqz5udn/image/upload/v1722160551/9780062315007_ybhzie.jpg'), " +
                "(6, 'Harry Potter', 'J. K. Rowling', 20000,1, 2, 'https://res.cloudinary.com/ddkqz5udn/image/upload/v1722160615/dd48ff69d040f5ea5d410d1199c180e7.jpg_720x720q80_xjc7mi.jpg'), " +
                "(7, 'The Industries of the Future', 'Alec Ross', 10000,1, 3, 'https://res.cloudinary.com/ddkqz5udn/image/upload/v1722168234/nxbtre_full_29422019_024254_ubcpyy.jpg'), " +
                "(8, 'Lập trình và Cuộc sống', 'Jeff Atwood', 12000,1, 3, 'https://res.cloudinary.com/ddkqz5udn/image/upload/v1722160717/lap_trinh_va_cuoc_song_tai_ban_2018_1_2018_10_26_10_43_02_fvs8yq.webp'), " +
                "(9, 'Thinking In Java','Bruce Eckel', 15000,1, 3, 'https://res.cloudinary.com/ddkqz5udn/image/upload/v1722160762/71672_unvvo8.jpg')," +
                " (10,'Món Ăn Việt Nam','Triệu Thị Chơi - Nguyễn Thị Phụng', 10000,1, 4, 'https://res.cloudinary.com/ddkqz5udn/image/upload/v1722160804/9786048688035_ixwmlr.webp'), " +
                "(11, 'Món xưa vị nay','Võ Hoàng Nhân', 12000,1, 4, 'https://res.cloudinary.com/ddkqz5udn/image/upload/v1722160853/mon-xua-vi-nay.-bia_o8dy0e.png')," +
                "(12,'Món ăn thuần Việt: 60 món mặn','Nguyễn Thị Diệu Thảo', 15000,1, 4, 'https://res.cloudinary.com/ddkqz5udn/image/upload/v1722160898/aff906cf5450ccd3af71a43aa919dffd_k2apyh.jpg')," +
                "(13,'Nhân tố enzyme', 'Hiromi Shinya', 10000,1, 5, 'https://res.cloudinary.com/ddkqz5udn/image/upload/v1722160947/8935280913943_tahgoy.webp'), " +
                "(14,'Cơ thể 4 giờ','Timothy Ferris',12000,1,5, 'https://res.cloudinary.com/ddkqz5udn/image/upload/v1722160990/10d77e922b14f632b7342250f89ae065_1_ix4apa.webp'), " +
                "(15,'Grain Brain','David Perlmutter',15000,1,5, 'https://res.cloudinary.com/ddkqz5udn/image/upload/v1722161045/4fcb5d4b82ee5b06a361ad620d69df0f_kkimkj.jpg')");

        db.execSQL("INSERT INTO THUTHU VALUES('admin01','Nguyễn Nhật Thuý','thuy123','thuy123@gmail.com','admin'),('admin02','Nguyễn Thành Đạt','dat123','dat123@gmail.com','admin'),('thuthu01','Phạm Thanh Toàn','toan123','toan123@gmail.com','thuthu'),('thuthu02','Nguyễn Quang Vinh','vinh123','vinh123@gmail.com','thuthu'),('thuthu03','Lê Hữu Nhân','nhan123','nhan123@gmail.com','thuthu')");

        db.execSQL("INSERT INTO THANHVIEN VALUES(1,'Nguyễn Thành Đạt','dat@gmail.com','dat123','123'),(2,'Phạm Thanh Toàn','toanptps37329@gmail.com','toan123','123'), (3,'Lê Hữu Nhân','nhan@gmail.com','nhan123','123'), (4,'Nguyễn Nhật Thuý','thuy@gmail.com','thuy123','123'), (5,'Nguyễn Quang Vinh','vinh@gmail.com','vinh123','123')");

        db.execSQL("INSERT INTO PHIEUMUON VALUES " +
                "('1','1','thuthu01','1','11/08/2016','22/11/2012','1', '12000')," +
                "('2','2','thuthu02','2','16/07/2017','23/11/2024','0', '18000')," +
                "('3','1','thuthu02','1','19/06/2018','20/11/2024','1', '15000')," +
                "('4','3','thuthu01','4','10/09/2019','11/11/2024','1', '19000')," +
                "('5','2','thuthu01','6','10/10/2020','21/11/2024','0', '12000')," +
                "('6','1','thuthu03','8','16/11/2021','19/11/2024','0', '11000')," +
                "('7','3','thuthu02','8','18/11/2022','19/11/2024','1', '10000')," +
                "('8','1','thuthu03','1','01/12/2022','11/11/2024','0', '15000')"
        );


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
