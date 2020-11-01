package com.example.stsystem3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.stsystem3.MyLayout;

public class tasksending extends AppCompatActivity {

    Button button;
    final int SUCCESS = 1;
    List<task>persontask = new ArrayList<task>();

    private android.os.Handler Handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                final LinearLayout lin = (LinearLayout)findViewById(R.id.lin1);
                lin.removeAllViews();
                for (task p : (List<task>)msg.obj){
                    MyLayout lay = new MyLayout(tasksending.this);
                    lay.setValues(p.getTa(),p.getTime());
                    lin.addView(lay);
                    /*final ImageButton image = (ImageButton)lay.findViewById(R.id.finish);
                    image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            lin.removeView((RelativeLayout)image.getParent());
                        }
                    });*/
                }
            }


        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasksending);
        ImageButton image = (ImageButton)findViewById(R.id.return3);
        button = (Button)findViewById(R.id.sea);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Connection conn = null;
                        conn =(Connection) DBOpenHelper.getConn();
                        String sql = "select * from task;";
                        PreparedStatement pst;
                        try {
                            pst = (PreparedStatement) conn.prepareStatement(sql);
                            ResultSet rs = pst.executeQuery();
                            while(rs.next()){
                                String ta = rs.getString("任务");
                                String time = rs.getString("时间");
                                task p = new task(ta,time);
                                persontask.add(p);
                                System.out.println(p.getTa());
                            }
                            Message msg = new Message();
                            msg.what = SUCCESS;
                            msg.obj = persontask;
                            Handler.sendMessage(msg);
                            pst.close();
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}
