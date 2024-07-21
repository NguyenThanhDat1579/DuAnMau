package com.example.duanmau.dao;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duanmau.database.DbHelper;

public class ThuThuDAO {
    DbHelper dbhelper;
    SharedPreferences sharedPreferences;
    public ThuThuDAO(Context context) {
        dbhelper = new DbHelper(context);
        sharedPreferences = context.getSharedPreferences("THONGTIN", MODE_PRIVATE);
    }

    //đăng nhập
    public boolean checkDangNhap(String matt, String matkhau){
        SQLiteDatabase sqliteDatabase = dbhelper.getReadableDatabase();

        //matt, hotentt, matkhau, loaitk
        Cursor cursor = sqliteDatabase.rawQuery("SELECT * FROM THUTHU WHERE matt = ? AND matkhau= ?", new String[]{matt, matkhau});
        if(cursor.getCount() !=0 ){
           cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("matt", cursor.getString(0));
            editor.putString("loaitk", cursor.getString(4));
            editor.commit();
            return true;
        }else{
            return false;
        }
    }



    public boolean dangky(String username, String hoten, String pass, String email){
        Log.d("TEST",hoten);
        Log.d("TEST", email);
        SQLiteDatabase sqliteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("matt", username);
        contentValues.put("email", email);
        contentValues.put("matkhau", pass);
        contentValues.put("hotentt", hoten);

        long check = sqliteDatabase.insert("THUTHU", null, contentValues);
        if(check != -1){
            return true;
        }else{
            return false;
        }

    }

    // quên
    public String quenmk(String email){
        SQLiteDatabase sqliteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqliteDatabase.rawQuery("SELECT matkhau FROM THUTHU WHERE email = ?", new String[]{email});
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            return cursor.getString(0);
        }else{
            return "";
        }
    }


}
