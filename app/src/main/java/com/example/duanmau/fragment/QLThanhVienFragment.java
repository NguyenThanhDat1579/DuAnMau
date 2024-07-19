package com.example.duanmau.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.DangKyActivity;
import com.example.duanmau.R;
import com.example.duanmau.adapter.ThanhVienAdapter;
import com.example.duanmau.dao.ThanhVienDAO;
import com.example.duanmau.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class QLThanhVienFragment extends Fragment {

    private RecyclerView recyclerThanhVien;
    private ThanhVienDAO thanhVienDao;
    private FloatingActionButton floatAddThanhVien;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlthanhvien, container, false);

        //anh xa
        recyclerThanhVien = view.findViewById(R.id.recyclerThanhVien);
        floatAddThanhVien = view.findViewById(R.id.floatAddThanhVien);


        thanhVienDao = new ThanhVienDAO(getContext());
        loadData();

        floatAddThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });




        return view;
    }


    private void loadData(){
        ArrayList<ThanhVien> list = thanhVienDao.getDSThanhVien();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerThanhVien.setLayoutManager(linearLayoutManager);
        ThanhVienAdapter adapter = new ThanhVienAdapter(getContext(), list);
        recyclerThanhVien.setAdapter(adapter);
    }


    private void showDialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_thanhvien, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        //xu ly chuc nang
        TextInputEditText edTenDangKy = view.findViewById(R.id.edTenDangKy);
        TextInputEditText edHoten = view.findViewById(R.id.edHoTen);
        TextInputEditText edDangKyEmail = view.findViewById(R.id.edDangKyEmail);
        TextInputEditText edPasswordDangKy = view.findViewById(R.id.edPasswordDangKy);
        TextInputEditText edRePasswordDangKy = view.findViewById(R.id.edRePasswordDangKy);
        TextInputLayout notiTenDangKy = view.findViewById(R.id.notiTenDangKy);
        TextInputLayout notiHoten = view.findViewById(R.id.notiHoten);
        TextInputLayout notiEmail = view.findViewById(R.id.notiEmail);
        TextInputLayout notiPassword = view.findViewById(R.id.notiPassword);
        TextInputLayout notiRePassword = view.findViewById(R.id.notiRePassword);
        Button btnThem = view.findViewById(R.id.btnThem);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edTenDangKy.getText().toString();
                String hoten = edHoten.getText().toString();
                String email = edDangKyEmail.getText().toString();
                String pass = edPasswordDangKy.getText().toString();
                String rePass = edRePasswordDangKy.getText().toString();

                // validate - bắt lỗi

                edTenDangKy.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length() == 0){
                            notiTenDangKy.setError("Vui lòng nhập username");
                        } else {
                            notiTenDangKy.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                edHoten.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length() == 0){
                            notiHoten.setError("Vui lòng nhập họ tên");
                        } else {
                            notiHoten.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                edDangKyEmail.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length() == 0){
                            notiEmail.setError("Vui lòng nhập email");
                        } else {
                            notiEmail.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                edPasswordDangKy.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length() == 0){
                            notiPassword.setError("Vui lòng nhập password");
                        } else {
                            notiPassword.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                edRePasswordDangKy.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length() == 0){
                            notiRePassword.setError("Vui lòng lại nhập password");
                        } else {
                            notiRePassword.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                if(user.equals("") || hoten.equals("") || email.equals("") || pass.equals("") || rePass.equals("")) {
                    Toast.makeText(getContext(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    if (user.equals("")) {
                        notiTenDangKy.setError("Vui lòng nhập username");
                    } else {
                        notiTenDangKy.setError(null);
                    }
                    if (hoten.equals("")) {
                        notiHoten.setError("Vui lòng nhập họ tên");
                    } else {
                        notiHoten.setError(null);
                    }
                    if (email.equals("")) {
                        notiEmail.setError("Vui lòng nhập email");
                    } else {
                        notiEmail.setError(null);
                    }
                    if (pass.equals("")) {
                        notiPassword.setError("Vui lòng nhập password");
                    } else {
                        notiPassword.setError(null);
                    }

                    if (rePass.equals("")) {
                        notiRePassword.setError("Vui lòng nhập lại password");
                    } else {
                        notiRePassword.setError(null);
                    }
                } else if (!pass.equals(rePass)){
                    Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = thanhVienDao.dangKy(user,hoten,email,pass);
                    if(check){
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        alertDialog.dismiss();
                    }else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
}
