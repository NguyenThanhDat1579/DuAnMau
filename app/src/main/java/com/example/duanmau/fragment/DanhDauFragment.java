package com.example.duanmau.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.adapter.DanhDauAdapter;
import com.example.duanmau.database.DbHelper;
import com.example.duanmau.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class DanhDauFragment extends Fragment {
    private SQLiteDatabase db;
    private DbHelper dbHelper;
    private RecyclerView recyclerView;
    private DanhDauAdapter adapter;
    private ArrayList<Sach> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danhdau, container, false);

        dbHelper = new DbHelper(getContext());

        recyclerView = view.findViewById(R.id.recyclerDanhDau);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadDanhDauBooks();

        return view;
    }

    private void loadDanhDauBooks() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SharedPreferences preferences = getContext().getSharedPreferences("Thong_tin_thanh_vien", Context.MODE_PRIVATE);
        int matv = preferences.getInt("maThanhVien", -1);

        Cursor cursor = db.rawQuery("SELECT masach FROM DANHDAU WHERE matv = ?", new String[]{String.valueOf(matv)});

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        int masach = cursor.getInt(0);

                        Cursor bookCursor = db.rawQuery("SELECT masach, tensach, tacgia, giathue, trangthai, maloai, urlHinh FROM SACH WHERE masach = ?", new String[]{String.valueOf(masach)});
                        if (bookCursor != null) {
                            if (bookCursor.moveToFirst()) {
                                String tensach = bookCursor.getString(1);
                                String tacgia = bookCursor.getString(2);
                                int giathue = bookCursor.getInt(3);
                                int trangthai = bookCursor.getInt(4);
                                int maloai = bookCursor.getInt(5);
                                String urlHinh = bookCursor.getString(6);

                                list.add(new Sach(masach, tensach, tacgia, giathue, trangthai, maloai, urlHinh));
                            }
                            bookCursor.close();
                        }
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }

        adapter = new DanhDauAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
    }
}