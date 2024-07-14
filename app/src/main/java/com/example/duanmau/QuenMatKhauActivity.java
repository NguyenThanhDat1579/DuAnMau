package com.example.duanmau;

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

import com.example.duanmau.dao.ThanhVienDao;
import com.example.duanmau.util.SendMail;
import com.google.android.material.textfield.TextInputEditText;

public class QuenMatKhauActivity extends AppCompatActivity {
    TextInputEditText edEmail;
    Button btnGuiMatKhau,btnHuy;
    ThanhVienDao thanhVienDao;
    SendMail sendMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quen_mat_khau);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edEmail = findViewById(R.id.edEmail);
        btnGuiMatKhau = findViewById(R.id.btnGuiMatKhau);
        btnHuy = findViewById(R.id.btnHuy);
        thanhVienDao = new ThanhVienDao(this);
        sendMail = new SendMail();

        btnGuiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmail.getText().toString();
                String matkhau = thanhVienDao.QuenMatKhau(email);
                
                if(matkhau.equals("")){
                    Toast.makeText(QuenMatKhauActivity.this, "Không tìm thấy email", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(QuenMatKhauActivity.this, "Đã gửi thành công", Toast.LENGTH_SHORT).show();
                    sendMail.Send(QuenMatKhauActivity.this, email, "LẤY LẠI MẬT KHẨU", "Mật khẩu của bạn là: "+matkhau);
                    finish();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();;
            }
        });

    }
}