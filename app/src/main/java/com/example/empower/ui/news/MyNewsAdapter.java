package com.example.empower.ui.news;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.empower.R;
import com.example.empower.entity.News;

import java.util.List;

public class MyNewsAdapter extends RecyclerView.Adapter<MyNewsAdapter.MyViewHolder> {


    private LayoutInflater mInflater;

    Context context;
    List<News> newsList;

    private OnItemClickListener onItemClickListener;


    public MyNewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
        mInflater = LayoutInflater.from(context);
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.news_item, parent, false);
        MyViewHolder  viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( final MyViewHolder holder, final int position) {
        News news = newsList.get(position);


        Glide.with(context)
                .load(news.getImageUrl())
                .into(holder.image);


        holder.title.setText(news.getTitle());

        //item click
        if (onItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout newsItem;      // get new recycle view item layout
        ImageView image;
        TextView title;
        String newsLink;

        public MyViewHolder(View itemView) {
            super(itemView);
            newsItem = itemView.findViewById(R.id.news_recyclerView);
            image = itemView.findViewById(R.id.news_imageView);
            title = itemView.findViewById(R.id.news_item_title);
        }
    }
}
