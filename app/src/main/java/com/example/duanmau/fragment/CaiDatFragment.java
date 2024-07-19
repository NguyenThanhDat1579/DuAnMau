package com.example.duanmau.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duanmau.DoiMatKhauActivity;
import com.example.duanmau.R;
import com.example.duanmau.adapter.ThanhVienAdapter;
import com.example.duanmau.dao.ThanhVienDAO;
import com.example.duanmau.model.ThanhVien;

import java.util.ArrayList;

public class CaiDatFragment extends Fragment {
    TextView txtDoiMatKhau, txtMaTV, txtTenTV, txtEmail, txtTenDangNhap;
    ThanhVienAdapter thanhVienAdapter;
    ThanhVienDAO thanhVienDao;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_caidat, container, false);
        txtDoiMatKhau = (TextView) view.findViewById(R.id.txtDoiMatKhau);
        txtMaTV = (TextView) view.findViewById(R.id.txtMaTV);
        txtTenTV = (TextView) view.findViewById(R.id.txtTenTV);
        txtEmail = (TextView) view.findViewById(R.id.txtEmail);
        txtTenDangNhap = (TextView) view.findViewById(R.id.txtTenDangNhap);

        thanhVienDao = new ThanhVienDAO(getContext());
        thanhVienDao = new ThanhVienDAO(getContext());

        // Lấy thông tin thành viên đang đăng nhập
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Thong_tin_thanh_vien", Context.MODE_PRIVATE);
        int maThanhVienDangNhap = sharedPreferences.getInt("maThanhVien", -1);

        if (maThanhVienDangNhap != -1) {
            ThanhVien thanhVienDangNhap = thanhVienDao.getThanhVienById(maThanhVienDangNhap);

            if (thanhVienDangNhap != null) {
                txtMaTV.setText(String.valueOf(thanhVienDangNhap.getMatv()));
                txtTenTV.setText(thanhVienDangNhap.getTentv());
                txtEmail.setText(thanhVienDangNhap.getEmail());
                txtTenDangNhap.setText(thanhVienDangNhap.getTendangnhap());
            }
        }



        txtDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(getActivity(), DoiMatKhauActivity.class));
            }
        });

        return view;
    }

}
