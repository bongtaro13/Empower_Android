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



/**
 * class name: MyNewsAdapter
 * function: manage the recycle with each item, integrated every single item to the news list
 * */

public class MyNewsAdapter extends RecyclerView.Adapter<MyNewsAdapter.MyViewHolder> {


    private LayoutInflater mInflater;

    Context context;
    List<News> newsList;

    private OnItemClickListener onItemClickListener;

    // constructor of the MyNewsAdapter
    public MyNewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
        mInflater = LayoutInflater.from(context);
    }



    // setting view holder in the adapter
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.news_item, parent, false);
        MyViewHolder  viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    // bing view holder with elements in the MyViewHolder
    @Override
    public void onBindViewHolder( final MyViewHolder holder, final int position) {
        News news = newsList.get(position);

        // transfer img URL to image display in the adapter
        Glide.with(context)
                .load(news.getImageUrl())
                .into(holder.image);


        holder.title.setText(news.getTitle());

        //item click listener
        if (onItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }


    }


    // count the size of item list
    @Override
    public int getItemCount() {
        return newsList.size();
    }

    // setting a listen to get operator from user
    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    // setting item click listener with interface include
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }



    // constructor of every single item in the adapter with managed by viewHolder
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
