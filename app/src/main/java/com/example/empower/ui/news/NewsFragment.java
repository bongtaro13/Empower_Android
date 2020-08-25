package com.example.empower.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.empower.R;
import com.example.empower.entity.News;
import com.example.empower.entity.NewsWarehouse;

import java.util.List;

public class NewsFragment extends Fragment {

    private NewsViewModel newsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsViewModel =
                ViewModelProviders.of(this).get(NewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_news, container, false);


        List<News> newsList = NewsWarehouse.createNews(100);
        NewsAdapter newsAdapter = new NewsAdapter(getActivity().getApplicationContext(), newsList);



        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        RecyclerView recyclerView = root.findViewById(R.id.news_recyclerView);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(layoutManager);

        // add item decoration among different news item
        recyclerView.addItemDecoration(new NewsItemDecoration(2));


//        final TextView textView = root.findViewById(R.id.text_home);
//        newsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });






        return root;
    }
}