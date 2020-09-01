//package com.example.empower.ui.news;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.empower.R;
//import com.example.empower.entity.News;
//
//import java.util.List;
//
//public class NewsItemAdapter extends RecyclerView.Adapter <NewsItemAdapter.ViewHolder> {
//
//    private List<News> mNewsList;
//
//    static class ViewHolder extends RecyclerView.ViewHolder{
//        ImageView newsImage;
//        TextView newsTitle;
//
//        public ViewHolder (View view){
//            super(view);
//            newsImage = (ImageView) view.findViewById(R.id.news_imageView);
//            newsTitle = (TextView) view.findViewById(R.id.news_item_title);
//        }
//    }
//
//
//    public NewsAdapter(List<News> newsList){
//        mNewsList = newsList;
//    }
//
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
//        ViewHolder holder = new ViewHolder(view);
//        return holder;
//    }
//
//    @Override
//}
