package com.example.empower.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;



/**
 * Class name: NewsViewModel.java
 * function: main aim of this function is to create a viewModel of the newsFragment
 * not using in current iteration, will be extend in the final iteration
 * */

public class NewsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NewsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}