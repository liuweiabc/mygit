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

public class MyLayout extends RelativeLayout {
    private TextView text1;
    private TextView text2;
    final int SUCCESS = 1;
    public MyLayout(Context context){
        super(context);
        LayoutInflater.from(context).inflate(R.layout.taskchoice,this); //剪裁实例化mylayout布局
        text1 = (TextView)findViewById(R.id.tasktext);
        text2 = (TextView)findViewById(R.id.tasktime);
        ImageButton button =(ImageButton)findViewById(R.id.finish);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"任务已完成",Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Connection conn = null;
                        conn =(Connection) DBOpenHelper.getConn();
                        String sql = "delete  from task where 任务 = ? and 时间 = ?;";
                        PreparedStatement pst;
                        try {
                            pst = (PreparedStatement) conn.prepareStatement(sql);
                            pst.setString(1,text1.getText().toString());
                            pst.setString(2,text2.getText().toString());
                            pst.executeUpdate();
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
    public void setValues(String text,String time){
        text1.setText(text);
        text2.setText(time);
    }
}
