package com.example.duanmau.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duanmau.R;
import com.example.duanmau.dao.LoaiSachDAO;
import com.example.duanmau.fragment.QLLoaiSachFragment;
import com.example.duanmau.model.LoaiSach;
import com.example.duanmau.model.ThanhVien;
import com.google.android.material.textfield.TextInputEditText;


import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder> {

    private Context context;
    private ArrayList<LoaiSach> list;
    LoaiSachDAO loaiSachDAO;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(LoaiSach loaiSach);
    }

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> list, OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.loaiSachDAO = new LoaiSachDAO(context);
        this.listener = listener;
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

        LoaiSach loaiSach = list.get(position);
        holder.bind(loaiSach, listener);

        holder.txtMaLoai.setText("Mã loại: LS" +list.get(position).getMaloai());
        holder.txtTenLoai.setText("Tên loại:  " +list.get(position).getTenloai());

        // Tải hình ảnh bằng Glide
        if (loaiSach.getUrlHinh() != null && !loaiSach.getUrlHinh().isEmpty()) {
            Glide.with(holder.itemView.getContext()).load(loaiSach.getUrlHinh()).into(holder.ivHinhLoaiSach);
        } else {
            holder.ivHinhLoaiSach.setImageResource(R.drawable.ic_books); // Ảnh mặc định nếu không có URL
        }

        holder.ivSuaLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDialogUpdate(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtMaLoai, txtTenLoai;
        ImageView ivHinhLoaiSach ,ivSuaLoaiSach;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaLoai = itemView.findViewById(R.id.txtMaLoai);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            ivHinhLoaiSach = itemView.findViewById(R.id.ivHinhLoaiSach);
            ivSuaLoaiSach = itemView.findViewById(R.id.ivSuaLoaiSach);

        }

        public void bind(final LoaiSach loaiSach, final OnItemClickListener listener) {
            txtTenLoai.setText(loaiSach.getTenloai());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(loaiSach);
                }
            });
        }
    }



//    public void showDialogUpdate(LoaiSach loaiSach){
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
//        View view = inflater.inflate(R.layout.dialog_sua_loaisach, null);
//        builder.setView(view);
//
//        AlertDialog alertDialog = builder.create();
//        alertDialog.setCancelable(false);
//        alertDialog.show();
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        EditText edTenLoai = view.findViewById(R.id.edTenLoai);
//        ImageView ivHinhLoaiSach = view.findViewById(R.id.ivHinhLoaiSach);
//        TextView txtTrangThaiHinhLS = view.findViewById(R.id.txtTrangThaiHinhLS);
//
//        Button btnSua = view.findViewById(R.id.btnSua);
//        Button btnHuy = view.findViewById(R.id.btnHuy);
//
//        // Đưa dữ liệu cần sửa lên edit
//        edTenLoai.setText(loaiSach.getTenloai());
//
//        // Hiển thị hình ảnh hiện tại
//        if (loaiSach.getUrlHinh() != null && !loaiSach.getUrlHinh().isEmpty()) {
//            Glide.with(context).load(loaiSach.getUrlHinh()).into(ivHinhLoaiSach);
//        } else {
//            ivHinhLoaiSach.setImageResource(R.drawable.ic_books); // Ảnh mặc định nếu không có URL
//        }
//
//        ivHinhLoaiSach.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((QLLoaiSachFragment)((Activity)context).getFragmentManager().findFragmentById(R.id.frameLayout)).accessTheGallery();
//            }
//        });
//
//        btnHuy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
//
//        btnSua.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int maloai = loaiSach.getMaloai();
//                String tenloai = edTenLoai.getText().toString();
//                String urlHinh = ((QLLoaiSachFragment)((Activity)context).getFragmentManager().findFragmentById(R.id.frameLayout)).getLinkHinh();
//
//                if (tenloai.length() == 0){
//                    Toast.makeText(context, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                } else {
//                    LoaiSach loaisachChinhSua = new LoaiSach(maloai, tenloai, urlHinh);
//                    boolean check = loaiSachDAO.chinhSua(loaisachChinhSua);
//                    if (check){
//                        Toast.makeText(context, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
//                        loadData();
//                        alertDialog.dismiss();
//                    } else {
//                        Toast.makeText(context, "Chỉnh sửa thất bại", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//    }

    public void loadData(){
        list.clear();
        list.addAll(loaiSachDAO.getDSLoaiSach());
        notifyDataSetChanged();
    }
}
