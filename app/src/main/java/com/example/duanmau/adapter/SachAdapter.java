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
import com.example.duanmau.model.LoaiSach;
import com.example.duanmau.model.Sach;

import java.util.ArrayList;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Sach> list;

    public SachAdapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = ((Activity)context).getLayoutInflater();
        View view = inf.inflate(R.layout.item_recycler_sach, parent, false);
        return new SachAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachAdapter.ViewHolder holder, int position) {

        Sach sach = list.get(position);
        holder.bind(sach);


        holder.txtMaSach.setText(list.get(position).getMasach()+"");
        holder.txtMaLoai.setText(list.get(position).getMaloai()+"");
        holder.txtTenLoai.setText(list.get(position).getTenloai());
        holder.txtTenSach.setText(list.get(position).getTensach());
        holder.txtGiaThue.setText(list.get(position).getGiathue()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtMaSach, txtMaLoai, txtTenLoai, txtTenSach, txtTacGia, txtGiaThue;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaSach = itemView.findViewById(R.id.txtMaSach);
            txtMaLoai = itemView.findViewById(R.id.txtMaLoai);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtTacGia = itemView.findViewById(R.id.txtTacGia);
            txtGiaThue = itemView.findViewById(R.id.txtGiaThue);

        }

        public void bind(Sach sach) {
            txtTenSach.setText(sach.getTensach());
            txtTacGia.setText(sach.getTacgia());
            txtGiaThue.setText(String.valueOf(sach.getGiathue()));
        }
    }
}
