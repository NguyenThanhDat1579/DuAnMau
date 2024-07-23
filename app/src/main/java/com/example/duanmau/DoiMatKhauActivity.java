package com.example.duanmau;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.android.material.textfield.TextInputLayout;

public class DoiMatKhauActivity extends AppCompatActivity {
    EditText edPassOld, edPassNew, edRePassNew;
    TextInputLayout txtPassOld, txtPassNew, txtRePassNew;
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
        txtPassOld = findViewById(R.id.txtPassOld);
        txtPassNew = findViewById(R.id.txtPassNew);
        txtRePassNew = findViewById(R.id.txtRePassNew);
        btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);
        btnHuy = findViewById(R.id.btnHuy);

        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpass = edPassOld.getText().toString();
                String newpass = edPassNew.getText().toString();
                String repass = edRePassNew.getText().toString();

                // validate - bắt lỗi

                edPassOld.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0) {
                            txtPassOld.setError("Vui lòng nhập mật khẩu cũ");
                        } else {
                            txtPassOld.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                edPassNew.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0) {
                            txtPassNew.setError("Vui lòng nhập mật khẩu mới");
                        } else {
                            txtPassNew.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                edRePassNew.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0) {
                            txtRePassNew.setError("Vui lòng nhập lại mật khẩu mới");
                        } else {
                            txtRePassNew.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                if (oldpass.equals("") || newpass.equals("") || repass.equals("")) {
                    Toast.makeText(DoiMatKhauActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    if (oldpass.equals("")) {
                        txtPassOld.setError("Vui lòng nhập mật khẩu cũ");
                    } else {
                        txtPassOld.setError(null);
                    }
                    if (newpass.equals("")) {
                        txtPassNew.setError("Vui lòng nhập mật khẩu mới");
                    } else {
                        txtPassNew.setError(null);
                    }
                    if (repass.equals("")) {
                        txtRePassNew.setError("Vui lòng nhập lại mật khẩu mới");
                    } else {
                        txtRePassNew.setError(null);
                    }

                } else if (newpass.equals(repass)) {
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
                    } else {
                        Toast.makeText(DoiMatKhauActivity.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DoiMatKhauActivity.this, "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
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