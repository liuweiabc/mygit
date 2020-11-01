package com.example.xuh.shopping;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class goodsAdapter extends RecyclerView.Adapter<goodsAdapter.ViewHolder> {

    private List<goodsModel> mlist;
    goodsAdapter(List<goodsModel> mlist){
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.commodityitem,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text.setText(mlist.get(position).getContent());
        holder.image.setImageResource(mlist.get(position).getImageId());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView text;
        public ViewHolder(View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.goodspic);
            text = itemView.findViewById(R.id.introduction);
        }
    }
}
