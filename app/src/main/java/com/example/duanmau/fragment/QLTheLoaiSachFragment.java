package com.example.duanmau.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.adapter.LoaiSachAdapter;
import com.example.duanmau.adapter.ThanhVienAdapter;
import com.example.duanmau.dao.LoaiSachDAO;
import com.example.duanmau.dao.ThanhVienDAO;
import com.example.duanmau.model.LoaiSach;
import com.example.duanmau.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class QLTheLoaiSachFragment extends Fragment {

    private RecyclerView recyclerLoaiSach;
    private LoaiSachDAO loaiSachDAO;
    private FloatingActionButton floatAddLoaiSach;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qltheloaisach, container, false);

        //anh xa
        recyclerLoaiSach = view.findViewById(R.id.recyclerLoaiSach);
        floatAddLoaiSach = view.findViewById(R.id.floatAddLoaiSach);

        loaiSachDAO = new LoaiSachDAO(getContext());
        loadData();

        floatAddLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });

        return view;

    }


    private void loadData() {
        ArrayList<LoaiSach> list = loaiSachDAO.getDSLoaiSach();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerLoaiSach.setLayoutManager(linearLayoutManager);
        LoaiSachAdapter adapter = new LoaiSachAdapter(getContext(), list, new LoaiSachAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LoaiSach loaiSach) {
                // Chuyển sang SachFragment và truyền ID của loại sách
                QLSachFragment sachFragment = new QLSachFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("maloai", loaiSach.getMaloai());
                bundle.putString("tenloai", loaiSach.getTenloai());
                sachFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, sachFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        recyclerLoaiSach.setAdapter(adapter);
    }


    private void showDialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_loaisach, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        //xu ly chuc nang
//        EditText edMaLoai = view.findViewById(R.id.edMaLoai);
        EditText edTenLoai = view.findViewById(R.id.edTenLoai);
        Button btnThem = view.findViewById(R.id.btnThem);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String maloai = edMaLoai.getText().toString()+"";
                String tenloai = edTenLoai.getText().toString();


                // validate - bắt lỗi

                edTenLoai.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0) {
                            edTenLoai.setError("Vui lòng nhập tên loại sách");
                        } else {
                            edTenLoai.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


                if (tenloai.equals("")) {
                    Toast.makeText(getContext(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    if (tenloai.equals("")) {
                        edTenLoai.setError("Vui lòng nhập tên loại sách");
                    } else {
                        edTenLoai.setError(null);
                    }
                } else {
                    boolean check = loaiSachDAO.themLoaiSach(tenloai);
                    if (check) {
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
}


