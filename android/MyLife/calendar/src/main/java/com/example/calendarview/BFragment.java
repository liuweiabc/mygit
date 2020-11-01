package com.example.calendarview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BFragment extends Fragment {
    //BFragment 用于跳出记账要键入的详细内容part

    private TextView mTvTitle;
    private Button mBtnSubmit;
    private EditText mEdDesc, mEdNum;
    private String tmp_date;
    private RadioGroup money;
    private String name;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b,container,false);
        return view;/*返回fragment的view*/
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //
        mTvTitle = view.findViewById(R.id.tv_title);
        mBtnSubmit = view.findViewById(R.id.btn_submit);
        mEdDesc = view.findViewById(R.id.ed_desc);
        mEdNum = view.findViewById(R.id.ed_num);
        Bundle bundle = getArguments();
        name = bundle.getString("nicheng");

        /*以时间戳作为唯一 ID -> 已弃用 -> 采用数据库自增 / ->弃用ID/

        /*单选按钮组*/
        money = (RadioGroup) view.findViewById(R.id.money);
        //
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get tmp_date 应该放在Listener里，否则会出现date不变的bug
                tmp_date = ((CalendarActivity)getActivity()).getDate();
                AccountModel accountModel = new AccountModel();
                DateUtils du = new DateUtils();
                long writeDate = du.date2TimeStamp(tmp_date);
                //income or outcome
                int selected = money.getCheckedRadioButtonId();
                /*When income*/
                if (selected == R.id.ra_income) {
                    try {
                        accountModel = new AccountModel(name,writeDate,
                                Float.parseFloat(mEdNum.getText().toString()),
                                0,
                                mEdDesc.getText().toString());
                        /*Toast.makeText(getActivity(),accountModel.toString() , Toast.LENGTH_SHORT).show();*///用于测试各column是否正确
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Error input.", Toast.LENGTH_SHORT).show();
                        accountModel = new AccountModel(name,0, 0, 0, "An error record.");
                    }
                    /*When outcome*/
                } else if (selected == R.id.ra_outcome) {
                    try {
                        accountModel = new AccountModel(name,writeDate,
                                0,
                                Float.parseFloat(mEdNum.getText().toString()),
                                mEdDesc.getText().toString());
                        /*Toast.makeText(getActivity(),accountModel.toString() , Toast.LENGTH_SHORT).show();*///用于测试各column是否正确
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Error input.", Toast.LENGTH_SHORT).show();
                        accountModel = new AccountModel(name,0, 0, 0, "An error record.");
                    }
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
                boolean success = dataBaseHelper.addOne(accountModel);
                Toast.makeText(getActivity(), "Success= " + success, Toast.LENGTH_SHORT).show();

                /*AccountModel accountModel = new AccountModel(tmp_date,
                        Float.parseFloat(mEdNum.getText().toString()),
                        0,
                        mEdDesc.getText().toString());
                if(accountModel.getDate() == null || accountModel.getDescription() == null){
                    Toast.makeText(getActivity(),"Please input values!" , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),accountModel.toString() , Toast.LENGTH_SHORT).show();
                }*/

                /*清空输入框*/
                mEdDesc.setText("");
                /*mEdDesc.setText(tmp_date);//用于测试BFragment是否能正确获取到MainActivity中的date参数*/
                mEdNum.setText("");

                getActivity().getSupportFragmentManager().popBackStack();//返回刚才的AFragment 防止出现重叠
            }
        });

    }
/*
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // TODO Auto-generated method stub
        switch (checkedId) {
            //支出
            case R.id.radio0:
                break;
            //收入
            case R.id.radio1:
                break;
        }
    }*/
    
    
}