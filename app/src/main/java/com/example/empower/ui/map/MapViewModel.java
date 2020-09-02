package com.example.empower.ui.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;



/**
 * Class name: MapViewModel.java
 * function: main aim of this function is to create a viewModel of the mapFragment
 * not using in current iteration, will be extend in the final iteration
 * */
public class MapViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}