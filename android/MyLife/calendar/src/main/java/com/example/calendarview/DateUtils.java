package com.example.calendarview;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public String timeStamp2Date(long ts){
        Long timeStamp = ts;  //获取当前时间戳
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));      // 时间戳转换成时间
        /*System.out.println("格式化结果：" + sd);*/
        return sd;
    }

    public long date2TimeStamp(String date){
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date test = sdf.parse(date);
            Long timeStamp = test.getTime();
            sdf=new SimpleDateFormat("yyyy-MM-dd");
            String sd = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));
            // 测试用语句
            /*System.out.println("获取到的时间戳: " + timeStamp + " 相应的日期为: " + sd );*/
            return timeStamp;
        }catch (Exception e){
            System.out.println("An error occurred.");
            return 0;
        }
    }
}
