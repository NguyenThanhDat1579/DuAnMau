package com.example.duanmau;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duanmau.dao.ThanhVienDao;
import com.google.android.material.textfield.TextInputEditText;

public class ThanhVienDangNhapActivity extends AppCompatActivity {
    TextInputEditText edTenDangNhapThanhVien, edPasswordThanhVien;
    Button btnLoginThanhVien, btnDangKyThanhVien;
    CheckBox chkGhiNhoTV;
    ThanhVienDao thanhvienDao;
    TextView txtQuenMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thanh_vien_dang_nhap);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edTenDangNhapThanhVien = findViewById(R.id.edTenDangNhapThanhVien);
        edPasswordThanhVien = findViewById(R.id.edPasswordThanhVien);
        btnLoginThanhVien = findViewById(R.id.btnLoginThanhVien);
        btnDangKyThanhVien = findViewById(R.id.btnDangKyThanhVien);
        chkGhiNhoTV = findViewById(R.id.chkGhiNhoTV);
        txtQuenMatKhau = findViewById(R.id.txtQuenMatKhau);
        thanhvienDao = new ThanhVienDao(this);

        // kiểm tra thông tin đăng nhập, người dùng có lưu lại hay ko?
        SharedPreferences sharedPreferences = getSharedPreferences("Thong_tin_thanh_vien", MODE_PRIVATE);
        boolean isRemember = sharedPreferences.getBoolean("isRemember", false);
        if(isRemember){
            String user = sharedPreferences.getString("usertv", "");
            String pass = sharedPreferences.getString("passtv", "");
            edTenDangNhapThanhVien.setText(user);
            edPasswordThanhVien.setText(pass);
            chkGhiNhoTV.setChecked(isRemember);
        }


        // validate - bắt lỗi
        edTenDangNhapThanhVien.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    edTenDangNhapThanhVien.setError("Vui lòng nhập username");
                } else {
                    edTenDangNhapThanhVien.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edPasswordThanhVien.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    edPasswordThanhVien.setError("Vui lòng nhập username");
                } else {
                    edPasswordThanhVien.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btnLoginThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usertv = edTenDangNhapThanhVien.getText().toString();
                String passtv = edPasswordThanhVien.getText().toString();

                if (usertv.equals("")) {
                    edTenDangNhapThanhVien.setError("Vui lòng nhập username");
                } else {
                    edTenDangNhapThanhVien.setError(null);
                }

                if (passtv.equals("")) {
                    edPasswordThanhVien.setError("Vui lòng nhập password");
                } else {
                    edPasswordThanhVien.setError(null);
                }

                if (usertv.length() > 0 && passtv.length() > 0) {
                    if (thanhvienDao.checkDangNhapThanhVien(usertv, passtv)) {
                        //lưu sharedpreferecens
                        if(chkGhiNhoTV.isChecked()){
                            SharedPreferences preferences = getSharedPreferences("Thong_tin_thanh_vien", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("usertv", usertv);
                            editor.putString("passtv", passtv);
                            editor.putBoolean("isRemember", chkGhiNhoTV.isChecked());
                            editor.apply();
                        } else {
                            SharedPreferences preferences = getSharedPreferences("Thong_tin_thanh_vien", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.clear();
                            editor.apply();
                        }

                        Intent intent = new Intent(ThanhVienDangNhapActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // out ứng dụng tại Main
                        startActivity(intent);
                    } else {
                        Toast.makeText(ThanhVienDangNhapActivity.this, "Tên đăng nhập hoặc Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ThanhVienDangNhapActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDangKyThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThanhVienDangNhapActivity.this, DangKyActivity.class));
            }
        });

        txtQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThanhVienDangNhapActivity.this, QuenMatKhauActivity.class));
            }
        });

    }
}