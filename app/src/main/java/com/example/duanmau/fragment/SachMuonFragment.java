package com.example.duanmau.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.adapter.Top10Adapter;
import com.example.duanmau.dao.PhieuMuonDAO;
import com.example.duanmau.dao.ThongKeDAO;
import com.example.duanmau.model.Sach;

import java.util.ArrayList;

public class SachMuonFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sachmuon, container, false);

        RecyclerView recyclerViewTop10 = view.findViewById(R.id.recyclertop10);

        ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
        ArrayList<Sach> list = thongKeDAO.getTop10();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewTop10.setLayoutManager(linearLayoutManager);
        Top10Adapter adapter = new Top10Adapter(getContext(), list);
        recyclerViewTop10.setAdapter(adapter);

        return view;
    }
}