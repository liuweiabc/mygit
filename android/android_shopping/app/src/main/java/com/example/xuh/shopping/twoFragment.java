package com.example.xuh.shopping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class twoFragment extends Fragment {
    private ViewPager pager;
    private FragmentAdapter fragmentAdapter;
    private List<Fragment> fragmentList;
    private TabLayout tabLayout;
    private List<String> mTitles;
    private String [] title={"首页","材料","方案","设计师之家"};
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_two,container,false);
        pager=view.findViewById(R.id.pages);
        tabLayout=view.findViewById(R.id.tab_layouts);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        fragmentList=new ArrayList<>();
        mTitles=new ArrayList<>();
        mTitles.add(title[0]);
        fragmentList.add(new Index_home());
        mTitles.add(title[1]);
        fragmentList.add(new testFragment());
        mTitles.add(title[2]);
        fragmentList.add(new testFragment1());
        mTitles.add(title[3]);
        fragmentList.add(new designerFragment());
        fragmentAdapter=new FragmentAdapter(getActivity().getSupportFragmentManager(),fragmentList,mTitles);
        pager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系
    }
}
