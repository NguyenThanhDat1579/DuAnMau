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
import com.example.duanmau.dao.ThanhVienDAO;
import com.example.duanmau.model.ThanhVien;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ThanhVien> list;
    private ThanhVienDAO thanhVienDAO;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list) {
        this.context = context;
        this.list = list;
        this.thanhVienDAO = new ThanhVienDAO(context);
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

        holder.ivSuaThanhVien.setOnClickListener(new View.OnClickListener() {
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

        TextView txtMaTV, txtTenTV, txtEmail, txtTendangnhap, txtMatkhau;
        ImageView ivSuaThanhVien;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaTV = itemView.findViewById(R.id.txtMaTV);
            txtTenTV = itemView.findViewById(R.id.txtTenTV);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtTendangnhap = itemView.findViewById(R.id.txtTenDangNhap);
            txtMatkhau = itemView.findViewById(R.id.txtMatkhau);
            ivSuaThanhVien = itemView.findViewById(R.id.ivSuaThanhVien);



        }
    }

    public void showDialogUpdate(ThanhVien thanhVien){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sua_thanhvien, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextInputEditText edTenDangKy = view.findViewById(R.id.edTenDangKy);
        TextInputEditText edHoten = view.findViewById(R.id.edHoTen);
        TextInputEditText edDangKyEmail = view.findViewById(R.id.edDangKyEmail);
        TextInputEditText edPasswordDangKy = view.findViewById(R.id.edPasswordDangKy);
        TextInputEditText edRePasswordDangKy = view.findViewById(R.id.edRePasswordDangKy);


        Button btnSua = view.findViewById(R.id.btnSua);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        //dua du lieu can sua len edit
        edTenDangKy.setText(thanhVien.getTendangnhap());
        edHoten.setText(thanhVien.getTentv());
        edDangKyEmail.setText(thanhVien.getEmail());
        edPasswordDangKy.setText(thanhVien.getMatkhau());
        edRePasswordDangKy.setText(thanhVien.getMatkhau());


        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int matv = thanhVien.getMatv();
                String hoten = edHoten.getText().toString();
                String email = edDangKyEmail.getText().toString();
                String tentv = edTenDangKy.getText().toString();
                String pass = edPasswordDangKy.getText().toString();



                if (hoten.length() == 0 || email.length() == 0 || tentv.length() == 0 || pass.length() == 0){
                    Toast.makeText(context, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    ThanhVien thanhvienChinhSua = new ThanhVien(matv, hoten, email, tentv, pass);
                    boolean check = thanhVienDAO.chinhSua(thanhvienChinhSua);
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
        list = thanhVienDAO.getDSThanhVien();
        notifyDataSetChanged();
    }
}
