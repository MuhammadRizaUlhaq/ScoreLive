package com.example.scorelive.ui.teamdetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.scorelive.data.TeamsData;
import com.example.scorelive.model.Team;

public class TeamDetailsViewModel extends ViewModel {

    private final MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private final LiveData<Team> mTeam = Transformations.map(mIndex, index -> TeamsData.teams.get(index));

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<Team> getTeam() {
        return mTeam;
    }
}