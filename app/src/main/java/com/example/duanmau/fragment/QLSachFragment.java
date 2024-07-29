package com.example.duanmau.fragment;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.adapter.LoaiSachAdapter;
import com.example.duanmau.adapter.SachAdapter;
import com.example.duanmau.dao.LoaiSachDAO;
import com.example.duanmau.dao.SachDAO;
import com.example.duanmau.model.LoaiSach;
import com.example.duanmau.model.Sach;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;

public class QLSachFragment extends Fragment {

    private RecyclerView recyclerSach;
    private SachDAO sachDAO;
    private ArrayList<Sach> sachList = new ArrayList<>();
    private int maloai;
    private TextView txtTenLoaiSach;
    private String tenloai;



    public QLSachFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlsach, container, false);

        recyclerSach = view.findViewById(R.id.recyclerSach);
        txtTenLoaiSach = view.findViewById(R.id.txtTenLoaiSach);
        sachDAO = new SachDAO(getContext());

        if (getArguments() != null) {
            maloai = getArguments().getInt("maloai");
            tenloai = getArguments().getString("tenloai");
            txtTenLoaiSach.setText(tenloai);
            loadData(maloai);
        }



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerSach.setLayoutManager(linearLayoutManager);
        SachAdapter adapter = new SachAdapter(getContext(), sachList, getDSLoaiSach(), sachDAO, new SachAdapter.sachAdapterInterface() {
            @Override
            public void setImageNe(ImageView ivHinh) {

            }
        });
        recyclerSach.setAdapter(adapter);

        return view;
    }

    private void loadData(int maloai) {
        sachList = sachDAO.getSachByLoai(maloai);
    }

    private ArrayList<HashMap<String, Object>> getDSLoaiSach(){
        LoaiSachDAO loaiSachDAO= new LoaiSachDAO(getContext());
        ArrayList<LoaiSach> list = loaiSachDAO.getDSLoaiSach();
        ArrayList<HashMap<String,Object>> listHM=new ArrayList<>();

        for(LoaiSach loai : list){
            HashMap<String,Object> hs = new HashMap<>();
            hs.put("maloai",loai.getMaloai());
            hs.put("tenloai",loai.getTenloai());
            listHM.add(hs);
        }
        return listHM;
    }
}


