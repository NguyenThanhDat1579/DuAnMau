package com.example.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.database.DbHelper;
import com.example.duanmau.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienDAO {
    DbHelper dbHelper;
    public ThanhVienDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    //đăng nhập
    public ThanhVien checkLogin(String tenDangNhap, String matKhau) {
        ThanhVien thanhVien = null;
        String query = "SELECT * FROM THANHVIEN WHERE tendangnhap = ? AND matkhau = ?";
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{tenDangNhap, matKhau});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int matv = cursor.getInt(cursor.getColumnIndexOrThrow("matv"));
                String tentv = cursor.getString(cursor.getColumnIndexOrThrow("tentv"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String tendangnhap = cursor.getString(cursor.getColumnIndexOrThrow("tendangnhap"));
                String matkhau = cursor.getString(cursor.getColumnIndexOrThrow("matkhau"));

                thanhVien = new ThanhVien(matv, tentv, email, tendangnhap, matkhau);
            }
            cursor.close();
        }
        return thanhVien;
    }


    //đăng ký
    public boolean dangKy(String tendangnhaptvnew, String hoten, String email, String matkhautvnew){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tendangnhap", tendangnhaptvnew);
        contentValues.put("tentv", hoten);
        contentValues.put("email", email);
        contentValues.put("matkhau",matkhautvnew);

        long check = sqLiteDatabase.insert("THANHVIEN",null,contentValues);
        return check != -1;
    }

    //đổi mật khẩu
    public boolean DoiMatKhau(String tendangnhaptv, String oldPass, String newPass){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THANHVIEN WHERE tendangnhap = ? AND matkhau = ?", new String[]{tendangnhaptv, oldPass});
        if(cursor.getCount() >0){
            ContentValues contentValues = new ContentValues();
            contentValues.put("matkhau", newPass);
            long check = sqLiteDatabase.update("THANHVIEN",contentValues,"tendangnhap = ?", new String[]{tendangnhaptv});
            return check != -1;
        }
        return false;
    }

    //quên mật khẩu
    public String QuenMatKhau(String email){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT matkhau FROM THANHVIEN WHERE email =?", new String[]{email});
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            return cursor.getString(0);
        }else{
            return "";
        }
    }

    public ArrayList<ThanhVien> getDSThanhVien(){
        ArrayList<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THANHVIEN", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do{
                list.add(new ThanhVien(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    // Hàm lấy thông tin thành viên theo mã thành viên
    public ThanhVien getThanhVienById(int id) {
        ThanhVien thanhVien = null;
        String query = "SELECT * FROM THANHVIEN WHERE matv = ?";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int matv = cursor.getInt(cursor.getColumnIndexOrThrow("matv"));
                String tentv = cursor.getString(cursor.getColumnIndexOrThrow("tentv"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String tendangnhap = cursor.getString(cursor.getColumnIndexOrThrow("tendangnhap"));
                String matkhau = cursor.getString(cursor.getColumnIndexOrThrow("matkhau"));

                thanhVien = new ThanhVien(matv, tentv, email, tendangnhap, matkhau);
            }
            cursor.close();
        }
        return thanhVien;
    }

    public boolean chinhSua(ThanhVien thanhVien){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("tentv", thanhVien.getTentv());
        contentValues.put("email", thanhVien.getEmail());
        contentValues.put("tendangnhap", thanhVien.getTendangnhap());
        contentValues.put("matkhau", thanhVien.getMatkhau());

        int check = sqLiteDatabase.update("THANHVIEN", contentValues, "matv = ?", new String[]{String.valueOf(thanhVien.getMatv())});
        if (check <= 0) return false;
        return true;
    }
}


