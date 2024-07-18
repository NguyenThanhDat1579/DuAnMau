package com.example.duanmau;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duanmau.dao.ThanhVienDAO;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class DangKyActivity extends AppCompatActivity {
    TextInputEditText edTenDangKy, edHoten, edDangKyEmail, edPasswordDangKy, edRePasswordDangKy;
    Button btnDangKyTk, btnTroVe;
    ThanhVienDAO thanhvienDao;
    TextInputLayout notiTenDangKy, notiHoten, notiEmail, notiPassword, notiRePassword;

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
        edHoten = findViewById(R.id.edHoTen);
        edDangKyEmail = findViewById(R.id.edDangKyEmail);
        edPasswordDangKy = findViewById(R.id.edPasswordDangKy);
        edRePasswordDangKy = findViewById(R.id.edRePasswordDangKy);
        btnDangKyTk = findViewById(R.id.btnDangKyTk);
        btnTroVe = findViewById(R.id.btnTroVe);
        notiTenDangKy = findViewById(R.id.notiTenDangKy);
        notiHoten = findViewById(R.id.notiHoten);
        notiEmail = findViewById(R.id.notiEmail);
        notiPassword = findViewById(R.id.notiPassword);
        notiRePassword = findViewById(R.id.notiRePassword);

        thanhvienDao = new ThanhVienDAO(this);

        btnDangKyTk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edTenDangKy.getText().toString();
                String hoten = edHoten.getText().toString();
                String email = edDangKyEmail.getText().toString();
                String pass = edPasswordDangKy.getText().toString();
                String rePass = edRePasswordDangKy.getText().toString();

                // validate - bắt lỗi

                edTenDangKy.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length() == 0){
                            notiTenDangKy.setError("Vui lòng nhập username");
                        } else {
                            notiTenDangKy.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                edHoten.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length() == 0){
                            notiHoten.setError("Vui lòng nhập họ tên");
                        } else {
                            notiHoten.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                edDangKyEmail.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length() == 0){
                            notiEmail.setError("Vui lòng nhập email");
                        } else {
                            notiEmail.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                edPasswordDangKy.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length() == 0){
                            notiPassword.setError("Vui lòng nhập password");
                        } else {
                            notiPassword.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                edRePasswordDangKy.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length() == 0){
                            notiRePassword.setError("Vui lòng lại nhập password");
                        } else {
                            notiRePassword.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                if(user.equals("") || hoten.equals("") || email.equals("") || pass.equals("") || rePass.equals("")) {
                    Toast.makeText(DangKyActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    if (user.equals("")) {
                        notiTenDangKy.setError("Vui lòng nhập username");
                    } else {
                        notiTenDangKy.setError(null);
                    }
                    if (hoten.equals("")) {
                        notiHoten.setError("Vui lòng nhập họ tên");
                    } else {
                        notiHoten.setError(null);
                    }
                    if (email.equals("")) {
                        notiEmail.setError("Vui lòng nhập email");
                    } else {
                        notiEmail.setError(null);
                    }
                    if (pass.equals("")) {
                        notiPassword.setError("Vui lòng nhập password");
                    } else {
                        notiPassword.setError(null);
                    }

                    if (rePass.equals("")) {
                        notiRePassword.setError("Vui lòng nhập lại password");
                    } else {
                        notiRePassword.setError(null);
                    }
                } else if (!pass.equals(rePass)){
                    Toast.makeText(DangKyActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = thanhvienDao.dangKy(user,hoten,email,pass);
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