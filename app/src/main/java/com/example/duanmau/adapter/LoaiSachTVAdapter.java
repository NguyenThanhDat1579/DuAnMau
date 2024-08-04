package com.example.duanmau.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duanmau.R;
import com.example.duanmau.dao.LoaiSachDAO;
import com.example.duanmau.fragment.QLLoaiSachFragment;
import com.example.duanmau.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachTVAdapter extends RecyclerView.Adapter<LoaiSachTVAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(LoaiSach loaiSach);
    }

    private Context context;
    private ArrayList<LoaiSach> list;
    LoaiSachDAO loaiSachDAO;
    private OnItemClickListener listener;
    private LoaiSachTVAdapter.loaisachAdapterInterface loaisachAdapterInterface;


    public interface loaisachAdapterInterface {
        void setImage(ImageView ivHinh);
    }

    public LoaiSachTVAdapter(Context context, ArrayList<LoaiSach> list, OnItemClickListener listener, loaisachAdapterInterface loaisachAdapterInterface) {
        this.context = context;
        this.list = list;
        this.loaiSachDAO = new LoaiSachDAO(context);
        this.listener = listener;
        this.loaisachAdapterInterface = loaisachAdapterInterface;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = ((Activity) context).getLayoutInflater();
        View view = inf.inflate(R.layout.item_recycler_loaisach_tv, parent, false);
        return new LoaiSachTVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSachTVAdapter.ViewHolder holder, int position) {

        LoaiSach loaiSach = list.get(position);
        holder.bind(loaiSach, listener, loaisachAdapterInterface);

        holder.txtMaLoai.setText("Mã loại: LS" + list.get(position).getMaloai());
        holder.txtTenLoai.setText("Tên loại:  " + list.get(position).getTenloai());

        // Tải hình ảnh bằng Glide
        if (loaiSach.getUrlHinh() != null && !loaiSach.getUrlHinh().isEmpty()) {
            Glide.with(holder.itemView.getContext()).load(loaiSach.getUrlHinh()).into(holder.ivHinhLoaiSach);
        } else {
            holder.ivHinhLoaiSach.setImageResource(R.drawable.ic_books); // Ảnh mặc định nếu không có URL
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMaLoai, txtTenLoai;
        ImageView ivHinhLoaiSach;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaLoai = itemView.findViewById(R.id.txtMaLoai);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            ivHinhLoaiSach = itemView.findViewById(R.id.ivHinhLoaiSach);

        }

        public void bind(final LoaiSach loaiSach, final OnItemClickListener listener, final loaisachAdapterInterface loaisachAdapterInterface) {
            txtTenLoai.setText(loaiSach.getTenloai());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(loaiSach);
                }
            });
            
        }
    }

    public void loadData() {
        list.clear();
        list.addAll(loaiSachDAO.getDSLoaiSach());
        notifyDataSetChanged();
    }




}
