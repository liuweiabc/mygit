package com.example.stsystem3;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyLayout1 extends RelativeLayout {
    private TextView text1;
    private TextView text2,text3,text4;
    public MyLayout1(Context context){
        super(context);
        LayoutInflater.from(context).inflate(R.layout.sea,this); //剪裁实例化mylayout布局
        text1 = (TextView)findViewById(R.id.inputname);
        text2 = (TextView)findViewById(R.id.inputpeonumber);
        text3 = (TextView)findViewById(R.id.inputphone);
        text4 = (TextView)findViewById(R.id.inputaddress);
    }
    public void setValues(String name,String peonumber,String phone,String address){
        text1.setText(name);
        text2.setText(peonumber);
        text3.setText(phone);
        text4.setText(address);
    }
}
