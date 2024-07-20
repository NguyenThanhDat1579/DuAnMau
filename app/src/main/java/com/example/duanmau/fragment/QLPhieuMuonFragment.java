package com.example.duanmau.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.adapter.PhieuMuonAdapter;
import com.example.duanmau.dao.PhieuMuonDAO;
import com.example.duanmau.dao.SachDAO;
import com.example.duanmau.dao.ThanhVienDAO;
import com.example.duanmau.dao.ThuThuDAO;
import com.example.duanmau.model.PhieuMuon;
import com.example.duanmau.model.Sach;
import com.example.duanmau.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Calendar;
import java.util.Locale;

public class QLPhieuMuonFragment extends Fragment {
    PhieuMuonDAO phieuMuonDAO;
    ArrayList<PhieuMuon> listPM;
    RecyclerView lvPhieuMuon;
    FloatingActionButton floatAdd;
    ThuThuDAO thuThuDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phieumuon, container, false);


        // ánh xạ
        lvPhieuMuon = view.findViewById(R.id.lvPhieuMuon);
        floatAdd = view.findViewById(R.id.floatAdd);
        //data
        //adapter
        thuThuDAO = new ThuThuDAO(getContext());
        phieuMuonDAO = new PhieuMuonDAO(getContext());
        listPM = phieuMuonDAO.getAllPhieuMuon();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        lvPhieuMuon.setLayoutManager(linearLayoutManager);
        PhieuMuonAdapter adapter = new PhieuMuonAdapter(listPM, getContext());
        lvPhieuMuon.setAdapter(adapter);




        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tạo dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater layoutInflater = getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.dialog_them_phieumuon, null);
                Spinner spnThanhVien = view.findViewById(R.id.spnThanhVien);
                Spinner spnSach = view.findViewById(R.id.spnSach);
                Button btnHuy = view.findViewById(R.id.btnHuy);
                Button btnXacNhan = view.findViewById(R.id.btnXacNhan);
                EditText edNgayTra= view.findViewById(R.id.edNgayTra);
                getDataThanhVien(spnThanhVien);
                getDataSach(spnSach);
                builder.setView(view);
                // show dialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });

                btnXacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        // lấy mã thành viên
                        HashMap<String, Object> hsTV = (HashMap<String, Object>) spnThanhVien.getSelectedItem();
                        int matv = (int) hsTV.get("matv");
                        // lấy mã sách
                        HashMap<String, Object> hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                        int masach = (int) hsSach.get("masach");

                        // lấy tiền thuê
                        int giathue = (int) hsSach.get("giathue");

                        // lấy ngày trả
                        String ngaytra = edNgayTra.getText().toString();

                        themPhieuMuon(matv, masach, ngaytra,giathue);
                    }
                });
            }
        });


        return view;
    }

    private void getDataThanhVien(Spinner spnThanhVien){
        // lấy dữ liệu
        ThanhVienDAO thanhVienDao = new ThanhVienDAO(getContext());
        ArrayList<ThanhVien> listTV = thanhVienDao.getDSThanhVien();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (ThanhVien thanhVien : listTV) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("matv", thanhVien.getMatv());
            hashMap.put("tentv", thanhVien.getTentv());
            listHM.add(hashMap);
        }

        // set spinner của thành viên
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHM
                ,android.R.layout.simple_spinner_item,
                new String[]{"tentv"},
                new int[]{android.R.id.text1});
        spnThanhVien.setAdapter(simpleAdapter);

    }

    private void getDataSach(Spinner spnSach){
        // lấy dữ liệu
        SachDAO sachDAO= new SachDAO(getContext());
        ArrayList<Sach> listSach = sachDAO.getAllSach();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (Sach sach : listSach) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("masach", sach.getMasach());
            hashMap.put("tensach", sach.getTensach());
            hashMap.put("giathue", sach.getGiathue());
            listHM.add(hashMap);
        }

        // set spinner của sách
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHM
                ,android.R.layout.simple_spinner_item,
                new String[]{"tensach"},
                new int[]{android.R.id.text1});
        spnSach.setAdapter(simpleAdapter);

    }

    private void loadData(){
        phieuMuonDAO = new PhieuMuonDAO(getContext());
        listPM = phieuMuonDAO.getAllPhieuMuon();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        lvPhieuMuon.setLayoutManager(linearLayoutManager);
        PhieuMuonAdapter adapter = new PhieuMuonAdapter(listPM, getContext());
        lvPhieuMuon.setAdapter(adapter);
    }

    private void themPhieuMuon(int matv,int masach,String ngaytra,int giathue){
        //lấy mã thủ thư
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Thong_tin_thu_thu", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String matt = sharedPreferences.getString("usertt", "");


        //lấy ngày hiện tại
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(currentTime);

        PhieuMuon phieuMuon = new PhieuMuon(matv, matt, masach, ngay, ngaytra, 0, giathue);
        boolean kiemtra = phieuMuonDAO.themPhieuMuon(phieuMuon);
        if(kiemtra){
            Toast.makeText(getContext(), "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
            loadData();
        }else{
            Toast.makeText(getContext(), "Thêm phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
        }


    }

}
