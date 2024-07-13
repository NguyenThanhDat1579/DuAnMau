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

import com.example.duanmau.dao.ThuThuDAO;
import com.google.android.material.textfield.TextInputEditText;

public class DangKyActivity extends AppCompatActivity {
    TextInputEditText edTenDangKy, edDangKyEmail, edPasswordDangKy, edRePasswordDangKy;
    Button btnDangKyTk, btnTroVe;


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

        ThuThuDAO thuthuDao = new ThuThuDAO(this);

        btnDangKyTk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edTenDangKy.getText().toString();
                String mail = edDangKyEmail.getText().toString();
                String pass = edPasswordDangKy.getText().toString();
                String repass = edRePasswordDangKy.getText().toString();

                if(!pass.equals(repass)){
                    Toast.makeText(DangKyActivity.this, "Kiểm tra lại mật khẩu", Toast.LENGTH_SHORT).show();
                }else{
                    boolean check = thuthuDao.dangky(user, " ", pass, mail);
                    if(check){
                        Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(DangKyActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangKyActivity.this, ThanhVienDangNhapActivity.class));
            }
        });
    }
}