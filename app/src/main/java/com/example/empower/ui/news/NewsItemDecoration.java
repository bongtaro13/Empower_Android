package com.example.empower.ui.news;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;



/**
 * Class name: NewsItemDecoration.java
 * function: This only aim of this class is to create the item decoration in the news list
 *              however, not worked as expected, this issue can be solved after the news list has been rewrite
 * */
public class NewsItemDecoration extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;


    public NewsItemDecoration(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.bottom = verticalSpaceHeight;

    }

}
