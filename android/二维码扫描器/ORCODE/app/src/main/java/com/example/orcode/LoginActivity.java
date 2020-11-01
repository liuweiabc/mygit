package com.example.orcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Button button,button1;
    private MyDBHelper dbHelper;
    private EditText username;
    private EditText userpassword;
    private boolean test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button = (Button)findViewById(R.id.btn_login);
        button.getBackground().setAlpha(100);
        button1 = (Button)findViewById(R.id.en);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logonClicked(v);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginClicked(v);
                if(test) {
                    Intent intent = new Intent(LoginActivity.this, QRcodeActivity.class);
                    startActivity(intent);
                }
            }
        });
        dbHelper = new MyDBHelper(this,"UserStore.db",null,1);
    }
    public void logonClicked(View view){
        Intent intent = new Intent(LoginActivity.this,EnrollActivity.class);
        startActivity(intent);
    }
    public void loginClicked(View view) {
        username=(EditText)findViewById(R.id.account_input);
        userpassword=(EditText)findViewById(R.id.password_input);
        String userName=username.getText().toString();
        String passWord=userpassword.getText().toString();
        if (login(userName,passWord)) {
            Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
            test = true;
        }
        else {
            Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
            test = false;
        }
    }
    public boolean login(String username,String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from userData where name=? and password=?";
        Cursor cursor = db.rawQuery(sql, new String[] {username, password});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }
}
