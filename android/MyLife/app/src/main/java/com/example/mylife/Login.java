package com.example.mylife;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylife.bean.User;
import com.example.mylife.util.UserDbHelper;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.LinkedList;

import demo.gesturepsd.gesturepsd_android.GestureEditActivity;
import demo.gesturepsd.gesturepsd_android.GestureVerifyActivity;

public class Login extends AppCompatActivity {

    EditText EtStuNumber,EtStuPwd;
    private String username,nicheng;
    LinkedList<User> users = new LinkedList<>();
    private showBottomDialog show = new showBottomDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView tvRegister = findViewById(R.id.tv_register);
        //跳转到注册界面
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
        EtStuNumber = findViewById(R.id.et_username);
        EtStuPwd = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btngesture = findViewById(R.id.btn_gesture);
        btngesture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("username",nicheng);
                Intent intent = new Intent(Login.this, GestureVerifyActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btnLogin.getBackground().setAlpha(120);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;
                if(CheckInput()) {
                    UserDbHelper dbHelper = new UserDbHelper(getApplicationContext(), UserDbHelper.DB_NAME,null,1);
                    nicheng = dbHelper.readnicheng(EtStuNumber.getText().toString());
                    users = dbHelper.readUsers();
                    for(User user : users) {
                        //如果可以找到,则输出登录成功,并跳转到主界面
                        if(user.getUsername().equals(EtStuNumber.getText().toString()) && user.getPassword().equals(EtStuPwd.getText().toString()) ) {
                            flag = true;
                            username = EtStuNumber.getText().toString();
                            if(dbHelper.readflag(user.getUsername()) == 1){
                                Toast.makeText(Login.this,"恭喜你登录成功!",Toast.LENGTH_SHORT).show();
                                //EventBus.getDefault().post(new message(username));
                                Intent intent = new Intent(Login.this,MainActivity.class);
                                Bundle bundle = new Bundle();
                                if(nicheng != null){
                                    bundle.putString("username",nicheng);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                                else
                                    Toast.makeText(Login.this,"找不到用户昵称",Toast.LENGTH_SHORT);
                            }
                            else{
                                show.BottomDialog(Login.this);
                                dbHelper.updateflag(user.getUsername());
                                dbHelper.close();
                            }

                        }
                    }
                    //否则提示登录失败,需要重新输入
                    if (!flag) {
                        Toast.makeText(Login.this,"学号或密码输入错误!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public boolean CheckInput() {
        String StuNumber = EtStuNumber.getText().toString();
        String StuPwd = EtStuPwd.getText().toString();
        if(StuNumber.trim().equals("")) {
            Toast.makeText(Login.this,"学号不能为空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(StuPwd.trim().equals("")) {
            Toast.makeText(Login.this,"密码不能为空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}