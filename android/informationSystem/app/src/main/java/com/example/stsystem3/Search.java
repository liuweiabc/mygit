package com.example.stsystem3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;
import com.example.stsystem3.LimitInputTextWatcher;

public class Search extends AppCompatActivity {

    ImageButton image;
    Button button;
    EditText text;
    List<person> personList = new ArrayList<person>();
    final int SUCCESS = 1;
    final int FAIL = 2;

    private android.os.Handler Handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LinearLayout lin = (LinearLayout) findViewById(R.id.lin);
            lin.removeAllViews();
            if(msg.what == 1) {
                for (person p : (List<person>)msg.obj) {
                    MyLayout1 tv = new MyLayout1(Search.this);
                    tv.setValues(p.getName(),p.getPeonumber(),p.getPhone(),p.getAddress());
                    lin.addView(tv);
                }
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        image = (ImageButton)findViewById(R.id.return1);
        button = (Button)findViewById(R.id.button);
        text = (EditText)findViewById(R.id.searchView);
        text.addTextChangedListener(new LimitInputTextWatcher(text));
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
                        String sql = "select * from user where 姓名 = ?;";
                        PreparedStatement pst;
                        try {
                            pst = (PreparedStatement) conn.prepareStatement(sql);
                            //将输入的edit框的值获取并插入到数据库中
                            pst.setString(1,text.getText().toString());
                            ResultSet rs = pst.executeQuery();
                            System.out.println("error");
                            while(rs.next()){
                                String address = rs.getString("住址");
                                String name = rs.getString("姓名");
                                String peonumber = rs.getString("身份证号");
                                String phone = rs.getString("手机号");
                                person p = new person(address,name,peonumber,phone);
                                System.out.println(p.getPeonumber());
                                personList.add(p);
                            }
                            Message msg = new Message();
                            msg.what = SUCCESS;
                            msg.obj = personList;
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
