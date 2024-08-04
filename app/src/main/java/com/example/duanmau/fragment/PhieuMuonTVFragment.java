package com.example.duanmau.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duanmau.R;
import com.example.duanmau.adapter.PhieuMuonTVAdapter;
import com.example.duanmau.dao.ThanhVienDAO;
import com.example.duanmau.dao.ThongKeDAO;
import com.example.duanmau.model.PhieuMuon;
import com.example.duanmau.model.ThanhVien;

import java.util.ArrayList;


public class PhieuMuonTVFragment extends Fragment {

    ThanhVienDAO thanhVienDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phieu_muon_tv, container, false);

        RecyclerView recyclerViewpmtv = view.findViewById(R.id.recyclerpmct);

        ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());

            thanhVienDao = new ThanhVienDAO(getContext());
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("Thong_tin_thanh_vien", Context.MODE_PRIVATE);
            int maThanhVienDangNhap = sharedPreferences.getInt("maThanhVien", -1);
            ArrayList<PhieuMuon> list = thongKeDAO.getpmtv(String.valueOf(maThanhVienDangNhap));

            if (maThanhVienDangNhap != -1) {
                ThanhVien thanhVienDangNhap = thanhVienDao.getThanhVienById(maThanhVienDangNhap);

                if (thanhVienDangNhap != null) {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    recyclerViewpmtv.setLayoutManager(linearLayoutManager);
                    PhieuMuonTVAdapter adapter = new PhieuMuonTVAdapter(getContext(), list);
                    recyclerViewpmtv.setAdapter(adapter);
                    return view;
                }
        }
        return view;
    }
}