package com.example.duanmau.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.adapter.SachAdapter;
import com.example.duanmau.adapter.ThanhVienAdapter;
import com.example.duanmau.dao.LoaiSachDAO;
import com.example.duanmau.dao.SachDAO;
import com.example.duanmau.dao.ThanhVienDAO;
import com.example.duanmau.model.LoaiSach;
import com.example.duanmau.model.PhieuMuon;
import com.example.duanmau.model.Sach;
import com.example.duanmau.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class TongHopSachFragment extends Fragment {

    RecyclerView recyclerTongHopSach;
    FloatingActionButton floatAddThemSach;
    SachDAO sachDAO;
    LoaiSachDAO loaiSachDAO;
    ArrayList<Sach> sachList = new ArrayList<>();
    int maloai;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tonghopsach, container, false);

        //anh xa
        recyclerTongHopSach = view.findViewById(R.id.recyclerTongHopSach);
        floatAddThemSach = view.findViewById(R.id.floatAddThemSach);
        sachDAO = new SachDAO(getContext());
        loadData();


        floatAddThemSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });


        return view;

    }

    private void loadData(){
        ArrayList<Sach> list = sachDAO.getAllSach();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerTongHopSach.setLayoutManager(linearLayoutManager);
        SachAdapter adapter = new SachAdapter(getContext(), list);
        recyclerTongHopSach.setAdapter(adapter);
    }

    private void getDataLoaiSach(Spinner spnLoaiSach){
        // lấy dữ liệu
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiSach> listLS = loaiSachDAO.getDSLoaiSach();

        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (LoaiSach loaiSach : listLS) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("maloai", loaiSach.getMaloai());
            hashMap.put("tenloai", loaiSach.getTenloai());
            list.add(hashMap);
        }

        // set spinner của loại sách
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                list
                ,android.R.layout.simple_spinner_item,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1});
        spnLoaiSach.setAdapter(simpleAdapter);

    }

    private void showDialogAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_sach, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        //xu ly chuc nang
        TextInputEditText edTenSach = view.findViewById(R.id.edTenSach);
        TextInputEditText edTenTacGia = view.findViewById(R.id.edTenTacGia);
        TextInputEditText edGiaThue = view.findViewById(R.id.edGiaThue);
        Spinner spnLoaiSach = view.findViewById(R.id.spnLoaiSach);
        Button btnThem = view.findViewById(R.id.btnThem);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}


