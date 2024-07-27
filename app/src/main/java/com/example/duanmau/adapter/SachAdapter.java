package com.example.duanmau.adapter;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.dao.LoaiSachDAO;
import com.example.duanmau.dao.SachDAO;
import com.example.duanmau.fragment.TongHopSachFragment;
import com.example.duanmau.model.LoaiSach;
import com.example.duanmau.model.Sach;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Sach> list;
    private ArrayList<HashMap<String,Object>> listHM;
    private  SachDAO sachDAO;

    public SachAdapter(Context context, ArrayList<Sach> list,ArrayList<HashMap<String,Object>> listHM,SachDAO sachDAO) {
        this.context = context;
        this.list = list;
        this.listHM=listHM;
        this.sachDAO=sachDAO;
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
        holder.txtGiaThue.setText(list.get(position).getGiathue()+"");



        if (list.get(position).getTrangthai() == 0) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
        } else {
            holder.itemView.setVisibility(View.VISIBLE);
        }




        holder.ivXoaSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        holder.btn_chinhsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDailog(list.get(holder.getAdapterPosition()));
                loadData();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtMaSach, txtMaLoai, txtTenLoai, txtTenSach, txtTacGia, txtGiaThue;
        ImageView ivXoaSach;
        Button btn_chinhsua;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaSach = itemView.findViewById(R.id.txtMaSach);
            txtMaLoai = itemView.findViewById(R.id.txtMaLoai);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtTacGia = itemView.findViewById(R.id.txtTacGia);
            txtGiaThue = itemView.findViewById(R.id.txtGiaThue);
            ivXoaSach = itemView.findViewById(R.id.ivXoaSach);
            btn_chinhsua=itemView.findViewById(R.id.chinhsua);
        }

        public void bind(Sach sach) {
            txtTenSach.setText(sach.getTensach());
            txtTacGia.setText(sach.getTacgia());
            txtGiaThue.setText(String.valueOf(sach.getGiathue()));
        }


    }


    private void showDailog(Sach sach){
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
        Button btnsua = view.findViewById(R.id.btnsua);
        Button btnhuy = view.findViewById(R.id.btnHuy);

        Spinner spnloaisach =  view.findViewById(R.id.spnLoaiSach);

        tensach.setText(sach.getTensach());
        tacgia.setText(sach.getTacgia());
        giathue.setText(String.valueOf(sach.getGiathue()));

        SimpleAdapter simpleAdapter =  new SimpleAdapter(
                context,
                listHM,
                android.R.layout.simple_expandable_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1}
        );
        spnloaisach.setAdapter(simpleAdapter);

        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = tensach.getText().toString();
                String tacGia = tacgia.getText().toString();
                int tien=Integer.parseInt(giathue.getText().toString());
                HashMap<String,Object> hs = (HashMap<String, Object>) spnloaisach.getSelectedItem();
                int maloai=(int) hs.get("maloai");
                boolean check =sachDAO.capNhatThongTinSach(sach.getMasach(),tenSach,tacGia,tien,maloai);
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

//        int index = 0;
//        int postion = -1;//vị trí tên loại nằm trongds
//        for(HashMap<String,Object> itemm : listHM){
////            int maloaiFromItemm = Integer.parseInt((String) itemm.get("maloai"));
//            if( (int) itemm.get("maloai") == sach.getMaloai()){
//                    postion = index;
//            }
//            index++;
//        }
//        spnloaisach.setSelection(postion);

//        builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String tenSach = tensach.getText().toString();
//                String tacGia = tacgia.getText().toString();
//                int tien=Integer.parseInt(giathue.getText().toString());
//                HashMap<String,Object> hs = (HashMap<String, Object>) spnloaisach.getSelectedItem();
//                int maloai=(int) hs.get("maloai");
//                boolean check =sachDAO.capNhatThongTinSach(sach.getMasach(),tenSach,tacGia,tien,maloai);
//                if(check){
//                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
//                    loadData();
//                }else{
//                    Toast.makeText(context, "Cập nhât thất bại", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//        builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();

    }

    private void loadData(){
        list.clear();
        list = sachDAO.getAllSach();
        notifyDataSetChanged();
    }
}
