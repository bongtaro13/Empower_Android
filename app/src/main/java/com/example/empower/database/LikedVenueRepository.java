package com.example.empower.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LikedVenueRepository {

    private LikedVenueDao dao;
    private LiveData<List<LikedVenue>> allLikedVeneu;
    private LikedVenue likedVenue;

    public LikedVenueRepository(Application application) {
        LikedVenueDatabase database = LikedVenueDatabase.getInstance(application);
        dao = database.likedVenueDao();
    }


    public LiveData<List<LikedVenue>> getAllLikedVeneu(){
        allLikedVeneu = dao.getAll();
        return allLikedVeneu;
    }


    public void insert(final LikedVenue likedVenue){
        LikedVenueDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(likedVenue);
            }
        });
    }

    public void insertAll(final LikedVenue... likedVenues){
        LikedVenueDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertALL(likedVenues);
            }
        });
    }

    public void deleteAll(){
        LikedVenueDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteAll();
            }
        });
    }

    public void delete(final LikedVenue likedVenue){
        LikedVenueDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(likedVenue);
            }
        });
    }

    public void updateLikedVenue(final LikedVenue... likedVenues){
        LikedVenueDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.updateLikedVenues(likedVenues);
            }
        });
    }

    public LikedVenue findByID(final int venueIndexID){
        LikedVenueDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                LikedVenue runLikedVeneu = dao.findByID(venueIndexID);
                setLikedVenue(runLikedVeneu);
            }
        });

        return likedVenue;
    }

    public void setLikedVenue(LikedVenue likedVenue){
        this.likedVenue = likedVenue;
    }

}
