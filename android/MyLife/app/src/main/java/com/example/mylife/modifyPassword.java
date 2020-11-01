package com.example.mylife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mylife.bean.User;
import com.example.mylife.util.UserDbHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;

public class modifyPassword extends AppCompatActivity {

    private EditText edit1,edit2,edit3;
    private String username;
    private Button button;
    LinkedList<User> users = new LinkedList<>();
    private String stunumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        edit1 = (EditText)findViewById(R.id.prime);
        edit2 = (EditText)findViewById(R.id.newpassword);
        edit3 = (EditText)findViewById(R.id.double_password);
        button = (Button)findViewById(R.id.btn_true);
        Bundle bundle =getIntent().getExtras();
        UserDbHelper dbHelper = new UserDbHelper(getApplicationContext(), UserDbHelper.DB_NAME,null,1);
        if(bundle != null){
            username = bundle.getString("username");
            stunumber = dbHelper.readusername(username);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                users = dbHelper.readUsers();
                boolean flag = false;
                if(CheckInput()){
                    for(User user : users) {
                        if(user.getUsername().equals(stunumber) && user.getPassword().equals(edit1.getText().toString()) ) {
                            flag = true;
                            if(edit2.getText().toString().equals(edit3.getText().toString()) && !edit2.getText().toString().equals(edit1.getText().toString())){
                                if(dbHelper.updateUser(user.getUsername(),edit2.getText().toString())){
                                    Toast.makeText(modifyPassword.this,"密码修改成功!",Toast.LENGTH_SHORT).show();
                                    dbHelper.close();
                                    finish();
                                }
                                else{
                                    Toast.makeText(modifyPassword.this,"密码因未知原因修改失败!",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else if(!edit2.getText().toString().equals(edit3.getText().toString())){
                                Toast.makeText(modifyPassword.this,"两次输入的密码不一致!",Toast.LENGTH_SHORT).show();
                            }
                            else if(edit2.getText().toString().equals(edit1.getText().toString()))
                                Toast.makeText(modifyPassword.this,"新密码不能与原密码相同!",Toast.LENGTH_SHORT).show();
                        }
                    }
                    //否则提示登录失败,需要重新输入
                    if (!flag) {
                        Toast.makeText(modifyPassword.this,"原密码输入错误!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public boolean CheckInput() {
        String prime = edit1.getText().toString();
        String new1 = edit2.getText().toString();
        String new2 = edit3.getText().toString();
        if(prime.trim().equals("")) {
            Toast.makeText(modifyPassword.this,"学号不能为空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(new1.trim().equals("")) {
            Toast.makeText(modifyPassword.this,"新密码不能为空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(new2.trim().equals("")) {
            Toast.makeText(modifyPassword.this,"重复新密码不能为空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}