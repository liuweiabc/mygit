package com.example.xuh.shopping;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class TabFragment extends Fragment {

    private TextView titleTv;

    private String mTitle;

    //这个构造方法是便于各导航同时调用一个fragment
    public TabFragment(String title){
        mTitle=title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_tab,container,false);
        titleTv=view.findViewById(R.id.tv_title);
        titleTv.setText(mTitle);
        return view;
    }
}