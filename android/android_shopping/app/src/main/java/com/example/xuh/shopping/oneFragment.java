package com.example.xuh.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xuh.shopping.adapter.AllCommodityAdapter;
import com.example.xuh.shopping.bean.Commodity;
import com.example.xuh.shopping.util.CommodityDbHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link oneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class oneFragment extends Fragment {

    ListView lvAllCommodity;
    List<Commodity> allCommodities = new ArrayList<>();
    ImageButton ibLearning,ibElectronic,ibDaily,ibSports;
    TextView text;

    CommodityDbHelper dbHelper;
    AllCommodityAdapter adapter;
    TextView tvStuNumber;
    String temp1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public oneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment oneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static oneFragment newInstance(String param1, String param2) {
        oneFragment fragment = new oneFragment();
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
        EventBus.getDefault().register(this);
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_one,container,false);
        text = view.findViewById(R.id.chenghao);
        lvAllCommodity = view.findViewById(R.id.lv_all_commodity);
        dbHelper = new CommodityDbHelper(getActivity().getApplicationContext(), CommodityDbHelper.DB_NAME, null, 1);
        adapter = new AllCommodityAdapter(getActivity().getApplicationContext());
        allCommodities = dbHelper.readAllCommodities();
        adapter.setData(allCommodities);
        lvAllCommodity.setAdapter(adapter);
        //final Bundle bundle = this.getActivity().getIntent().getExtras();
        tvStuNumber = view.findViewById(R.id.tv_student_number);
        TextView tvRefresh = view.findViewById(R.id.tv_refresh);
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allCommodities = dbHelper.readAllCommodities();
                adapter.setData(allCommodities);
                lvAllCommodity.setAdapter(adapter);
            }
        });
        lvAllCommodity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Commodity commodity = (Commodity) lvAllCommodity.getAdapter().getItem(position);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("position",position);
                bundle1.putByteArray("picture",commodity.getPicture());
                bundle1.putString("title",commodity.getTitle());
                bundle1.putString("description",commodity.getDescription());
                bundle1.putString("stuId",commodity.getStuId());
                bundle1.putString("reviewer",temp1);
                Intent intent = new Intent(getActivity(), ReviewCommodityActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
        return view;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(message mes){
        tvStuNumber.setText("欢迎您，"+mes.getNicheng());
        temp1 = mes.getNicheng();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}