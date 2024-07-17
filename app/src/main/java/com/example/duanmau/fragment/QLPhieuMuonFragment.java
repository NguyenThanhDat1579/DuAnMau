package com.example.duanmau.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.adapter.PhieuMuonAdapter;
import com.example.duanmau.dao.PhieuMuonDAO;
import com.example.duanmau.model.PhieuMuon;

import java.util.ArrayList;

public class QLPhieuMuonFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phieumuon, container, false);




        // ánh xạ
        RecyclerView lvPhieuMuon = view.findViewById(R.id.lvPhieuMuon);
        //data
        PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(getContext());
        ArrayList<PhieuMuon> listPM = phieuMuonDAO.getAllPhieuMuon();
        //adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        lvPhieuMuon.setLayoutManager(linearLayoutManager);
        PhieuMuonAdapter adapter = new PhieuMuonAdapter(listPM, getContext());
        lvPhieuMuon.setAdapter(adapter);

        return view;
    }
}
