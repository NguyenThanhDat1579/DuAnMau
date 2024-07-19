package com.example.duanmau.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.dao.LoaiSachDAO;
import com.example.duanmau.model.LoaiSach;
import com.example.duanmau.model.ThanhVien;
import com.google.android.material.textfield.TextInputEditText;


import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder> {

    private Context context;
    private ArrayList<LoaiSach> list;
    LoaiSachDAO loaiSachDAO;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> list) {
        this.context = context;
        this.list = list;
        this.loaiSachDAO = new LoaiSachDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = ((Activity)context).getLayoutInflater();
        View view = inf.inflate(R.layout.item_recycler_loaisach, parent, false);
        return new LoaiSachAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSachAdapter.ViewHolder holder, int position) {
        holder.txtMaLoai.setText("Mã loại: LS" +list.get(position).getMaloai());
        holder.txtTenLoai.setText("Tên loại:  " +list.get(position).getTenloai());

        holder.ivSuaLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUpdate(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtMaLoai, txtTenLoai;
        ImageView ivSuaLoaiSach, ivXoaLoaiSach;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaLoai = itemView.findViewById(R.id.txtMaLoai);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            ivSuaLoaiSach = itemView.findViewById(R.id.ivSuaLoaiSach);
            ivXoaLoaiSach = itemView.findViewById(R.id.ivXoaLoaiSach);


        }
    }



    public void showDialogUpdate(LoaiSach loaiSach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sua_loaisach, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        EditText edTenLoai = view.findViewById(R.id.edTenLoai);



        Button btnSua = view.findViewById(R.id.btnSua);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        //dua du lieu can sua len edit
        edTenLoai.setText(loaiSach.getTenloai());



        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maloai = loaiSach.getMaloai();
                String tenloai = edTenLoai.getText().toString();



                if (tenloai.length() == 0){
                    Toast.makeText(context, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    LoaiSach loaisachChinhSua = new LoaiSach(maloai, tenloai);
                    boolean check = loaiSachDAO.chinhSua(loaisachChinhSua);
                    if (check){
                        Toast.makeText(context, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();

                        loadData();

                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(context, "Chỉnh sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void loadData(){
        list.clear();
        list = loaiSachDAO.getDSLoaiSach();
        notifyDataSetChanged();
    }
}
