package com.example.mylife.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylife.Login;
import com.example.mylife.MainActivity;
import com.example.mylife.Modifyinfo;
import com.example.mylife.R;
import com.example.mylife.message;
import com.example.mylife.myBill;
import com.example.mylife.showBottomDialog;
import com.example.mylife.util.UserDbHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import demo.gesturepsd.gesturepsd_android.GestureEditActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Personal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Personal extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String temp;
    private TextView text1,text2,text3,text4,text5;
    private Button button;
    private showBottomDialog show = new showBottomDialog();

    public Personal() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Personal.
     */
    // TODO: Rename and change types and number of parameters
    public static Personal newInstance(String param1, String param2) {
        Personal fragment = new Personal();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //EventBus.getDefault().register(this);
        View rootview = inflater.inflate(R.layout.fragment_personal, container, false);
        text1 = rootview.findViewById(R.id.username);
        text2 = rootview.findViewById(R.id.mymenu);
        text3 = rootview.findViewById(R.id.modify_password);
        text4 = rootview.findViewById(R.id.modify_gesture);
        text5 = rootview.findViewById(R.id.modify_info);
        button = rootview.findViewById(R.id.dismiss);
        //UserDbHelper dbHelper = new UserDbHelper(getActivity().getApplicationContext(), UserDbHelper.DB_NAME,null,1);
        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = text1.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("username",temp);
                Intent intent = new Intent(getActivity(),com.example.mylife.modifyPassword.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        Bundle bundle = getArguments();
        String text = bundle.getString("username");
        text1.setText(text);

        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), myBill.class);
                Bundle bu = new Bundle();
                bu.putString("nicheng",text);
                intent.putExtras(bu);
                startActivity(intent);
            }
        });

        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText inputServer = new EditText(getActivity());
                inputServer.setFocusable(true);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("请输入用户密码").setIcon(
                        R.drawable.ic_launcher).setView(inputServer).setNegativeButton(
                        "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                String inputpass = inputServer.getText().toString();
                                UserDbHelper dbHelper = new UserDbHelper(getActivity().getApplicationContext(), UserDbHelper.DB_NAME,null,1);
                                String password = dbHelper.readPassword(text);
                                if(inputpass.equals(password)){
                                    Intent intent = new Intent(getActivity(),GestureEditActivity.class);
                                    startActivity(intent);
                                }
                                else
                                    Toast.makeText(getActivity(),"密码输入错误",Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.show();
            }
        });
        text5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDbHelper dbHelper = new UserDbHelper(getActivity().getApplicationContext(), UserDbHelper.DB_NAME,null,1);
                String stu_number = dbHelper.readusername(text);
                if(stu_number != null){
                    Intent intent = new Intent(getActivity(), Modifyinfo.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("stu_number",stu_number);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("退出登录").setMessage("确定要退出当前账号吗？")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getActivity(), Login.class);
                                        startActivity(intent);
                                    }
                                });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        });

        return rootview;
    }
    /*@Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(message mes){
        temp = mes.getUsername();
        text1.setText(temp);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        /*if(resultCode == 1){
            Bundle bundle = data.getExtras();
            String namString = bundle.getString("nicheng");
            String nam2String = bundle.getString("username");
            name1.setText(namString);
            name2.setText(namString);
            phone.setText(nam2String);
        }*/
        /*if(resultCode == 0){
            Bundle bundle = data.getExtras();
            String address1 = bundle.getString("address");
            address.setText(address1);
        }
    }*/

    /*@Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }*/
}