package com.example.xuh.shopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xuh.shopping.R;
import com.example.xuh.shopping.bean.Student;
import com.example.xuh.shopping.bean.designer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;

    private List<Student> Students = new ArrayList<>();
    //对每一个item保存其位置
    HashMap<Integer, View> location = new HashMap<>();

    public StudentAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<Student> Students) {
        this.Students = Students;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return Students.size();
    }

    @Override
    public Object getItem(int position) {
        return Students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StudentAdapter.ViewHolder holder = null;
        if(location.get(position) == null){
            convertView = layoutInflater.inflate(R.layout.designers,null);
            Student studentm = (Student) getItem(position);
            holder = new StudentAdapter.ViewHolder(convertView,studentm);
            //保存view的位置position
            location.put(position,convertView);
            convertView.setTag(holder);
        }else{
            convertView = location.get(position);
            holder = (StudentAdapter.ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    //定义静态类,包含每一个item的所有元素
    static class ViewHolder {
        TextView tvchenghao,tvxuehao,tvname,tvmajor;

        public ViewHolder(View itemView,Student students) {
            tvchenghao = itemView.findViewById(R.id.chenghao);
            tvxuehao = itemView.findViewById(R.id.myname);
            //tvPrice = itemView.findViewById(R.id.tv_price);
            tvname = itemView.findViewById(R.id.myphone);
            tvmajor = itemView.findViewById(R.id.myaddress);
            tvchenghao.setText(students.getStuName());
            tvxuehao.setText(students.getStuNumber());
            tvname.setText(students.getStuName());
            tvmajor.setText(students.getStuMajor());
            //tvPrice.setText(String.valueOf(commodity.getPrice())+"元");
        }
    }
}
