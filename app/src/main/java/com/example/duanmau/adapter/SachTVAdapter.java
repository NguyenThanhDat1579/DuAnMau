package com.example.duanmau.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duanmau.R;
import com.example.duanmau.dao.PhieuMuonDAO;
import com.example.duanmau.dao.SachDAO;
import com.example.duanmau.fragment.TongHopSachFragment;
import com.example.duanmau.model.Sach;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class SachTVAdapter extends RecyclerView.Adapter<SachTVAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Sach> list;
    private ArrayList<HashMap<String,Object>> listHM;
    private  SachDAO sachDAO;
    private sachAdapterInterface sachAdapterInterface;
    private int matv;

    public SachTVAdapter(Context context, ArrayList<Sach> list, ArrayList<HashMap<String,Object>> listHM, SachDAO sachDAO, sachAdapterInterface sachAdapterInterface) {
        this.context = context;
        this.list = list;
        this.listHM=listHM;
        this.sachDAO=sachDAO;
        this.sachAdapterInterface = sachAdapterInterface;
    }

    public SachTVAdapter(Context context, ArrayList<Sach> list, SachDAO sachDAO) {
        this.context = context;
        this.list = list;
        this.sachDAO = sachDAO;

        SharedPreferences preferences = context.getSharedPreferences("Thong_tin_thanh_vien", Context.MODE_PRIVATE);
        this.matv = preferences.getInt("maThanhVien", -1); // Lấy mã thành viên từ SharedPreferences
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = ((Activity)context).getLayoutInflater();
        View view = inf.inflate(R.layout.item_recycler_sach_tv, parent, false);
        return new SachTVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachTVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Sach sach = list.get(position);
        holder.bind(sach);


        holder.txtMaSach.setText(list.get(position).getMasach()+"");
        holder.txtMaLoai.setText(list.get(position).getMaloai()+"");
        holder.txtTenLoai.setText(list.get(position).getTenloai());
        holder.txtTenSach.setText(list.get(position).getTensach());
        holder.chkDanhDau.setChecked(sach.isChecked());

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

        SharedPreferences preferences = context.getSharedPreferences("Thong_tin_thanh_vien", Context.MODE_PRIVATE);
        matv = preferences.getInt("maThanhVien", -1); // Lấy mã thành viên từ SharedPreferences
        boolean isBookMarked = sachDAO.isBookMarked(sach.getMasach(), matv);
        holder.chkDanhDau.setChecked(isBookMarked);

        holder.chkDanhDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.chkDanhDau.isChecked()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Thông báo");

                    //Xác nhận "Nguyễn Thành Đạt" đã trả sách
                    builder.setMessage("Xác nhận thêm \"" + list.get(position).getTensach() + "\" vào danh sách đánh dấu?");
                    builder.setIcon(R.drawable.icon_warning);

                    builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sachDAO.addToDanhDau(sach.getMasach(), matv);
                        }
                    });

                    builder.setNegativeButton("Hủy", null);

                    AlertDialog alertDialog = builder.create();
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Thông báo");

                    //Xác nhận "Nguyễn Thành Đạt" đã trả sách
                    builder.setMessage("Xác nhận xóa \"" + list.get(position).getTensach() + "\" khỏi danh sách đánh dấu?");
                    builder.setIcon(R.drawable.icon_warning);

                    builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sachDAO.removeFromDanhDau(sach.getMasach(), matv);
                        }
                    });

                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            holder.chkDanhDau.setChecked(true);
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtMaSach, txtMaLoai, txtTenLoai, txtTenSach, txtTacGia, txtGiaThue, txtTrangThaiHinhSach;
        ImageView  ivHinhSach;

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


    private void loadData(){
        list.clear();
        list = sachDAO.getAllSach();
        notifyDataSetChanged();
    }

    public interface sachAdapterInterface{
        public void setImageNe(ImageView ivHinh);
    }

    private void saveToDanhDau(Sach sach) {
        SharedPreferences preferences = context.getSharedPreferences("Thong_tin_thanh_vien", Context.MODE_PRIVATE);
        int matv = preferences.getInt("maThanhVien", -1);
        sachDAO.addToDanhDau(sach.getMasach(), matv);
    }

    private void removeFromDanhDau(Sach sach) {
        SharedPreferences preferences = context.getSharedPreferences("Thong_tin_thanh_vien", Context.MODE_PRIVATE);
        int matv = preferences.getInt("maThanhVien", -1);
        sachDAO.removeFromDanhDau(sach.getMasach(), matv);
    }
}