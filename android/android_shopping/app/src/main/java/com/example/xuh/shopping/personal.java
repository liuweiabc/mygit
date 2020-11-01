package com.example.xuh.shopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xuh.shopping.adapter.AllCommodityAdapter;
import com.example.xuh.shopping.bean.Commodity;
import com.example.xuh.shopping.util.CommodityDbHelper;

import java.util.ArrayList;
import java.util.List;

public class personal extends AppCompatActivity {
    private TextView text;
    private ListView list;
    private String ni;
    CommodityDbHelper dbHelper;
    AllCommodityAdapter adapter;
    List<Commodity> allCommodities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        text = (TextView)findViewById(R.id.contentm);
        list = (ListView)findViewById(R.id.list_comment);
        ni = getIntent().getExtras().getString("nicheng");
        text.setText(ni);
        dbHelper = new CommodityDbHelper(getApplicationContext(), CommodityDbHelper.DB_NAME, null, 1);
        adapter = new AllCommodityAdapter(getApplicationContext());
        allCommodities = dbHelper.readspecCommodities(ni);
        adapter.setData(allCommodities);
        list.setAdapter(adapter);
        TextView tvBack = findViewById(R.id.tv_back);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Commodity commodity = (Commodity) list.getAdapter().getItem(position);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("position",position);
                bundle1.putByteArray("picture",commodity.getPicture());
                bundle1.putString("title",commodity.getTitle());
                bundle1.putString("description",commodity.getDescription());
                bundle1.putString("stuId",commodity.getStuId());
                bundle1.putString("reviewer",ni);
                Intent intent = new Intent(personal.this, ReviewCommodityActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }
}