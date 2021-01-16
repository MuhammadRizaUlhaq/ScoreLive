package com.example.scorelive.ui.teams;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.scorelive.model.Team;

import java.util.List;


public class TeamsViewModel extends ViewModel {
    private final MutableLiveData<List<Team>> mTeamList = new MutableLiveData<>();

    public void setTeamList(List<Team> teams) {
        mTeamList.setValue(teams);
    }

    public LiveData<List<Team>> getTeamList() {
        return mTeamList;
    }
}