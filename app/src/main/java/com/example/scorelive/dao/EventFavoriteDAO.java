package com.example.scorelive.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.scorelive.entity.EventFavorite;

import java.util.List;

@Dao
public interface EventFavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(EventFavorite eventFavorite);

    @Query("SELECT * FROM eventfavorite")
    List<EventFavorite> get();
}
