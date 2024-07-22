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
    Button btnPhieuMuonTV, btnTongHopSach, btnDanhDau, btnThongKe;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_thanhvien, container, false);

        //anh xa
        btnPhieuMuonTV = view.findViewById(R.id.btnPhieuMuonTV);
        btnTongHopSach = view.findViewById(R.id.btnTongHopSach);
        btnDanhDau = view.findViewById(R.id.btnDanhDau);
        btnThongKe = view.findViewById(R.id.btnThongKe);




        btnPhieuMuonTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayout, new PhieuMuonTVFragment()).commit();
            }
        });

        btnTongHopSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayout, new TongHopSachFragment()).commit();
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
