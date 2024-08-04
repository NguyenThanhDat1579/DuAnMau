package com.example.duanmau.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.duanmau.R;

public class HomeThanhVienFragment extends Fragment {
    Button btnPhieuMuonTV, btnTongHopSach, btnQlLoaiSach, btnThongKe, btnDanhDau;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_thanhvien, container, false);

        //anh xa
        btnPhieuMuonTV = view.findViewById(R.id.btnPhieuMuonTV);
//        btnTongHopSach = view.findViewById(R.id.btnTongHopSach);
        btnQlLoaiSach = view.findViewById(R.id.btnQlLoaiSach);
        btnThongKe = view.findViewById(R.id.btnThongKe);
        btnDanhDau = view.findViewById(R.id.btnDanhDau);




        btnPhieuMuonTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayout, new PhieuMuonTVFragment()).commit();
            }
        });

        btnDanhDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayout, new DanhDauFragment()).commit();
            }
        });

        btnQlLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayout, new QLLoaiSachTVFragment()).commit();
            }
        });

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayout, new Top10SachMuonFragment()).commit();
            }
        });

        return view;
    }
}
