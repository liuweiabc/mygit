package com.example.xuh.shopping;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xuh.shopping.bean.User;
import com.example.xuh.shopping.util.UserDbHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;

/**
 * 登录界面Activity类
 * @author : autumn_leaf
 */
public class LoginActivity extends AppCompatActivity {

    EditText EtStuNumber,EtStuPwd;
    private String username;
    private String nicheng1;

    LinkedList<User> users = new LinkedList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView tvRegister = findViewById(R.id.tv_register);
        //跳转到注册界面
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        EtStuNumber = findViewById(R.id.et_username);
        EtStuPwd = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.getBackground().setAlpha(120);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;
                if(CheckInput()) {
                    UserDbHelper dbHelper = new UserDbHelper(getApplicationContext(),UserDbHelper.DB_NAME,null,1);
                    users = dbHelper.readUsers();
                    for(User user : users) {
                        //如果可以找到,则输出登录成功,并跳转到主界面
                        if(user.getUsername().equals(EtStuNumber.getText().toString()) && user.getPassword().equals(EtStuPwd.getText().toString()) ) {
                            flag = true;
                            Toast.makeText(LoginActivity.this,"恭喜你登录成功!",Toast.LENGTH_SHORT).show();
                            username = EtStuNumber.getText().toString();
                            nicheng1 = dbHelper.readnicheng(username);
                            /*Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            System.out.println(nicheng1);
                            bundle.putString("username",username);
                            bundle.putString("nicheng",nicheng1);
                            intent.putExtras(bundle);
                            LoginActivity.this.setResult(1,intent);
                            Intent intent1 = new Intent(LoginActivity.this,WeiboActivity.class);
                            Bundle bundle1 = new Bundle();
                            bundle1.putString("user_id",nicheng1);
                            intent1.putExtras(bundle1);
                            startActivity(intent1);
                            finish();*/
                            EventBus.getDefault().post(new message(nicheng1,username));
                            finish();
                        }
                    }
                    //否则提示登录失败,需要重新输入
                    if (!flag) {
                        Toast.makeText(LoginActivity.this,"学号或密码输入错误!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //检查输入是否符合要求
    public boolean CheckInput() {
        String StuNumber = EtStuNumber.getText().toString();
        String StuPwd = EtStuPwd.getText().toString();
        if(StuNumber.trim().equals("")) {
            Toast.makeText(LoginActivity.this,"学号不能为空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(StuPwd.trim().equals("")) {
            Toast.makeText(LoginActivity.this,"密码不能为空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
