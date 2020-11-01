package com.example.orcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnrollActivity extends AppCompatActivity {
    private Button button1;
    private MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new MyDBHelper(this,"UserStore.db",null,1);
        setContentView(R.layout.activity_enroll_activity);
        button1 = (Button)findViewById(R.id.btn_enroll);
        button1.getBackground().setAlpha(100);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logon(v);
            }
        });
    }
    public void logon(View view){
        EditText editText3=(EditText)findViewById(R.id.account_input);
        EditText editText4=(EditText)findViewById(R.id.password_input);
        String newname =editText3.getText().toString();
        String password=editText4.getText().toString();
        if (CheckIsDataAlreadyInDBorNot(newname)) {
            Toast.makeText(this,"该用户名已被注册，注册失败",Toast.LENGTH_SHORT).show();
        }
        else {

            if (register(newname, password)) {
                Toast.makeText(this, "插入数据表成功", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public boolean register(String username,String password){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",username);
        values.put("password",password);
        db.insert("userData",null,values);
        db.close();
        return true;
    }
    public boolean CheckIsDataAlreadyInDBorNot(String value){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String Query = "Select * from userData where name =?";
        Cursor cursor = db.rawQuery(Query,new String[] { value });
        if (cursor.getCount()>0){
            cursor.close();
            return  true;
        }
        cursor.close();
        return false;
    }

}
