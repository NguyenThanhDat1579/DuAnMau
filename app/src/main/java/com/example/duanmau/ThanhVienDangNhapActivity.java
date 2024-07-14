package com.example.duanmau;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    CheckBox checkBoxThanhVien;
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
        checkBoxThanhVien = findViewById(R.id.checkBoxThanhVien);
        txtQuenMatKhau = findViewById(R.id.txtQuenMatKhau);
        thanhvienDao = new ThanhVienDao(this);

        btnLoginThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usertv = edTenDangNhapThanhVien.getText().toString();
                String passtv = edPasswordThanhVien.getText().toString();
                if(thanhvienDao.checkDangNhapThanhVien(usertv,passtv)){
                    //lưu sharedpreferecens
                    SharedPreferences sharedPreferences = getSharedPreferences("ThongTinThanhVien", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("matv", usertv);
                    editor.commit();

                    startActivity(new Intent(ThanhVienDangNhapActivity.this, MainActivity.class));
                }else{
                    Toast.makeText(ThanhVienDangNhapActivity.this, "Tên đăng nhập và Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDangKyThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThanhVienDangNhapActivity.this,DangKyActivity.class));
            }
        });

        txtQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThanhVienDangNhapActivity.this,QuenMatKhauActivity.class));
            }
        });

    }
}