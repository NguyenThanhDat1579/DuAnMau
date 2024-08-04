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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.duanmau.R;
import com.example.duanmau.adapter.SachAdapter;
import com.example.duanmau.adapter.SachTVAdapter;
import com.example.duanmau.dao.LoaiSachDAO;
import com.example.duanmau.dao.SachDAO;
import com.example.duanmau.model.LoaiSach;
import com.example.duanmau.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TongHopSachTVFragment extends Fragment {

    RecyclerView recyclerTongHopSachTV;
    FloatingActionButton floatAddThemSach;
    SearchView searchBook;
    SachDAO sachDAO;
    LoaiSachDAO loaiSachDAO;
    ArrayList<Sach> sachList = new ArrayList<>();
    int maloai;

    TextView txtTrangThaiHinhSach;
    ImageView ivHinhSach;
    private String filePath = "";
    private String urlHinh = "";
    private SachAdapter sachAdapter;
    private ArrayList<Sach> list;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tonghopsach_tv, container, false);

        //anh xa
        recyclerTongHopSachTV = view.findViewById(R.id.recyclerTongHopSachTV);

        searchBook = view.findViewById(R.id.searchBook);
        configCloudinary();
        sachDAO = new SachDAO(getContext());
        loadData(null);




        searchBook.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadData(newText);
                return false;
            }
        });

        return view;

    }

    private void loadData(String text) {
        ArrayList<Sach> list = sachDAO.getAllSach();
        sachList.clear();  // Xóa danh sách trước khi thêm sách mới vào

        if (text != null && !text.isEmpty()) {
            for (Sach sach : list) {
                if (sach.getTensach().toLowerCase().startsWith(text.toLowerCase())) {
                    sachList.add(sach);
                }
            }
        } else {
            sachList.addAll(list);  // Nếu không có tìm kiếm, hiển thị toàn bộ sách
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerTongHopSachTV.setLayoutManager(linearLayoutManager);
        SachTVAdapter adapter = new SachTVAdapter(getContext(), sachList, getDSLoaiSach(), sachDAO, new SachTVAdapter.sachAdapterInterface() {
            @Override
            public void setImageNe(ImageView ivHinh) {
                ivHinhSach = ivHinh;
                accessTheGallery();

            }
        });
        recyclerTongHopSachTV.setAdapter(adapter);
    }

    private void getDataLoaiSach(Spinner spnLoaiSach) {
        // lấy dữ liệu
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiSach> listLS = loaiSachDAO.getDSLoaiSach();

        ArrayList<String> tenLoaiSachList = new ArrayList<>();
        for (LoaiSach loaiSach : listLS) {
            tenLoaiSachList.add(loaiSach.getTenloai());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                tenLoaiSachList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLoaiSach.setAdapter(adapter);

    }


    private ArrayList<HashMap<String, Object>> getDSLoaiSach(){
        LoaiSachDAO loaiSachDAO= new LoaiSachDAO(getContext());
        ArrayList<LoaiSach> list = loaiSachDAO.getDSLoaiSach();
        ArrayList<HashMap<String,Object>> listHM=new ArrayList<>();

        for(LoaiSach loai : list){
            HashMap<String,Object> hs = new HashMap<>();
            hs.put("maloai",loai.getMaloai());
            hs.put("tenloai",loai.getTenloai());
            listHM.add(hs);
        }
        return listHM;
    }

    public void accessTheGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        myLauncher.launch(i);
    }

    private ActivityResultLauncher<Intent> myLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            //get the image's file location
            if (result.getResultCode() == RESULT_OK) {
                try {
                    //set picked image to the mProfile
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), result.getData().getData());
                    ivHinhSach.setImageBitmap(bitmap);

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
//                txtTrangThaiHinhSach.setText("Bắt đầu upload");
                Toast.makeText(getContext(), "Bắt đầu upload", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
//                txtTrangThaiHinhSach.setText("Đang upload... ");
                Toast.makeText(getContext(), "Đang upload... ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
//                txtTrangThaiHinhSach.setText("Thành công: " + resultData.get("url").toString());
                urlHinh = resultData.get("url").toString();
                Toast.makeText(getContext(), "Upload thành công", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
//                txtTrangThaiHinhSach.setText("Lỗi " + error.getDescription());
                Toast.makeText(getContext(), "Lỗi " + error.getDescription(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
//                txtTrangThaiHinhSach.setText("Reshedule " + error.getDescription());
                Toast.makeText(getContext(), "Reshedule " + error.getDescription(), Toast.LENGTH_SHORT).show();
            }
        }).dispatch();
    }
    public String getLinkHinh() {
        return urlHinh;
    }

}