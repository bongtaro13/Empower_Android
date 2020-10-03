package com.example.empower.ui.about;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.empower.database.LikedVenue;
import com.example.empower.database.LikedVenueRepository;

import java.util.List;

public class AboutViewModel extends ViewModel {

    private LikedVenueRepository venueRepository;
    private MutableLiveData<List<LikedVenue>> allLikedVenue;

    public AboutViewModel() {
        allLikedVenue = new MutableLiveData<>();
    }


    public void setLikedVenues(List<LikedVenue> likedVenues) {
        allLikedVenue.setValue(likedVenues);
    }

    public LiveData<List<LikedVenue>> getAllLikedVenues() {
        return venueRepository.getAllLikedVeneu();
    }

    public void initalizeVars(Application application) {
        venueRepository = new LikedVenueRepository(application);
    }

    public void insert(LikedVenue likedVenue) {
        venueRepository.insert(likedVenue);
    }

    public void insertAll(LikedVenue... likedVenues) {
        venueRepository.insertAll(likedVenues);
    }

    public void delete(LikedVenue likedVenue) {
        venueRepository.delete(likedVenue);
    }

    public void deleteAll() {
        venueRepository.deleteAll();
    }

    public void update(LikedVenue... likedVenues) {
        venueRepository.updateLikedVenue(likedVenues);
    }

    public LikedVenue findByID(int venueIndexID) {
        return venueRepository.findByID(venueIndexID);
    }

    public LikedVenue findByVenueID(String venueID){
        return venueRepository.findByVenueID(venueID);
    }


}