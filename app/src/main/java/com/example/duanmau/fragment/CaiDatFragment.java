package com.example.duanmau.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duanmau.DoiMatKhauActivity;
import com.example.duanmau.R;

public class CaiDatFragment extends Fragment {
    TextView txtDoiMatKhau;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_caidat, container, false);
        txtDoiMatKhau = (TextView) view.findViewById(R.id.txtDoiMatKhau);
        txtDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(getActivity(), DoiMatKhauActivity.class));
            }
        });

        return view;
    }

}
