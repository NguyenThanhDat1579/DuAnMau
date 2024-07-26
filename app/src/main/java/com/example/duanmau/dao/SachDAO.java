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
        String query = "SELECT SACH.masach, SACH.tensach, SACH.tacgia, SACH.giathue, SACH.maloai, SACH.trangthai,LOAISACH.tenloai " +
                "FROM SACH " +
                "INNER JOIN LOAISACH ON SACH.maloai = LOAISACH.maloai";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4),cursor.getInt(5),cursor.getString(6)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean thayDoiTrangThaiSach(int masach){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trangthai", 0);
        long check = sqLiteDatabase.update("SACH",contentValues,"masach = ?", new String[]{String.valueOf(masach)});
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    public ArrayList<Sach> getSachByLoai(int maloai) {
        ArrayList<Sach> list = new ArrayList<>();
        String query = "SELECT SACH.masach, SACH.tensach, SACH.tacgia, SACH.giathue, SACH.maloai, SACH.trangthai ,LOAISACH.tenloai " +
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
                int trangthai = cursor.getInt(5);
                String tenloai = cursor.getString(6);
                list.add(new Sach(masach, tensach, tacgia, giathue, maloaiSach, trangthai,tenloai));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean themSach(Sach sach) {
        ContentValues values = new ContentValues();
        values.put("tensach", sach.getTensach());
        values.put("tacgia", sach.getTacgia());
        values.put("giathue", sach.getGiathue());
        values.put("trangthai", sach.getTrangthai());
        values.put("maloai", sach.getMaloai());
        long result = db.insert("SACH", null, values);
        return result != -1;
    }

    public boolean capNhatThongTinSach(int masach,String tensach,String tacgia,int giathue,int maloai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensach",tensach);
        values.put("tacgia",tacgia);
        values.put("giathue",giathue);
        values.put("maloai",maloai);
        long check = sqLiteDatabase.update("SACH",values,"masach=?",new String[]{String.valueOf(masach)});
        if(check == -1)
            return false;
        return true;
    };


}
