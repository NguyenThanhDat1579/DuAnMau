package com.example.duanmau.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.duanmau.R;
import com.example.duanmau.adapter.LoaiSachAdapter;
import com.example.duanmau.adapter.LoaiSachTVAdapter;
import com.example.duanmau.dao.LoaiSachDAO;
import com.example.duanmau.model.LoaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QLLoaiSachTVFragment extends Fragment {

    private RecyclerView recyclerLoaiSachTV;
    private LoaiSachDAO loaiSachDAO;
    private ImageView ivHinhLoaiSach;
    private TextView txtTrangThaiHinhLS;
    private String filePath = "";
    private String urlHinh = "";
    private LoaiSachAdapter loaiSachAdapter;
    private ArrayList<LoaiSach> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qltheloaisach_tv, container, false);

        // ánh xạ
        recyclerLoaiSachTV = view.findViewById(R.id.recyclerLoaiSachTV);



        configCloudinary();

        loaiSachDAO = new LoaiSachDAO(getContext());
        loadData();



        return view;

    }


    private void loadData() {
        ArrayList<LoaiSach> list = loaiSachDAO.getDSLoaiSach();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerLoaiSachTV.setLayoutManager(linearLayoutManager);

        LoaiSachTVAdapter adapter = new LoaiSachTVAdapter(getContext(), list, new LoaiSachTVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LoaiSach loaiSach) {
                // Chuyển sang SachFragment và truyền ID của loại sách
                QLSachTVFragment sachTVFragment = new QLSachTVFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("maloai", loaiSach.getMaloai());
                bundle.putString("tenloai", loaiSach.getTenloai());
                bundle.putString("urlHinh", loaiSach.getUrlHinh());
                sachTVFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, sachTVFragment)
                        .addToBackStack(null)
                        .commit();
            }
        }, new LoaiSachTVAdapter.loaisachAdapterInterface() {
            @Override
            public void setImage(ImageView ivHinh) {
                ivHinhLoaiSach = ivHinh;
                accessTheGallery();
            }
        });
        recyclerLoaiSachTV.setAdapter(adapter);
    }








    private void showDialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_loaisach, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        //xu ly chuc nang
//        EditText edMaLoai = view.findViewById(R.id.edMaLoai);
        EditText edTenLoai = view.findViewById(R.id.edTenLoai);
        Button btnThem = view.findViewById(R.id.btnThem);
        Button btnHuy = view.findViewById(R.id.btnHuy);
        ivHinhLoaiSach = view.findViewById(R.id.ivHinhLoaiSach);
        txtTrangThaiHinhLS = view.findViewById(R.id.txtTrangThaiHinhLS);

        ivHinhLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessTheGallery();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String maloai = edMaLoai.getText().toString()+"";
                String tenloai = edTenLoai.getText().toString();


                // validate - bắt lỗi

                edTenLoai.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0) {
                            edTenLoai.setError("Vui lòng nhập tên loại sách");
                        } else {
                            edTenLoai.setError(null);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) { }
                });

                if (tenloai.isEmpty() || urlHinh.isEmpty()) {
                    Toast.makeText(getContext(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    if (tenloai.isEmpty()) {
                        edTenLoai.setError("Vui lòng nhập tên loại sách");
                    } else {
                        edTenLoai.setError(null);
                    }
                    if (urlHinh.isEmpty()) {
                        txtTrangThaiHinhLS.setText("Vui lòng chọn và upload ảnh");
                    }
                } else {
                    boolean check = loaiSachDAO.themLoaiSach(tenloai, urlHinh);
                    if (check) {
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        Log.d("LinkHinh", "linkHinh: " + urlHinh);
                        loadData();
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    public void accessTheGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        myLauncher.launch(i);
    }

    private ActivityResultLauncher<Intent> myLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if (result.getResultCode() == RESULT_OK) {
                try {
                    //set picked image to the mProfile
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), result.getData().getData());
                    ivHinhLoaiSach.setImageBitmap(bitmap);

                    //get the image's file location
                    filePath = getRealPathFromUri(result.getData().getData(), getActivity());
                    uploadToCloudinary(filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    private String getRealPathFromUri(Uri imageUri, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(imageUri, null, null, null, null);

        if (cursor == null) {
            return imageUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    HashMap<String, String> config = new HashMap<>();

    public void configCloudinary() {
        // Kiểm tra nếu MediaManager chưa được khởi tạo
        try {
            config.put("cloud_name", "ddkqz5udn");
            config.put("api_key", "895116334859589");
            config.put("api_secret", "rJ2xEtitU3MEc_2Xz4rcVo-q97c");
            MediaManager.init(getActivity(), config);
        } catch (IllegalStateException e) {
            // MediaManager đã được khởi tạo, không cần làm gì
        }
    }


    private void uploadToCloudinary(String filePath) {
        Log.d("A", "sign up uploadToCloudinary- ");
        MediaManager.get().upload(filePath).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
//                txtTrangThaiHinhLS.setText("Bắt đầu upload");
                Toast.makeText(getContext(), "Bắt đầu upload", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
//                txtTrangThaiHinhLS.setText("Đang upload... ");
                Toast.makeText(getContext(), "Đang upload... ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
//                txtTrangThaiHinhLS.setText("Thành công: " + resultData.get("url").toString());
                urlHinh = resultData.get("url").toString();
                Toast.makeText(getContext(), "Upload thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
//                txtTrangThaiHinhLS.setText("Lỗi " + error.getDescription());
                Toast.makeText(getContext(), "Lỗi " + error.getDescription(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
//                txtTrangThaiHinhLS.setText("Reshedule " + error.getDescription());
                Toast.makeText(getContext(), "Reshedule " + error.getDescription(), Toast.LENGTH_SHORT).show();
            }
        }).dispatch();
    }
    public String getLinkHinh() {
        return urlHinh;
    }

}

