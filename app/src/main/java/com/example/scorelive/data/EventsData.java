package com.example.scorelive.data;

import android.os.Build;

import com.example.scorelive.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventsData {
    public static List<Event> nextEvents = new ArrayList<>();
    public static List<Event> pastEvents = new ArrayList<>();

    public static Event findEventById(int id) {
        List<Event> events = getCombinedEvents();
        if (Build.VERSION.SDK_INT < 24) {
            for (Event event: events){
                if(event.getIdEvent() == id){
                    return event;
                }
            }

            return null;
        }

        return events.stream().filter(event -> id == event.getIdEvent()).findFirst().orElse(null);
    }

    public static List<Event> getCombinedEvents() {
        List<Event> events = new ArrayList<>();
        events.addAll(nextEvents);
        events.addAll(pastEvents);
        return events;
    }
}
