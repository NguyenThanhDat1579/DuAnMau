package com.example.duanmau.adapter;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.duanmau.dao.LoaiSachDAO;
import com.example.duanmau.dao.PhieuMuonDAO;
import com.example.duanmau.dao.SachDAO;
import com.example.duanmau.fragment.QLLoaiSachFragment;
import com.example.duanmau.fragment.TongHopSachFragment;
import com.example.duanmau.model.LoaiSach;
import com.example.duanmau.model.Sach;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Sach> list;
    private ArrayList<HashMap<String,Object>> listHM;
    private  SachDAO sachDAO;
    private sachAdapterInterface sachAdapterInterface;

    public SachAdapter(Context context, ArrayList<Sach> list,ArrayList<HashMap<String,Object>> listHM,SachDAO sachDAO, sachAdapterInterface sachAdapterInterface) {
        this.context = context;
        this.list = list;
        this.listHM=listHM;
        this.sachDAO=sachDAO;
        this.sachAdapterInterface = sachAdapterInterface;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = ((Activity)context).getLayoutInflater();
        View view = inf.inflate(R.layout.item_recycler_sach, parent, false);
        return new SachAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

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




        holder.ivXoaSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");

                //Xác nhận xóa sách "Nguyễn Thành Đạt"
                builder.setMessage("Xác nhận xóa sách \"" + holder.txtTenSach.getText() + "\"");
                builder.setIcon(R.drawable.icon_warning);

                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SachDAO  sachDAO = new SachDAO(context);
                        boolean kiemtra = sachDAO.thayDoiTrangThaiSach(list.get(holder.getAdapterPosition()).getMasach());
                        if(kiemtra){
                            list.clear();
                            list = sachDAO.getAllSach();
                            notifyDataSetChanged();
                        }else{
                            Toast.makeText(context, "Xóa sách thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Hủy", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
            }

        });

        holder.btn_chinhsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSuaSach(list.get(holder.getAdapterPosition()));
                loadData();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtMaSach, txtMaLoai, txtTenLoai, txtTenSach, txtTacGia, txtGiaThue, txtTrangThaiHinhSach;
        ImageView ivXoaSach, ivHinhSach;
        Button btn_chinhsua;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaSach = itemView.findViewById(R.id.txtMaSach);
            txtMaLoai = itemView.findViewById(R.id.txtMaLoai);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtTacGia = itemView.findViewById(R.id.txtTacGia);
            txtGiaThue = itemView.findViewById(R.id.txtGiaThue);
            txtTrangThaiHinhSach = itemView.findViewById(R.id.txtTrangThaiHinhSach);
            ivXoaSach = itemView.findViewById(R.id.ivXoaSach);
            ivHinhSach = itemView.findViewById(R.id.ivHinhSach);
            btn_chinhsua=itemView.findViewById(R.id.chinhsua);
        }

        public void bind(Sach sach) {
            txtTenSach.setText(sach.getTensach());
            txtTacGia.setText(sach.getTacgia());
            txtGiaThue.setText(String.valueOf(sach.getGiathue()));
        }


    }


    private void showDialogSuaSach(Sach sach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_sach,null);
        builder.setView(view);



        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




        TextInputEditText tensach = view.findViewById(R.id.edtTenSach);
        TextInputEditText tacgia = view.findViewById(R.id.edtTacGiaInput);
        TextInputEditText giathue = view.findViewById(R.id.edtGiaThueInput);
        ImageView ivHinhSach = view.findViewById(R.id.ivHinhSach);
        Button btnsua = view.findViewById(R.id.btnsua);
        Button btnhuy = view.findViewById(R.id.btnHuy);

        Spinner spnloaisach =  view.findViewById(R.id.spnLoaiSach);

        tensach.setText(sach.getTensach());
        tacgia.setText(sach.getTacgia());
        giathue.setText(String.valueOf(sach.getGiathue()));






        SimpleAdapter simpleAdapter =  new SimpleAdapter(
                context,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1}
        );
        spnloaisach.setAdapter(simpleAdapter);


        // Hiển thị hình ảnh hiện tại
        if (sach.getUrlHinh() != null && !sach.getUrlHinh().isEmpty()) {
            Log.d("SachAdapter", "URL Hinh: " + sach.getUrlHinh());
            Glide.with(context)
                    .load(sach.getUrlHinh())
                    .error(R.drawable.ic_anhsach) // Đặt hình ảnh mặc định nếu có lỗi khi tải ảnh
                    .into(ivHinhSach);
        } else {
            Log.d("SachAdapter", "URL Hinh is null or empty");
            ivHinhSach.setImageResource(R.drawable.ic_anhsach); // Ảnh mặc định nếu không có URL
        }


        // Chọn ảnh từ thư viện
        ivHinhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sachAdapterInterface.setImageNe(ivHinhSach);
            }

        });


        int index = 0;
        int postion = -1;//vị trí tên loại nằm trongds
        for(HashMap<String,Object> itemm : listHM){
//            int maloaiFromItemm = Integer.parseInt((String) itemm.get("maloai"));
            if( (int) itemm.get("maloai") == sach.getMaloai()){
                postion = index;
            }
            index++;
        }
        spnloaisach.setSelection(postion);

        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = tensach.getText().toString();
                String tacGia = tacgia.getText().toString();
                int tien=Integer.parseInt(giathue.getText().toString());

                String urlHinh = ((TongHopSachFragment) ((FragmentActivity) context).getSupportFragmentManager().findFragmentById(R.id.frameLayout)).getLinkHinh();
                if (urlHinh == null || urlHinh.isEmpty()) {
                    urlHinh = sach.getUrlHinh(); // Lấy URL hình ảnh hiện tại của sách
                }


                HashMap<String,Object> hs = (HashMap<String, Object>) spnloaisach.getSelectedItem();
                int maloai=(int) hs.get("maloai");
                boolean check =sachDAO.capNhatThongTinSach(sach.getMasach(),tenSach,tacGia,tien,maloai, urlHinh);
                if(check){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    alertDialog.dismiss();
                }else{
                    Toast.makeText(context, "Cập nhât thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    private void loadData(){
        list.clear();
        list = sachDAO.getAllSach();
        notifyDataSetChanged();
    }

    public interface sachAdapterInterface{
        public void setImageNe(ImageView ivHinh);
    }
}
