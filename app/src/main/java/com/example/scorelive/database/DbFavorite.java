package com.example.scorelive.database;

import android.content.Context;

import androidx.room.Room;

public class DbFavorite {
    private static DbFavorite instance;
    private AppDatabase database;
    private DbFavorite(Context context) {
        database = Room.databaseBuilder(
                context,
                AppDatabase.class,
                "dbFavorite")
                .build();
    }

    public static DbFavorite getInstance(Context context) {
        if (instance == null) {
            instance = new DbFavorite(context);
        }

        return instance;
    }

    public static DbFavorite getInstance() {
        return instance;
    }

    public AppDatabase getDatabase(){
        return database;
    }
}
