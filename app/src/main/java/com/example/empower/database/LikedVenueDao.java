package com.example.empower.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface LikedVenueDao {

    @Query("SELECT * FROM LikedVenue")
    LiveData<List<LikedVenue>> getAll();


    @Query("SELECT * FROM LikedVenue WHERE venueID = :venueID LiMIT 1")
    LikedVenue findByVenueID(String venueID);


    @Insert
    void insertALL(LikedVenue... likedVeneuses);

    @Insert
    long insert(LikedVenue likedVenue);

    @Delete
    void delete(LikedVenue likedVenue);

    @Update
    void updateLikedVenues(LikedVenue... likedVeneuses);

    @Query("DELETE FROM LikedVenue")
    void deleteAll();

}
