package com.example.duanmau;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duanmau.dao.ThuThuDAO;
import com.google.android.material.textfield.TextInputEditText;

public class ThanhVienDangNhapActivity extends AppCompatActivity {
    TextInputEditText edTenDangNhapThanhVien, edPasswordThanhVien;
    Button btnLoginThanhVien, btnDangKyThanhVien;
    CheckBox checkBoxThanhVien;
    TextView txtmk;
    ThuThuDAO thuthuDao;

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
        txtmk = findViewById(R.id.txtQuenMatKhau);

        thuthuDao = new ThuThuDAO(this);
        btnLoginThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edTenDangNhapThanhVien.getText().toString();
                String pass = edPasswordThanhVien.getText().toString();

                if (thuthuDao.checkDangNhap(user, pass)) {
                    // luu
                    startActivity(new Intent(ThanhVienDangNhapActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(ThanhVienDangNhapActivity.this, "username và mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDangKyThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ThanhVienDangNhapActivity.this, DangKyActivity.class));
            }
        });
        txtmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(ThanhVienDangNhapActivity.this, DangKyActivity.class));
                showDialogForgot();
            }
        });
    }
        private void showDialogForgot(){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inf = getLayoutInflater();
            View view = inf.inflate(R.layout.forgot,null);
            builder.setView(view);
            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();

            // ánh xạ
            EditText edtemail = view.findViewById(R.id.edtEmail);
            Button btnsend = view.findViewById(R.id.btnSend);
            Button btncancel = view.findViewById(R.id.btncancel);

            btncancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });

            btnsend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = edtemail.getText().toString();
                    String matkhau = thuthuDao.quenmk(email);
                    Toast.makeText(ThanhVienDangNhapActivity.this, matkhau, Toast.LENGTH_SHORT).show();
                }
            });
    }
}