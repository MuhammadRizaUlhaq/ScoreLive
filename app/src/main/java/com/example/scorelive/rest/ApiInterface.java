package com.example.scorelive.rest;

import com.example.scorelive.model.Teams;
import com.example.scorelive.model.Events;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("lookup_all_teams.php?id=4328")
    Call<Teams> getTeams();

    @GET("eventsnextleague.php?id=4328")
    Call<Events> getNextEvents();

    @GET("eventspastleague.php?id=4328")
    Call<Events> getPastEvents();
}
