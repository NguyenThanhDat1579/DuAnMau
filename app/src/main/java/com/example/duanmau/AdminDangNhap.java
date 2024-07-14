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

public class AdminDangNhap extends AppCompatActivity {

    Button btnDangNhap;
    EditText edTenDangNhap, edPassword;
    CheckBox chkGhiNho;

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

        thuThuDAO = new ThuThuDAO(this);


        // kiểm tra thông tin đăng nhập, người dùng có lưu lại hay ko?
        SharedPreferences sharedPreferences = getSharedPreferences("Thong_tin_thu_thu", MODE_PRIVATE);
        boolean isRemember = sharedPreferences.getBoolean("isRemember", false);
        if(isRemember){
            String user = sharedPreferences.getString("usertt", "");
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
                if(s.length() == 0){
                    edTenDangNhap.setError("Vui lòng nhập username");
                } else {
                    edTenDangNhap.setError(null);
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
                if(s.length() == 0){
                    edPassword.setError("Vui lòng nhập username");
                } else {
                    edPassword.setError(null);
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


                if(user.equals("")){
                    edTenDangNhap.setError("Vui lòng nhập username");
                } else {
                    edTenDangNhap.setError(null);
                }

                if(pass.equals("")){
                    edPassword.setError("Vui lòng nhập password");
                } else {
                    edPassword.setError(null);
                }
                if(user.length() > 0 && pass.length() > 0) {
                    if (thuThuDAO.checkDangNhap(user, pass)) {
                        //lưu sharedpreferecens
                        if(chkGhiNho.isChecked()){
                            SharedPreferences preferences = getSharedPreferences("Thong_tin_thu_thu", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("usertt", user);
                            editor.putString("passtt", pass);
                            editor.putBoolean("isRemember", chkGhiNho.isChecked());
                            editor.apply();
                        } else {
                            SharedPreferences preferences = getSharedPreferences("Thong_tin_thu_thu", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.clear();
                            editor.apply();
                        }


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