package com.example.duanmau;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.duanmau.fragment.HomeFragment;
import com.example.duanmau.fragment.QLPhieuMuonFragment;
import com.example.duanmau.fragment.QLThanhVienFragment;
import com.example.duanmau.fragment.QLLoaiSachFragment;
import com.example.duanmau.fragment.TongHopSachFragment;
import com.example.duanmau.fragment.Top10SachMuonFragment;
import com.example.duanmau.fragment.ThongKeFragment;
import com.google.android.material.navigation.NavigationView;


public class AdminActivity extends AppCompatActivity {
    Toolbar toolbar;
    FrameLayout framelayout;
    NavigationView navigationView;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // ánh xạ
        toolbar = findViewById(R.id.toolbar);
        framelayout = findViewById(R.id.frameLayout);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawer_layout);

        //set toolbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setTitle("");


        //set fragment mặc định
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;


                if (menuItem.getItemId() == R.id.item_Home) {
                    fragment = new HomeFragment();
                }else if (menuItem.getItemId() == R.id.item_QLTheLoaiSach) {
                    fragment = new QLLoaiSachFragment();
                }else if (menuItem.getItemId() == R.id.item_TongHopSach){
                    fragment = new TongHopSachFragment();
                } else if (menuItem.getItemId() == R.id.item_QLPhieuMuon){
                    fragment = new QLPhieuMuonFragment();
                } else if (menuItem.getItemId() == R.id.item_QLThanhVien){
                    fragment = new QLThanhVienFragment();
                } else if (menuItem.getItemId() == R.id.item_ThongKe){
                    fragment = new ThongKeFragment();
                } else if (menuItem.getItemId() == R.id.itemSachMuon){
                    fragment = new Top10SachMuonFragment();
                } else if (menuItem.getItemId() == R.id.itemDangXuat) {
                    //xoa sharedpreferences
                    SharedPreferences preferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();

                    Intent intent = new Intent(AdminActivity.this, ChonVaiTroActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

                if(fragment != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

        //hiển thị 1 số chức năng cho Admin
        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
        String loaitk = sharedPreferences.getString("loaitk", "");
        if (!loaitk.equals("admin")) {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.item_ThongKe).setVisible(false);
        }

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }


        return super.onOptionsItemSelected(item);
    }
}