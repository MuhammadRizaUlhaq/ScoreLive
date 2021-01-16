package com.example.scorelive.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.scorelive.dao.EventFavoriteDAO;
import com.example.scorelive.dao.TeamFavoriteDAO;
import com.example.scorelive.entity.EventFavorite;
import com.example.scorelive.entity.TeamFavorite;

@Database(entities = {TeamFavorite.class, EventFavorite.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TeamFavoriteDAO teamFavoriteDAO();
    public abstract EventFavoriteDAO eventFavoriteDAO();
}
