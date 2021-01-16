package com.example.scorelive.ui.eventdetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.scorelive.data.EventsData;
import com.example.scorelive.model.Event;

public class EventDetailsViewModel extends ViewModel {

    private final MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private final LiveData<Event> mEvent = Transformations.map(mIndex, index -> EventsData.getCombinedEvents().get(index));

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<Event> getEvent() {
        return mEvent;
    }
}