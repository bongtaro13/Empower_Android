package com.example.empower.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {LikedVenue.class}, version = 2, exportSchema = false)
public abstract class LikedVenueDatabase extends RoomDatabase {

    public abstract LikedVenueDao likedVenueDao();

    private static LikedVenueDatabase INSTANCE;

    public static synchronized LikedVenueDatabase getINSTANCE(final Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    LikedVenueDatabase.class, "LikedVenueDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

}
