package com.example.duanmau;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duanmau.dao.ThanhVienDAO;

public class DoiMatKhauActivity extends AppCompatActivity {
    EditText edPassOld, edPassNew, edRePassNew;
    Button btnDoiMatKhau, btnHuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doi_mat_khau);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edPassOld = findViewById(R.id.edPassOld);
        edPassNew = findViewById(R.id.edPassNew);
        edRePassNew = findViewById(R.id.edRePassNew);
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);
        btnHuy = findViewById(R.id.btnHuy);

        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpass = edPassOld.getText().toString();
                String newpass = edPassNew.getText().toString();
                String repass = edRePassNew.getText().toString();
                if(newpass.equals(repass)){
                    SharedPreferences sharedPreferences = getSharedPreferences("Thong_tin_thanh_vien", MODE_PRIVATE);
                    String usertv = sharedPreferences.getString("usertv", "");
                    //cập nhật
                    ThanhVienDAO thanhVienDao = new ThanhVienDAO(DoiMatKhauActivity.this);
                    boolean check = thanhVienDao.DoiMatKhau(usertv, oldpass, newpass);
                    if (check) {
                        Toast.makeText(DoiMatKhauActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();

                        //xoa sharedpreferences
                        SharedPreferences preferences = getSharedPreferences("Thong_tin_thanh_vien", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();

                        Intent intent = new Intent(DoiMatKhauActivity.this, ChonVaiTroActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else{
                        Toast.makeText(DoiMatKhauActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(DoiMatKhauActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}