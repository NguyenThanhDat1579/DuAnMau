package com.example.duanmau;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duanmau.dao.ThanhVienDao;
import com.google.android.material.textfield.TextInputEditText;

public class DangKyActivity extends AppCompatActivity {
    TextInputEditText edTenDangKy, edDangKyEmail, edPasswordDangKy, edRePasswordDangKy;
    Button btnDangKyTk, btnTroVe;
    ThanhVienDao thanhvienDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ky);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edTenDangKy = findViewById(R.id.edTenDangKy);
        edDangKyEmail = findViewById(R.id.edDangKyEmail);
        edPasswordDangKy = findViewById(R.id.edPasswordDangKy);
        edRePasswordDangKy = findViewById(R.id.edRePasswordDangKy);
        btnDangKyTk = findViewById(R.id.btnDangKyTk);
        btnTroVe = findViewById(R.id.btnTroVe);
        thanhvienDao = new ThanhVienDao(this);

        btnDangKyTk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edTenDangKy.getText().toString();
                String email = edDangKyEmail.getText().toString();
                String pass = edPasswordDangKy.getText().toString();
                String rePass = edRePasswordDangKy.getText().toString();

                // validate - bắt lỗi
                if(user.equals("") || email.equals("") || pass.equals("") || rePass.equals("")) {
                    Toast.makeText(DangKyActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    if (user.equals("")) {
                        edTenDangKy.setError("Vui lòng nhập username");
                    } else {
                        edTenDangKy.setError(null);
                    }
                    if (email.equals("")) {
                        edDangKyEmail.setError("Vui lòng nhập email");
                    } else {
                        edDangKyEmail.setError(null);
                    }
                    if (pass.equals("")) {
                        edPasswordDangKy.setError("Vui lòng nhập password");
                    } else {
                        edPasswordDangKy.setError(null);
                    }

                    if (rePass.equals("")) {
                        edRePasswordDangKy.setError("Vui lòng nhập lại password");
                    } else {
                        edRePasswordDangKy.setError(null);
                    }
                } else if (!pass.equals(rePass)){
                    Toast.makeText(DangKyActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = thanhvienDao.dangKy(user,email,pass);
                    if(check){
                        Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(DangKyActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });

        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}