package com.example.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.widget.ActionBarContextView;

import com.example.duanmau.database.DbHelper;
import com.example.duanmau.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienDao {
    DbHelper dbHelper;
    public ThanhVienDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    //đăng nhập
    public boolean checkDangNhapThanhVien(String tendangnhaptv, String matkhautv){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THANHVIEN WHERE tendangnhap = ? AND matkhau = ?", new String[]{tendangnhaptv, matkhautv});
        return cursor.getCount() != 0;
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
        if(cursor.getCount() !=0){
            cursor.moveToFirst();
            do{
                list.add(new ThanhVien(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)));
            }while (cursor.moveToNext());
        }
        return list;
    }

}
