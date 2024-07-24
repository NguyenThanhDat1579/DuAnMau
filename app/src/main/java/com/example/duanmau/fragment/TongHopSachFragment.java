package com.example.duanmau.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.adapter.SachAdapter;
import com.example.duanmau.adapter.ThanhVienAdapter;
import com.example.duanmau.dao.LoaiSachDAO;
import com.example.duanmau.dao.SachDAO;
import com.example.duanmau.dao.ThanhVienDAO;
import com.example.duanmau.model.LoaiSach;
import com.example.duanmau.model.PhieuMuon;
import com.example.duanmau.model.Sach;
import com.example.duanmau.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class TongHopSachFragment extends Fragment {

    RecyclerView recyclerTongHopSach;
    FloatingActionButton floatAddThemSach;
    SearchView searchBook;
    SachDAO sachDAO;
    LoaiSachDAO loaiSachDAO;
    ArrayList<Sach> sachList = new ArrayList<>();
    int maloai;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tonghopsach, container, false);

        //anh xa
        recyclerTongHopSach = view.findViewById(R.id.recyclerTongHopSach);
        floatAddThemSach = view.findViewById(R.id.floatAddThemSach);
        searchBook = view.findViewById(R.id.searchBook);
        sachDAO = new SachDAO(getContext());
        loadData(null);


        floatAddThemSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });

        searchBook.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadData(newText);
                return false;
            }
        });

        return view;

    }

    private void loadData(String text) {
        ArrayList<Sach> list = sachDAO.getAllSach();
        sachList.clear();  // Xóa danh sách trước khi thêm sách mới vào

        if (text != null && !text.isEmpty()) {
            for (Sach sach : list) {
                if (sach.getTensach().toLowerCase().startsWith(text.toLowerCase())) {
                    sachList.add(sach);
                }
            }
        } else {
            sachList.addAll(list);  // Nếu không có tìm kiếm, hiển thị toàn bộ sách
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerTongHopSach.setLayoutManager(linearLayoutManager);
        SachAdapter adapter = new SachAdapter(getContext(), sachList);
        recyclerTongHopSach.setAdapter(adapter);
    }

    private void getDataLoaiSach(Spinner spnLoaiSach) {
        // lấy dữ liệu
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiSach> listLS = loaiSachDAO.getDSLoaiSach();

        ArrayList<String> tenLoaiSachList = new ArrayList<>();
        for (LoaiSach loaiSach : listLS) {
            tenLoaiSachList.add(loaiSach.getTenloai());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                tenLoaiSachList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLoaiSach.setAdapter(adapter);

    }

    private void showDialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_sach, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        // Ánh xạ các thành phần trong dialog
        TextInputEditText edTenSach = view.findViewById(R.id.edTenSach);
        TextInputEditText edTacGia = view.findViewById(R.id.edTacGiaInput);
        TextInputEditText edGiaThue = view.findViewById(R.id.edGiaThueInput);

        Spinner spnLoaiSach = view.findViewById(R.id.spnLoaiSach);
        Button btnThem = view.findViewById(R.id.btnThem);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        // Lấy dữ liệu loại sách và thiết lập cho spinner
        getDataLoaiSach(spnLoaiSach);

        // Xử lý sự kiện nút hủy
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        // Xử lý sự kiện nút thêm
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = edTenSach.getText().toString().trim();
                String tacGia = edTacGia.getText().toString().trim();
                String giaThueStr = edGiaThue.getText().toString().trim();
                int viTriLoaiSach = spnLoaiSach.getSelectedItemPosition();

                // Kiểm tra thông tin đầu vào
                if (tenSach.isEmpty() || tacGia.isEmpty() || giaThueStr.isEmpty() || viTriLoaiSach < 0) {
                    Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int giaThue = Integer.parseInt(giaThueStr);
                LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
                int maloai = loaiSachDAO.getDSLoaiSach().get(viTriLoaiSach).getMaloai();

                Sach sach = new Sach();
                sach.setTensach(tenSach);
                sach.setTacgia(tacGia);
                sach.setGiathue(giaThue);
                sach.setMaloai(maloai);

                SachDAO sachDAO = new SachDAO(getContext());
                sachDAO.themSach(sach);

                // Cập nhật lại danh sách sách
                loadData(null);
                alertDialog.dismiss();
            }
        });

    }

}


