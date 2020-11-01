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
public class testFragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String []content = {"木质家具与纯白墙壁结合，呈现出简约、安静的环境","小柜子非常实用",
            "在特朗普声称美国新冠疫情已经接近尾声后","美国原本眼看受控的疫情突然转折","6月27日一早，瑞幸咖啡发布声明称","再次为事件造成的恶劣影响向社会各界诚挚道歉。",
            "6月26日0时至24时，北京新增报告本地确诊病例17例","根据文旅部消息，端午假期第2日","近日，参与侦破口秀演员卡","其中一单就是发给卡姆",
            "今日早间，瑞幸发公告称","管在传统社交平台方面的出海之路并不顺畅","在疫情阴霾笼罩之下，一季度巨亏40亿元","退款困难、客服难接通等问",
            "世卫组织首席科学家苏米娅·斯瓦米纳坦表示","比起一般需要8-10年的通常流程","另据我国科技部19日透露","西班牙巴塞罗那大学的一个研究小组对当地废水样本做了检测"};
    private RecyclerView recyclerView;
    private List<goodsModel> goodsList;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public testFragment1() {
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
        View view=inflater.inflate(R.layout.fragment_test1,container,false);
        System.out.println(content[0]);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview2);
        goodsList = new ArrayList<>();
        for(int i = 0;i < content.length;i++){
            int imageid = getResources().getIdentifier("method"+i,"drawable",this.getActivity().getPackageName());
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