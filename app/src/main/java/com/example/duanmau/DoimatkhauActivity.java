package com.example.duanmau;

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

import com.example.duanmau.dao.ThanhVienDao;

public class DoimatkhauActivity extends AppCompatActivity {
    EditText edPassOld, edPassNew, edRePassNew;
    Button btnDoiMatKhau, btnHuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doimatkhau);
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
                    SharedPreferences sharedPreferences = getSharedPreferences("ThongTinThanhVien", MODE_PRIVATE);
                    String matv = sharedPreferences.getString("matv", "");
                    //cập nhật
                    ThanhVienDao thanhVienDao = new ThanhVienDao(DoimatkhauActivity.this);
                    boolean check = thanhVienDao.DoiMatKhau(matv, oldpass, newpass);
                    if (check) {
                        Toast.makeText(DoimatkhauActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(DoimatkhauActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(DoimatkhauActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
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