package com.example.duanmau;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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


        ThuThuDAO thuthuDao = new ThuThuDAO(this);
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edTenDangNhap.getText().toString();
                String pass = edPassword.getText().toString();
                if(thuthuDao.checkDangNhap(user, pass)){
                    // luu

                    startActivity(new Intent(AdminDangNhap.this, MainActivity.class));
                }else{
                    Toast.makeText(AdminDangNhap.this, "username và mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}