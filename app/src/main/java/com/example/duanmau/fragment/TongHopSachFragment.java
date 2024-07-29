package com.example.duanmau.fragment;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
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
import com.example.duanmau.adapter.LoaiSachAdapter;
import com.example.duanmau.adapter.SachAdapter;
import com.example.duanmau.adapter.ThanhVienAdapter;
import com.example.duanmau.dao.LoaiSachDAO;
import com.example.duanmau.dao.SachDAO;
import com.example.duanmau.dao.ThanhVienDAO;
import com.example.duanmau.model.LoaiSach;
import com.example.duanmau.model.PhieuMuon;
import com.example.duanmau.model.Sach;
import com.example.duanmau.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TongHopSachFragment extends Fragment {

    RecyclerView recyclerTongHopSach;
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
    private Bitmap imageLib;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tonghopsach, container, false);

        //anh xa
        recyclerTongHopSach = view.findViewById(R.id.recyclerTongHopSach);
        floatAddThemSach = view.findViewById(R.id.floatAddThemSach);
        searchBook = view.findViewById(R.id.searchBook);
        configCloudinary();
        sachDAO = new SachDAO(getContext());
        loadData(null);


        floatAddThemSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });

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
        recyclerTongHopSach.setLayoutManager(linearLayoutManager);
        SachAdapter adapter = new SachAdapter(getContext(), sachList, getDSLoaiSach(), sachDAO, new SachAdapter.sachAdapterInterface() {
            @Override
            public Bitmap setImageNe() {
                accessTheGallery();
                return imageLib;
            }
        });
        recyclerTongHopSach.setAdapter(adapter);
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

    private void showDialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_sach, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        // Ánh xạ các thành phần trong dialog
        TextInputEditText edTenSach = view.findViewById(R.id.edTenSach);
        TextInputEditText edTacGia = view.findViewById(R.id.edTacGiaInput);
        TextInputEditText edGiaThue = view.findViewById(R.id.edGiaThueInput);
        txtTrangThaiHinhSach = view.findViewById(R.id.txtTrangThaiHinhSach);

        Spinner spnLoaiSach = view.findViewById(R.id.spnLoaiSach);
        Button btnThem = view.findViewById(R.id.btnThem);
        Button btnHuy = view.findViewById(R.id.btnHuy);
        ivHinhSach = view.findViewById(R.id.ivHinhSach);

        ivHinhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessTheGallery();
            }
        });

        // Lấy dữ liệu loại sách và thiết lập cho spinner
        getDataLoaiSach(spnLoaiSach);

        // Xử lý sự kiện nút hủy
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        // Xử lý sự kiện nút thêm
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSach = edTenSach.getText().toString().trim();
                String tacGia = edTacGia.getText().toString().trim();
                String giaThueStr = edGiaThue.getText().toString().trim();
                int viTriLoaiSach = spnLoaiSach.getSelectedItemPosition();

                // Kiểm tra thông tin đầu vào
                if (tenSach.isEmpty() || tacGia.isEmpty() || giaThueStr.isEmpty() || viTriLoaiSach < 0 || urlHinh.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                    if (tenSach.isEmpty()) {
                        edTenSach.setError("Vui lòng nhập tên sách");
                    } else {
                        edTenSach.setError(null);
                    }
                    if (tacGia.isEmpty()) {
                        edTacGia.setError("Vui lòng nhập tác giả");
                    } else {
                        edTacGia.setError(null);
                    }
                    if (giaThueStr.isEmpty()) {
                        edGiaThue.setError("Vui lòng nhập giá thuê");
                    } else {
                        edGiaThue.setError(null);
                    }
                    if (urlHinh.isEmpty()) {
                        txtTrangThaiHinhSach.setText("Vui lòng chọn và upload ảnh");
                    }

                } else {
                    int giaThue = Integer.parseInt(giaThueStr);
                    LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
                    int maloai = loaiSachDAO.getDSLoaiSach().get(viTriLoaiSach).getMaloai();

                    Sach sach = new Sach();
                    sach.setTensach(tenSach);
                    sach.setTacgia(tacGia);
                    sach.setGiathue(giaThue);
                    sach.setMaloai(maloai);
                    sach.setUrlHinh(urlHinh);

                    SachDAO sachDAO = new SachDAO(getContext());
                    sachDAO.themSach(sach);



                    boolean check = sachDAO.themSach(sach);
                    if (check) {
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();

                        // Cập nhật lại danh sách sách
                        loadData(null);
                        alertDialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

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
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        myLauncher.launch(i);
    }

    private ActivityResultLauncher<Intent> myLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            //get the image's file location
            filePath = getRealPathFromUri(result.getData().getData(), getActivity());

            if (result.getResultCode() == RESULT_OK) {
                try {
                    //set picked image to the mProfile
                    imageLib = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), result.getData().getData());
                  //  ivHinhSach.setImageBitmap(bitmap);

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
                txtTrangThaiHinhSach.setText("Bắt đầu upload");
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                txtTrangThaiHinhSach.setText("Đang upload... ");
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                txtTrangThaiHinhSach.setText("Thành công: " + resultData.get("url").toString());
                urlHinh = resultData.get("url").toString();

            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                txtTrangThaiHinhSach.setText("Lỗi " + error.getDescription());
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                txtTrangThaiHinhSach.setText("Reshedule " + error.getDescription());
            }
        }).dispatch();
    }
    public String getLinkHinh() {
        return urlHinh;
    }

}


