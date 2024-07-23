package com.example.duanmau.dao;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duanmau.database.DbHelper;
import com.example.duanmau.model.LoaiSach;
import com.example.duanmau.model.ThanhVien;

import java.util.ArrayList;

public class LoaiSachDAO {
    DbHelper dbHelper;
    public LoaiSachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<LoaiSach> getDSLoaiSach(){
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LOAISACH", null);
        if(cursor.getCount() !=0){
            cursor.moveToFirst();
            do{
                LoaiSach loaiSach = new LoaiSach(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                list.add(loaiSach);
                Log.d(TAG, "LoaiSach: " + loaiSach.getMaloai() + ", " + loaiSach.getTenloai() + ", " + loaiSach.getUrlHinh());
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean themLoaiSach(String tenloai, String urlHinh){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", tenloai);
        contentValues.put("urlHinh", urlHinh); // Thêm trường urlHinh vào ContentValues


        long check = sqLiteDatabase.insert("LOAISACH",null,contentValues);
        return check != -1;
    }

    public boolean chinhSua(LoaiSach loaiSach){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", loaiSach.getTenloai());
        contentValues.put("urlHinh", loaiSach.getUrlHinh());


        int check = sqLiteDatabase.update("LOAISACH", contentValues, "maloai = ?", new String[]{String.valueOf(loaiSach.getMaloai())});
        if (check <= 0) return false;
        return true;
    }
}
