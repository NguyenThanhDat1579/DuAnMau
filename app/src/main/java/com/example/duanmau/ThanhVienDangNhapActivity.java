package com.example.duanmau;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class ThanhVienDangNhapActivity extends AppCompatActivity {
    TextInputEditText edTenDangNhapThanhVien, edPasswordThanhVien;
    Button btnLoginThanhVien, btnDangKyThanhVien;
    CheckBox checkBoxThanhVien;

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

    }
}