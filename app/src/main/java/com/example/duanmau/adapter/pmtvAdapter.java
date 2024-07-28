package com.example.duanmau.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.model.PhieuMuon;
import com.example.duanmau.model.Sach;

import java.util.ArrayList;

public class pmtvAdapter extends RecyclerView.Adapter<pmtvAdapter.ViewHolder>{

    private Context context;
    private ArrayList<PhieuMuon> list;

    public pmtvAdapter(Context context, ArrayList<PhieuMuon> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_phieumuontv, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txttentt.setText(list.get(position).getTentt());
        holder.txttentv.setText(list.get(position).getTentv());
        holder.txttens.setText(list.get(position).getTensach());
        holder.txtngaymuon.setText(list.get(position).getNgaymuon());
        holder.txtngaytra.setText(list.get(position).getNgaytra());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView  txttentt, txttentv, txttens, txtngaymuon, txtngaytra, txtspm;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txttentt = itemView.findViewById(R.id.txtTenThuThu);
            txttentv = itemView.findViewById(R.id.txtTenKH);
            txttens = itemView.findViewById(R.id.txtTenSach);
            txtngaymuon = itemView.findViewById(R.id.txtNgayThue);
            txtngaytra = itemView.findViewById(R.id.txtNgayTra);

        }
    }
}


