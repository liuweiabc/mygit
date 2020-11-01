package com.example.mylife;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.example.mylife.util.UserDbHelper;

import demo.gesturepsd.gesturepsd_android.GestureEditActivity;

public class showBottomDialog {
    private View view;
    public void BottomDialog(Context context) {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(context, R.style.DialogTheme);
        //2、设置布局
        view = View.inflate(context, R.layout.dialog, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.gestureEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,GestureEditActivity.class);
                Bundle bundle = new Bundle();
                @SuppressLint("ResourceType") String username = context.getString(R.id.et_username);
                UserDbHelper dbHelper = new UserDbHelper(context.getApplicationContext(), UserDbHelper.DB_NAME,null,1);
                String nicheng = dbHelper.readnicheng(username);
                if(nicheng != null){
                    bundle.putString("username",nicheng);
                    context.startActivity(intent);
                }
                else
                    Toast.makeText(context,"找不到用户昵称",Toast.LENGTH_SHORT);
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MainActivity.class);
                Bundle bundle = new Bundle();
                @SuppressLint("ResourceType") String username = context.getString(R.id.et_username);
                UserDbHelper dbHelper = new UserDbHelper(context.getApplicationContext(), UserDbHelper.DB_NAME,null,1);
                String nicheng = dbHelper.readnicheng(username);
                if(nicheng != null){
                    bundle.putString("username",nicheng);
                    context.startActivity(intent);
                }
                else
                    Toast.makeText(context,"找不到用户昵称",Toast.LENGTH_SHORT);
                dialog.dismiss();
            }
        });
    }
}
