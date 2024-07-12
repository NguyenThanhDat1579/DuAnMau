package com.example.duanmau;

import android.os.Bundle;
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
import androidx.fragment.app.FragmentManager;

import com.example.duanmau.dao.SachDAO;
import com.example.duanmau.fragment.QLTheLoaiSachFragment;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
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

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                if (menuItem.getItemId() == R.id.item_QLTheLoaiSach){
                    fragment = new QLTheLoaiSachFragment();
                } else if (menuItem.getItemId() == R.id.item_QLPhieuMuon){

                } else if (menuItem.getItemId() == R.id.item_QLThanhVien){

                } else if (menuItem.getItemId() == R.id.item_ThongKe){

                } else if (menuItem.getItemId() == R.id.itemSachMuon){

                } else if (menuItem.getItemId() == R.id.itemDanhDau){

                } else if (menuItem.getItemId() == R.id.itemSetting){

                } else if (menuItem.getItemId() == R.id.itemDangXuat) {

                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
                drawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }
}