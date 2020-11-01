package com.example.xuh.shopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Myadapter1 extends BaseAdapter {
    private Context context;
    private ArrayList<String> list;

    public Myadapter1(Context context, ArrayList<String> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    //此方法 会根据索引返回ListView中第arg0个显示的列表
    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return list.get(arg0);
    }

    //此方法 根据索引返回id
    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    //每绘制一次条目就会调用一次这个方法,在此方法中指定绘制条目的样式,
    //并把要显示的相应数据添加到对应的条目控件中
    //postion 表示下一次将要绘制第几个条目
    //contextView 默认值为null,我们给它重新赋值,用来指定条目样式
    //并获取条目中所有控件,给响应属性赋值
    public View getView(int position, View contextView, ViewGroup parent) {
        // TODO Auto-generated method stub

        // 获取指定索引值的数据
        String string = list.get(position);

        if (contextView == null) {
            // 通过LayoutInflater 类的 from 方法 再 使用 inflate()方法得到指定的布局
            // 得到ListView中要显示的条目的布局
            LayoutInflater from = LayoutInflater.from(context);
            contextView = from.inflate(R.layout.mylayout1, null);
            // 从要显示的条目布局中 获得指定的组件
            Temp.tv = (TextView) contextView.findViewById(R.id.tasktime);
        }


        // 设置数值
        Temp.tv.setText(string);

        // 返回布局
        return contextView;
    }

    //静态内部类,保证不一直查找此对象(优化)
    static class Temp {
        static TextView tv;
    }

}
