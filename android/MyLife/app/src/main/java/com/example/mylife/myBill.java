package com.example.mylife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.calendarview.DataBaseHelper;
import com.example.mylife.Adapter.billAdapter;

import java.util.ArrayList;
import java.util.List;

public class myBill extends AppCompatActivity {

    DataBaseHelper dbHelper;
    billAdapter adapter;
    List<ListItem> allItems = new ArrayList<>();
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bill);
        Bundle bundle = getIntent().getExtras();
        lv = (ListView)findViewById(R.id.list1);
        dbHelper = new DataBaseHelper(myBill.this);
        adapter = new billAdapter(getApplicationContext());
        for(int i = 1;i <= 12;i++){
            String mon = i + "";
            String date = "2020-"+ mon + "-1";
            System.out.println(date);
            Float income = dbHelper.monthIncome(bundle.getString("nicheng"),date);
            Float outcome = dbHelper.monthOutcome(bundle.getString("nicheng"),date);
            System.out.println(income + outcome);
            Float total = income - outcome;
            String time = "2020." + mon;
            System.out.println(time);
            if(income == 0 && outcome == 0)
                continue;
            else{
                ListItem item = new ListItem(income,outcome,time,total);
                allItems.add(item);
            }
        }
        if(allItems.size() != 0){
            adapter.setData(allItems);
            lv.setAdapter(adapter);
        }

    }
}