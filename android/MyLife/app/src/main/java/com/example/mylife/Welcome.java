package com.example.mylife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Welcome extends AppCompatActivity {

    private Button skip_btn;
    private Handler myhandler;
    private boolean has_skip = false;
    private LinearLayout lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        lv = (LinearLayout)findViewById(R.id.lv);
        int random = (int)(Math.random()*3);
        int id = getResources().getIdentifier("welcome_pic"+random,"drawable",getPackageName());
        lv.setBackground(getResources().getDrawable(id));
        skip_btn = (Button)findViewById(R.id.switch_btn);

        //switch to the main activity by Timer
        myhandler = new Handler(){
            public void handleMessage(Message msg){
                switch (msg.what) {
                    //2秒的时候设置跳过按钮可见
                    case 110:
                        skip_btn.setVisibility(View.VISIBLE);
                        break;
                     //5秒的时候跳转页面
                    case 111:
                        if (!has_skip) {
                            Intent intent = new Intent();
                            intent.setClass(Welcome.this, Login.class);
                            startActivity(intent);
                        }
                        break;
                    default:
                        break;
                }
            }
        };

        myhandler.sendEmptyMessageDelayed(110,2000);
        myhandler.sendEmptyMessageDelayed(111, 5000);

        skip_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(Welcome.this, Login.class);
                startActivity(intent);
                has_skip = true;
            }
        });
    }
}