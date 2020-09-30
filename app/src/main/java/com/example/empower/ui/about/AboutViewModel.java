package com.example.empower.ui.about;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.empower.database.LikedVenue;
import com.example.empower.database.LikedVenueRepository;

import java.util.List;

public class AboutViewModel extends ViewModel {

   private LikedVenueRepository venueRepository;
   private MutableLiveData<List<LikedVenue>> allLikedVenue;

   public AboutViewModel (){
       allLikedVenue = new MutableLiveData<>();
   }


}