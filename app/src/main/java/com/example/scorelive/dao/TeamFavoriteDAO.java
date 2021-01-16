package com.example.scorelive.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.scorelive.entity.TeamFavorite;

import java.util.List;

@Dao
public interface TeamFavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(TeamFavorite teamFavorite);

    @Query("SELECT * FROM teamfavorite")
    List<TeamFavorite> get();
}
