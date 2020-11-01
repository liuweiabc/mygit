package com.example.xuh.shopping;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link testFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class testFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String []content = {"木质家具与纯白墙壁结合，呈现出简约、安静的环境","在这项试验之前，法国、意大利等欧洲国家均有试验",
            "但是常常被误诊为流感而被掩盖。","近日，美国新冠疫情反弹","连续3日新增病例创新纪录","巴西卫生部公布的数据显示",
            "该国单日新增新冠肺炎确诊病例46860例，累计确诊1274974例","印度卫生部官方网站公布的最新数据显示"};
    private RecyclerView recyclerView;
    private List<goodsModel> goodsList;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public testFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment testFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static testFragment newInstance(String param1, String param2) {
        testFragment fragment = new testFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_test,container,false);
        System.out.println(content[0]);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview1);
        goodsList = new ArrayList<>();
        for(int i = 0;i < content.length;i++){
            int imageid = getResources().getIdentifier("goods_"+i,"drawable",this.getActivity().getPackageName());
            goodsList.add(new goodsModel(imageid,content[i]));
        }
        GridLayoutManager layout1 = new GridLayoutManager(this.getContext(),2);
        //layout1.setOrientation(LinearLayout.HORIZONTAL);
        recyclerView.setLayoutManager(layout1);
        goodsAdapter oneadapter = new goodsAdapter(goodsList);
        recyclerView.setAdapter(oneadapter);
        return view;
    }
}