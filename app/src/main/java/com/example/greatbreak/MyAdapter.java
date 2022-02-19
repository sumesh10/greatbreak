package com.example.greatbreak;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

    ArrayList<Item> list;

    public MyAdapter(Context context, ArrayList<Item> list) {

        this.context = context;
        this.list = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_layout_order,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item item=list.get(position);
        holder.foodname.setText(item.getIname());
        holder.foodprice.setText(item.getIprice());
        Glide.with(holder.image.getContext()).load(item.getImageUrl()).into(holder.image);

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView foodname, foodprice;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);

            image=itemView.findViewById(R.id.imageView);

            foodname=itemView.findViewById(R.id.product_name);
            foodprice=itemView.findViewById(R.id.price);

        }
    }
}
