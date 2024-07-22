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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duanmau.dao.ThuThuDAO;
import com.google.android.material.textfield.TextInputLayout;

public class AdminDangNhap extends AppCompatActivity {

    Button btnDangNhap;
    EditText edTenDangNhap, edPassword;
    CheckBox chkGhiNho;
    TextInputLayout notiTenDangNhap, notiPassword;

    ThuThuDAO thuThuDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dang_nhap);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnDangNhap = findViewById(R.id.btnDangNhap);
        edTenDangNhap = findViewById(R.id.edTenDangNhap);
        edPassword = findViewById(R.id.edPassword);
        chkGhiNho = findViewById(R.id.chKGhiNho);
        notiTenDangNhap = findViewById(R.id.notiTenDangNhap);
        notiPassword = findViewById(R.id.notiPassword);

        thuThuDAO = new ThuThuDAO(this);


        // kiểm tra thông tin đăng nhập, người dùng có lưu lại hay ko?
        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);

        boolean isRemember = sharedPreferences.getBoolean("isRemember", false);
        if (isRemember) {
            String user = sharedPreferences.getString("matt", "");
            String pass = sharedPreferences.getString("passtt", "");
            edTenDangNhap.setText(user);
            edPassword.setText(pass);
            chkGhiNho.setChecked(isRemember);
        }

        // validate - bắt lỗi
        edTenDangNhap.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    notiTenDangNhap.setError("Vui lòng nhập username");
                } else {
                    notiTenDangNhap.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        edPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    notiPassword.setError("Vui lòng nhập password");
                } else {
                    notiPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String user = edTenDangNhap.getText().toString();
                String pass = edPassword.getText().toString();

                SharedPreferences preferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();


                if (user.equals("")) {
                    notiTenDangNhap.setError("Vui lòng nhập username");
                } else {
                    notiTenDangNhap.setError(null);
                }

                if (pass.equals("")) {
                    notiPassword.setError("Vui lòng nhập password");
                } else {
                    notiPassword.setError(null);
                }
                if (user.length() > 0 && pass.length() > 0) {
                    if (thuThuDAO.checkDangNhap(user, pass)) {
                        // Lưu thông tin đăng nhập và loại tài khoản vào SharedPreferences
                        editor.putString("matt", user);
                        editor.putString("passtt", pass);
                        if (chkGhiNho.isChecked()) {
                            editor.putBoolean("isRemember", true);
                        } else {
                            editor.putBoolean("isRemember", false);
                        }
                        editor.apply();


                        Toast.makeText(AdminDangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdminDangNhap.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // out ứng dụng tại Main
                        startActivity(intent);
                    } else {
                        Toast.makeText(AdminDangNhap.this, "Tên đăng nhập hoặc Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AdminDangNhap.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}