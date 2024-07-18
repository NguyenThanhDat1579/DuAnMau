package com.example.duanmau.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.dao.PhieuMuonDAO;
import com.example.duanmau.model.PhieuMuon;

import java.util.ArrayList;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder>{
    ArrayList<PhieuMuon> listPM = new ArrayList<>();
    Context context;

    public PhieuMuonAdapter(ArrayList<PhieuMuon> listPM, Context context) {
        this.listPM = listPM;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.itemrcv_phieumuon, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaPhieu.setText("PM"+listPM.get(position).getMapm());
        holder.txtTenThuThu.setText(listPM.get(position).getTentt());
        holder.txtTenKH.setText(listPM.get(position).getTentv());
        holder.txtTenSach.setText(listPM.get(position).getTensach());
        holder.txtNgayThue.setText(listPM.get(position).getNgaymuon());
        holder.txtNgayTra.setText(listPM.get(position).getNgaytra());
        String trangthai = "";
        if(listPM.get(position).getTrangthai() == 1){
            trangthai = "Đã trả sách";
            int color = ContextCompat.getColor(context, R.color.GREEN);
            holder.txtTrangThai.setTextColor(color);
            holder.btnXacNhan.setVisibility(View.INVISIBLE);
        }else{
            trangthai = "Chưa trả sách";
            int color = ContextCompat.getColor(context, R.color.RED);
            holder.txtTrangThai.setTextColor(color);
            holder.btnXacNhan.setVisibility(View.VISIBLE);
        }

        holder.txtTrangThai.setText(trangthai);
        holder.txtTienThue.setText(listPM.get(position).getTienthue()+" VND");;


        holder.btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                boolean kiemtra = phieuMuonDAO.thayDoiTrangThai(listPM.get(holder.getAdapterPosition()).getMapm());
                if(kiemtra){
                    listPM.clear();
                    listPM = phieuMuonDAO.getAllPhieuMuon();
                    notifyDataSetChanged();
                }else{
                    Toast.makeText(context, "Thay đổi trạng thái không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPM.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaPhieu, txtTenThuThu, txtTenKH, txtTenSach, txtNgayThue, txtNgayTra, txtTienThue, txtTrangThai;
        Button btnXacNhan;
        ImageView ivSuaPhieuMuon, ivXoaPhieuMuon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaPhieu = itemView.findViewById(R.id.txtMaPhieu);
            txtTenThuThu = itemView.findViewById(R.id.txtTenThuThu);
            txtTenKH = itemView.findViewById(R.id.txtTenKH);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtNgayThue = itemView.findViewById(R.id.txtNgayThue);
            txtNgayTra = itemView.findViewById(R.id.txtNgayTra);
            txtTienThue = itemView.findViewById(R.id.txtTienThue);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            btnXacNhan = itemView.findViewById(R.id.btnXacNhan);
            ivSuaPhieuMuon = itemView.findViewById(R.id.ivSuaPhieuMuon);
            ivXoaPhieuMuon = itemView.findViewById(R.id.ivXoaPhieuMuon);
        }
    }
}
