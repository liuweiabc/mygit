package com.example.calendarview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

public class CalendarActivity extends AppCompatActivity {

    SQLiteDatabase myDatabase;
    CalendarView calendarView;
    TextView myDate;
    private AFragment aFragment;
    private Button mBtnSave, mBtnTest;
    private BFragment bFragment;
    private String date;
    private String temp;

    public String getDate(){
        return date;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        /*切换到BFragment*/
        mBtnSave = findViewById(R.id.btn_save);
        mBtnTest = findViewById(R.id.testButton);
        final Bundle bundle = getIntent().getExtras();
        temp = bundle.getString("nicheng");
        /*button listeners*/
        //记一笔button
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bFragment == null) {
                    bFragment = new BFragment();
                    bFragment.setArguments(bundle);
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.animator.slide_right_in,
                                R.animator.slide_right_out,
                                R.animator.slide_right_in,
                                R.animator.slide_right_out
                        ).replace(R.id.fl_container, bFragment)
                        .addToBackStack(null)
                        .commit();
                /*getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,bFragment).commitAllowingStateLoss();*/

            }

        });

        //this test button is to test why the application will go wrong. 轮流测试各功能用的劳模button
        mBtnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = getDate();
                /*Long timeStamp = System.currentTimeMillis();  //获取当前时间戳
                Timestamp ts = Timestamp.valueOf(date);
                System.out.println("获取到的Date的时间戳: " + ts);

                //timeStamp2String Example1
                System.out.println("时间戳: " + timeStamp);
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                String sd = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));      // 时间戳转换成时间
                System.out.println("格式化结果：" + sd);*/

                //try String2timeStamp
                System.out.println(temp);
                System.out.println(date);
                DateUtils du = new DateUtils();
                long sDate = du.date2TimeStamp(date);

                //timeStamp2String Example2
                /*SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
                String sd2 = sdf2.format(new Date(Long.parseLong(String.valueOf(timeStamp))));
                System.out.println("格式化结果：" + sd2);*/

                /*DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                List<AccountModel> selectedAccount = dataBaseHelper.getSelected(date);

                ArrayAdapter accountArrayAdapter = new ArrayAdapter<AccountModel>(MainActivity.this,
                    android.R.layout.simple_list_item_1, dataBaseHelper.getSelected(date));

                //测试数据是否正确读出
                Toast.makeText(MainActivity.this, selectedAccount.toString(), Toast.LENGTH_SHORT).show();

                //*/
                DataBaseHelper dataBaseHelper = new DataBaseHelper(CalendarActivity.this);

                //测试修改后的本月收入/支出函数 例子：传入 2020-9-1 即可统计9月收入/支出
                System.out.println("本月收入: "+ dataBaseHelper.monthIncome(temp,"2020-9-1") + " 本月支出: " + dataBaseHelper.monthOutcome(temp,"2020-9-1"));
                dataBaseHelper.close();
            }
        });


        /*实例化Afragment*/
        /*aFragment = new AFragment();*/
        /*把Afragment添加到Activity中，记得调用commit*/
        /*getSupportFragmentManager().beginTransaction().add(R.id.fl_container,aFragment).commitAllowingStateLoss();*/

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        myDate = (TextView) findViewById(R.id.myDate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                /*String date = (month + 1) + "/" + dayOfMonth + "/" + year;*/
                date = year + "-" + (month + 1) + "-" + dayOfMonth;
                /*测试设置listView*/

                myDate.setText(date);
                aFragment = AFragment.newInstance(date);
                /*使用setCustomAnimations添加Fragment的过渡动画*/
                getSupportFragmentManager().beginTransaction().setCustomAnimations(
                        R.animator.slide_left_in,
                        R.animator.slide_left_out,
                        R.animator.slide_left_in,
                        R.animator.slide_left_out
                ).add(R.id.fl_container, aFragment).commitAllowingStateLoss();

                /*pull the date from database*/
                /*DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                List<AccountModel> allAccount = dataBaseHelper.getAll();
                Toast.makeText(MainActivity.this, allAccount.toString(), Toast.LENGTH_SHORT).show();*/

                getSupportFragmentManager().beginTransaction().remove(aFragment);//清空AFragment 防止出现重叠
            }
        });
    }

}