package com.example.duanmau.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.database.DbHelper;
import com.example.duanmau.model.PhieuMuon;
import com.example.duanmau.model.Sach;

import java.util.ArrayList;

public class ThongKeDAO {
    DbHelper dbHelper;
    public ThongKeDAO(Context context){
        dbHelper = new DbHelper(context);

    }
    public ArrayList<Sach> getTop10(){
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT pm.masach, sc.tensach, count(pm.masach) FROM PHIEUMUON pm, SACH sc WHERE pm.masach = sc.masach GROUP BY pm.masach, sc.tensach ORDER BY COUNT(pm.masach) DESC LIMIT 10", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do{
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public int getDoanhThu(String ngaybatdau, String ngayketthuc){
        ngaybatdau = ngaybatdau.replace("/","");
        ngayketthuc = ngayketthuc.replace("/","");
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor  = sqLiteDatabase.rawQuery("SELECT SUM(tienthue) FROM PHIEUMUON  WHERE substr(ngaymuon,7)||substr(ngaymuon,4,2)||substr(ngaymuon,1,2) BETWEEN ? AND ?", new String[]{ngaybatdau, ngayketthuc});
        if(cursor.getCount() !=0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }

    public ArrayList<PhieuMuon> getpmtv(String maThanhVienDangNhap){
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT th.hotentt, tv.tentv, s.tensach,  pm.ngaymuon,  pm.ngaytra  FROM PHIEUMUON pm  INNER JOIN THUTHU th ON pm.matt = th.matt INNER JOIN THANHVIEN tv ON pm.matv = tv.matv INNER JOIN SACH s ON pm.masach = s.masach WHERE tv.matv = ?", new String[]{maThanhVienDangNhap});
           if( cursor.moveToFirst()){
            do{
                list.add(new PhieuMuon(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4)));
            }while (cursor.moveToNext());
        }
           cursor.close();
        return list;

    }
}
