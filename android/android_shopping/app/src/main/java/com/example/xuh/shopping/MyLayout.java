package com.example.xuh.shopping;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MyLayout extends RelativeLayout  {
    private TextView text1;
    private ImageView image,image1,image2,image3;
    private TextView name;
    public MyLayout(Context context){
        super(context);
        LayoutInflater.from(context).inflate(R.layout.mylayout,this); //剪裁实例化mylayout布局
        text1 = (TextView)findViewById(R.id.tasktime);
        name = (TextView)findViewById(R.id.name1);
        image = (ImageView)findViewById(R.id.head1);
        image1 = (ImageView)findViewById(R.id.onephoto);
        image2 = (ImageView)findViewById(R.id.twophoto);
        image3 = (ImageView)findViewById(R.id.threephoto);
    }
    public void setValues(String text1,String name){
        this.text1.setText(text1);
        this.name.setText(name);
    }
}
