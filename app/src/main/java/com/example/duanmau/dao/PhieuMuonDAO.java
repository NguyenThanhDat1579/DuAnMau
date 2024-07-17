package com.example.duanmau.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.database.DbHelper;
import com.example.duanmau.model.PhieuMuon;

import java.util.ArrayList;

public class PhieuMuonDAO {
    DbHelper dbHelper;
    public PhieuMuonDAO(Context context){
        dbHelper  = new DbHelper(context);
    }
    public ArrayList<PhieuMuon> getAllPhieuMuon() {
        ArrayList<PhieuMuon> listPM = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select pm.mapm, pm.matv, tv.tentv, pm.matt, tt.hotentt, pm.masach, sc.tensach, pm.ngaymuon, pm.ngaytra, pm.trangthai, pm.tienthue from PHIEUMUON pm, THANHVIEN tv, THUTHU tt, SACH sc WHERE pm.matv = tv.matv AND pm.matt = tt.matt AND pm.masach = sc.masach\n", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                listPM.add(new PhieuMuon(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getInt(9), cursor.getInt(10)));
            } while (cursor.moveToNext());
        }
        return listPM;
    }
}
