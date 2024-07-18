package com.example.duanmau.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ThanhVien> list;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = ((Activity)context).getLayoutInflater();
        View view = inf.inflate(R.layout.item_recycler_thanhvien, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaTV.setText(list.get(position).getMatv()+"");
        holder.txtTenTV.setText(list.get(position).getTentv());
        holder.txtEmail.setText(list.get(position).getEmail());
        holder.txtTendangnhap.setText(list.get(position).getTendangnhap());
        holder.txtMatkhau.setText(list.get(position).getMatkhau());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtMaTV, txtTenTV, txtEmail, txtTendangnhap, txtMatkhau;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaTV = itemView.findViewById(R.id.txtMaTV);
            txtTenTV = itemView.findViewById(R.id.txtTenTV);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtTendangnhap = itemView.findViewById(R.id.txtTenDangNhap);
            txtMatkhau = itemView.findViewById(R.id.txtMatkhau);


        }
    }
}
