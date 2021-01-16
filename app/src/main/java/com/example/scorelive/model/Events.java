package com.example.scorelive.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Events {
    @SerializedName("events")
    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
