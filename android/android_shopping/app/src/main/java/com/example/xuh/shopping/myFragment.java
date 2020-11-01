package com.example.xuh.shopping;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xuh.shopping.adapter.AllCommodityAdapter;
import com.example.xuh.shopping.adapter.MyCollectionAdapter;
import com.example.xuh.shopping.bean.Collection;
import com.example.xuh.shopping.bean.Commodity;
import com.example.xuh.shopping.util.CommodityDbHelper;
import com.example.xuh.shopping.util.MyCollectionDbHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link myFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class myFragment extends Fragment {

    private ImageView image;
    CommodityDbHelper dbHelper;
    AllCommodityAdapter adapter;
    List<Commodity> allCommodities = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView lv;
    private View rootview;
    private TextView name1;
    private TextView name2;
    private TextView phone;
    private ImageButton button,button1,button2;
    private TextView address;
    List<Collection> myCollections = new ArrayList<>();
    private String temp;

    MyCollectionDbHelper dbHelper1;
    //CommodityDbHelper commodityDbHelper;
    MyCollectionAdapter adapter1;

    public myFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment myFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static myFragment newInstance(String param1, String param2) {
        myFragment fragment = new myFragment();
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
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_my, container, false);
        EventBus.getDefault().register(this);

        lv = (ListView)rootview.findViewById(R.id.lv);
        image = (ImageView)rootview.findViewById(R.id.headimg);
        name1 = rootview.findViewById(R.id.mrliu);
        name2 = rootview.findViewById(R.id.myname);
        phone = rootview.findViewById(R.id.myphone);
        button = rootview.findViewById(R.id.edit);
        button1 = rootview.findViewById(R.id.shoucang);
        button2 = rootview.findViewById(R.id.dongtai);
        address = rootview.findViewById(R.id.myaddress);
        /*ArrayList<String>list = new ArrayList<String>();
        list.add("今天天气不错");
        list.add("大家好");
        list.add("我是刘同学");
        Myadapter adapter = new Myadapter(myFragment.this.getActivity(),list);
        lv.setAdapter(adapter);*/
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
               startActivity(new Intent(getActivity(),LoginActivity.class));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ModifyInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("stu_number2",phone.getText().toString());
                intent.putExtras(bundle);
                startActivityForResult(intent,0);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Commodity commodity = (Commodity) lv.getAdapter().getItem(position);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("position",position);
                bundle1.putByteArray("picture",commodity.getPicture());
                bundle1.putString("title",commodity.getTitle());
                bundle1.putString("description",commodity.getDescription());
                bundle1.putString("stuId",commodity.getStuId());
                bundle1.putString("reviewer",name1.getText().toString());
                Intent intent = new Intent(getActivity(), ReviewCommodityActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv.setAdapter(null);
                dbHelper1 = new MyCollectionDbHelper(getActivity().getApplicationContext(),MyCollectionDbHelper.DB_NAME,null,1);
                myCollections = dbHelper1.readMyCollections(name1.getText().toString());
                adapter1 = new MyCollectionAdapter(getActivity().getApplicationContext());
                adapter1.setData(myCollections);
                lv.setAdapter(adapter1);
                //设置长按删除事件
                lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("提示:").setMessage("确定删除此收藏商品吗?").setIcon(R.drawable.icon_user).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Collection collection = (Collection) adapter.getItem(position);
                                //删除收藏商品项
                                dbHelper1.deleteMyCollection(collection.getTitle(),collection.getDescription(),collection.getNicheng());
                                Toast.makeText(getActivity(),"删除成功!",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                        return false;
                    }
                });
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv.setAdapter(null);
                dbHelper = new CommodityDbHelper(getActivity().getApplicationContext(), CommodityDbHelper.DB_NAME, null, 1);
                adapter = new AllCommodityAdapter(getActivity().getApplicationContext());
                allCommodities = dbHelper.readspecCommodities(temp);
                adapter.setData(allCommodities);
                lv.setAdapter(adapter);
            }
        });
        return rootview;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(message mes){
        name1.setText(mes.getNicheng());
        name2.setText(mes.getNicheng());
        phone.setText(mes.getUsername());
        temp = mes.getNicheng();
        dbHelper = new CommodityDbHelper(getActivity().getApplicationContext(), CommodityDbHelper.DB_NAME, null, 1);
        adapter = new AllCommodityAdapter(getActivity().getApplicationContext());
        allCommodities = dbHelper.readspecCommodities(mes.getNicheng());
        adapter.setData(allCommodities);
        lv.setAdapter(adapter);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        /*if(resultCode == 1){
            Bundle bundle = data.getExtras();
            String namString = bundle.getString("nicheng");
            String nam2String = bundle.getString("username");
            name1.setText(namString);
            name2.setText(namString);
            phone.setText(nam2String);
        }*/
        if(resultCode == 0){
            Bundle bundle = data.getExtras();
            String address1 = bundle.getString("address");
            address.setText(address1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}