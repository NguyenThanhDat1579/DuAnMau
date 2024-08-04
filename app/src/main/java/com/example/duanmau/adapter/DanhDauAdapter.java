package com.example.duanmau.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duanmau.R;
import com.example.duanmau.dao.SachDAO;
import com.example.duanmau.model.Sach;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class DanhDauAdapter extends RecyclerView.Adapter<DanhDauAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Sach> list;

    public DanhDauAdapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DanhDauAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = ((Activity) context).getLayoutInflater();
        View view = inf.inflate(R.layout.item_recycler_danhdau, parent, false);
        return new DanhDauAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhDauAdapter.ViewHolder holder, int position) {
        Sach sach = list.get(position);
        holder.bind(sach);


        holder.txtMaSach.setText(list.get(position).getMasach()+"");
        holder.txtMaLoai.setText(list.get(position).getMaloai()+"");
        holder.txtTenLoai.setText(list.get(position).getTenloai());
        holder.txtTenSach.setText(list.get(position).getTensach());

        NumberFormat format = new DecimalFormat("#,###");
        double myNumber = list.get(position).getGiathue();
        String formattedNumber = format.format(myNumber);
        holder.txtGiaThue.setText(formattedNumber + " VND");


        // Tải hình ảnh bằng Glide
        if (sach.getUrlHinh() != null && !sach.getUrlHinh().isEmpty()) {
            Log.d("SachAdapter", "URL Hinh: " + sach.getUrlHinh());
            Glide.with(context)
                    .load(sach.getUrlHinh())
                    .error(R.drawable.ic_anhsach) // Đặt hình ảnh mặc định nếu có lỗi khi tải ảnh
                    .into(holder.ivHinhSach);
        } else {
            Log.d("SachAdapter", "URL Hinh is null or empty");
            holder.ivHinhSach.setImageResource(R.drawable.ic_anhsach); // Ảnh mặc định nếu không có URL
        }


        if (list.get(position).getTrangthai() == 0) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
        } else {
            holder.itemView.setVisibility(View.VISIBLE);
        }




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaSach, txtMaLoai, txtTenLoai, txtTenSach, txtTacGia, txtGiaThue, txtTrangThaiHinhSach;
        ImageView ivHinhSach;

        CheckBox chkDanhDau;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaSach = itemView.findViewById(R.id.txtMaSach);
            txtMaLoai = itemView.findViewById(R.id.txtMaLoai);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtTacGia = itemView.findViewById(R.id.txtTacGia);
            txtGiaThue = itemView.findViewById(R.id.txtGiaThue);
            txtTrangThaiHinhSach = itemView.findViewById(R.id.txtTrangThaiHinhSach);

            ivHinhSach = itemView.findViewById(R.id.ivHinhSach);

            chkDanhDau = itemView.findViewById(R.id.chkDanhDau);

        }

        public void bind(Sach sach) {
            txtTenSach.setText(sach.getTensach());
            txtTacGia.setText(sach.getTacgia());
            txtGiaThue.setText(String.valueOf(sach.getGiathue()));
        }
    }

    public interface sachAdapterInterface{
        public void setImageNe(ImageView ivHinh);
    }

    private void saveToDanhDau(Sach sach) {
        // Lấy matv từ SharedPreferences
        SharedPreferences preferences = context.getSharedPreferences("Thong_tin_thanh_vien", Context.MODE_PRIVATE);
        int matv = preferences.getInt("maThanhVien", -1);

        // Thêm sách vào bảng DANHDAU
        SachDAO sachDAO = new SachDAO(context);
        sachDAO.addToDanhDau(sach.getMasach(), matv);
    }

    private void removeFromDanhDau(Sach sach) {
        // Lấy matv từ SharedPreferences
        SharedPreferences preferences = context.getSharedPreferences("Thong_tin_thanh_vien", Context.MODE_PRIVATE);
        int matv = preferences.getInt("maThanhVien", -1);

        // Xóa sách khỏi bảng DANHDAU
        SachDAO sachDAO = new SachDAO(context);
        sachDAO.removeFromDanhDau(sach.getMasach(), matv);
    }

}
