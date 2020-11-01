package com.example.mylife.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mylife.ListItem;
import com.example.mylife.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class billAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;

    private List<ListItem> bills = new ArrayList<>();
    HashMap<Integer, View> location =new HashMap<>();

    public billAdapter(Context context){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<ListItem> bills){
        this.bills = bills;
        notifyDataSetChanged();
    }

    @Override
    public int getCount(){return bills.size();}

    @Override
    public Object getItem(int position){return bills.get(position);}

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(location.get(position) == null){
            convertView = layoutInflater.inflate(R.layout.list_item,null);
            ListItem item = (ListItem) getItem(position);
            holder = new ViewHolder();
            holder.tvtime = convertView.findViewById(R.id.time1);
            //tvPrice = itemView.findViewById(R.id.tv_price);
            holder.tvincome = convertView.findViewById(R.id.income);
            //tvPhone = itemView.findViewById(R.id.tv_phone);
            holder.tvoutcome = convertView.findViewById(R.id.outcome);
            holder.tvtotal = convertView.findViewById(R.id.total);
            location.put(position,convertView);
            convertView.setTag(holder);
        }else{
            convertView = location.get(position);
            holder = (ViewHolder) convertView.getTag();
        }
        ListItem item = (ListItem)getItem(position);
        holder.tvtime.setText(item.getTime());
        holder.tvincome.setText(item.getIncome().toString());
        holder.tvoutcome.setText(item.getOutcome().toString());
        holder.tvtotal.setText(item.getTotal().toString());
        return convertView;
    }

    static class ViewHolder {
        //ImageView headimg;
        public TextView tvtime,tvincome,tvoutcome,tvtotal;
    }

}
