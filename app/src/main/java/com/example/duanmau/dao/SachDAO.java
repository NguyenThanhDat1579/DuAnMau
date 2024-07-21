package com.example.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.database.DbHelper;
import com.example.duanmau.model.PhieuMuon;
import com.example.duanmau.model.Sach;

import java.util.ArrayList;

public class SachDAO {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public SachDAO(Context context){
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    //lấy toàn bộ đầu sách có trong thư viện
    public ArrayList<Sach> getAllSach(){
        ArrayList<Sach> list = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        String query = "SELECT SACH.masach, SACH.tensach, SACH.tacgia, SACH.giathue, SACH.maloai, LOAISACH.tenloai " +
                "FROM SACH " +
                "INNER JOIN LOAISACH ON SACH.maloai = LOAISACH.maloai";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<Sach> getSachByLoai(int maloai) {
        ArrayList<Sach> list = new ArrayList<>();
        String query = "SELECT SACH.masach, SACH.tensach, SACH.tacgia, SACH.giathue, SACH.maloai, LOAISACH.tenloai " +
                "FROM SACH " +
                "INNER JOIN LOAISACH ON SACH.maloai = LOAISACH.maloai " +
                "WHERE SACH.maloai = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(maloai)});
        if (cursor.moveToFirst()) {
            do {
                int masach = cursor.getInt(0);
                String tensach = cursor.getString(1);
                String tacgia = cursor.getString(2);
                int giathue = cursor.getInt(3);
                int maloaiSach = cursor.getInt(4);
                String tenloai = cursor.getString(5);
                list.add(new Sach(masach, tensach, tacgia, giathue, maloaiSach, tenloai));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }






}
