package com.example.scorelive.ui.games;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.scorelive.model.Event;

import java.util.List;

public class GamesViewModel extends ViewModel {

    private final MutableLiveData<List<Event>> mEventList = new MutableLiveData<>();

    public GamesViewModel() {
    }

    public LiveData<List<Event>> getEventList() {
        return mEventList;
    }

    public void setEventList(List<Event> eventList) {
        mEventList.setValue(eventList);
    }
}