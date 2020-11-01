package com.example.stsystem3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.example.stsystem3.LimitInputTextWatcher;

public class input extends AppCompatActivity {
    ImageButton save;
    ImageButton save1;
    ImageButton submit;
    EditText text1,text2,text3,text4;
    EditText texta,textb,textc,textd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        ImageButton  image = (ImageButton)findViewById(R.id.return3);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save = (ImageButton)findViewById(R.id.save);
        save1 = (ImageButton)findViewById(R.id.save1);
        submit = (ImageButton)findViewById(R.id.submitall);
        text1 = (EditText)findViewById(R.id.add1);
        text2 = (EditText)findViewById(R.id.nam1);
        text2.addTextChangedListener(new LimitInputTextWatcher(text2));
        text3 = (EditText)findViewById(R.id.pho1);
        text4 = (EditText)findViewById(R.id.peo1);
        texta = (EditText)findViewById(R.id.add2);
        textb = (EditText)findViewById(R.id.nam2);
        textb.addTextChangedListener(new LimitInputTextWatcher(textb));
        textc = (EditText)findViewById(R.id.pho2);
        textd = (EditText)findViewById(R.id.peo2);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(text3.length() != 11){
                    Toast t = Toast.makeText(input.this, "电话位数不正确", Toast.LENGTH_LONG);
                    text3.setText("");
                }
                else if(text4.length() != 18){
                    Toast t = Toast.makeText(input.this, "身份证位数不正确", Toast.LENGTH_SHORT);
                    text4.setText("");
                }
                else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Connection conn = null;
                            int u = 0;
                            conn =(Connection) DBOpenHelper.getConn();
                            String sql = "insert into user values(?,?,?,?)";
                            PreparedStatement pst;
                            try {
                                pst = (PreparedStatement) conn.prepareStatement(sql);
                                //将输入的edit框的值获取并插入到数据库中
                                pst.setString(1,text1.getText().toString());
                                pst.setString(2,text2.getText().toString());
                                pst.setString(3,text3.getText().toString());
                                pst.setString(4,text4.getText().toString());

                                u = pst.executeUpdate();
                                if(u != 0){
                                    Looper.prepare();
                                    Toast t = Toast.makeText(input.this, "数据插入成功", Toast.LENGTH_SHORT);
                                    t.show();
                                    text1.setText("");
                                    text2.setText("");
                                    text3.setText("");
                                    text4.setText("");
                                    Looper.loop();
                                }
                                else{
                                    Looper.prepare();
                                    Toast t = Toast.makeText(input.this, "数据插入失败", Toast.LENGTH_SHORT);
                                    t.show();
                                    Looper.loop();
                                }
                                pst.close();
                                conn.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }


            }
        });

        save1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(textc.length() != 11){
                    Toast t = Toast.makeText(input.this, "电话位数不正确", Toast.LENGTH_SHORT);
                    textc.setText("");
                }
                else if(textd.length() != 18){
                    Toast t = Toast.makeText(input.this, "身份证位数不正确", Toast.LENGTH_SHORT);
                    textd.setText("");
                }
                else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Connection conn = null;
                            int u = 0;
                            conn =(Connection) DBOpenHelper.getConn();
                            String sql = "insert into user values(?,?,?,?)";
                            PreparedStatement pst;
                            try {
                                pst = (PreparedStatement) conn.prepareStatement(sql);
                                //将输入的edit框的值获取并插入到数据库中
                                pst.setString(1,texta.getText().toString());
                                pst.setString(2,textb.getText().toString());
                                pst.setString(3,textc.getText().toString());
                                pst.setString(4,textd.getText().toString());

                                u = pst.executeUpdate();
                                if(u != 0){
                                    Looper.prepare();
                                    Toast t = Toast.makeText(input.this, "数据插入成功", Toast.LENGTH_SHORT);
                                    t.show();
                                    texta.setText("");
                                    textb.setText("");
                                    textc.setText("");
                                    textd.setText("");
                                    Looper.loop();
                                }
                                else{
                                    Looper.prepare();
                                    Toast t = Toast.makeText(input.this, "数据插入失败", Toast.LENGTH_SHORT);
                                    t.show();
                                    Looper.loop();
                                }
                                pst.close();
                                conn.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
    }
}
