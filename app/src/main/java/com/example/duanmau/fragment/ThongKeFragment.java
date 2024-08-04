package com.example.duanmau.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.adapter.Top10Adapter;
import com.example.duanmau.dao.ThongKeDAO;
import com.example.duanmau.model.Sach;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ThongKeFragment extends Fragment {
    TextInputEditText edTuNgay, edDenNgay;
    Button btnDoanhThu;
    TextView txtDoanhThu;
    String ngaybatdau;
    String ngayketthuc;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongke, container, false);
        edTuNgay  = view.findViewById(R.id.edTuNgay);
        edDenNgay = view.findViewById(R.id.edDenNgay);
        btnDoanhThu = view.findViewById(R.id.btnDoanhThu);
        txtDoanhThu = view.findViewById(R.id.txtDoanhThu);



        Calendar calendar = Calendar.getInstance();

        edTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String ngay = "";
                                String thang = "";
                                if(dayOfMonth < 10){
                                    ngay = "0" + dayOfMonth;
                                }else{
                                    ngay = dayOfMonth + "";
                                }
                                if((month + 1) < 10){
                                    thang = "0" + (month + 1);
                                }else{
                                    thang = (month + 1) + "";
                                }


                                edTuNgay.setText(ngay + "/" +thang + "/" + year);
                                ngaybatdau = year + "/" +thang + "/" + ngay;
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });

        edDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String ngay = "";
                                String thang = "";
                                if(dayOfMonth < 10){
                                    ngay = "0" + dayOfMonth;
                                }else{
                                    ngay = dayOfMonth + "";
                                }
                                if((month + 1) < 10){
                                    thang = "0" + (month + 1);
                                }else{
                                    thang = (month + 1) + "";
                                }

                                edDenNgay.setText(ngay + "/" +thang + "/" + year);
                                 ngayketthuc = year + "/" +thang + "/" + ngay;
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });

        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edTuNgay.length() > 0 && edDenNgay.length() > 0){
                    ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
                    double doanhthu = thongKeDAO.getDoanhThu(ngaybatdau,ngayketthuc);
                    NumberFormat format = new DecimalFormat("#,###");
                    String formattedNumber = format.format(doanhthu);
                    txtDoanhThu.setText(formattedNumber + " VND");
                } else {
                    Toast.makeText(getContext(), "Vui lòng chọn đầy đủ ngày", Toast.LENGTH_SHORT).show();
                }


            }
        });

        return view;
    }
}

