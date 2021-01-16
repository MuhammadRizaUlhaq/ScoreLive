package com.example.scorelive.ui.games;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scorelive.EventDetailsActivity;
import com.example.scorelive.R;
import com.example.scorelive.adapter.ListEventsAdapter;
import com.example.scorelive.data.EventsData;
import com.example.scorelive.model.Event;
import com.example.scorelive.model.Events;
import com.example.scorelive.rest.ApiClient;
import com.example.scorelive.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GamesFragment extends Fragment {

    private ApiInterface mApiInterface;
    private GamesViewModel gamesViewModel;
    private ListEventsAdapter listEventsAdapter;
    private RecyclerView rvEvents;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gamesViewModel =
                new ViewModelProvider(this).get(GamesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_games, container, false);
        rvEvents = root.findViewById(R.id.rvEventsList);
        rvEvents.setHasFixedSize(true);
        rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        listEventsAdapter = new ListEventsAdapter(getContext(), new ArrayList<>());
        rvEvents.setAdapter(listEventsAdapter);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gamesViewModel.getEventList().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable List<Event> eventList) {
                listEventsAdapter.setEvents(eventList);
                listEventsAdapter.notifyDataSetChanged();

                listEventsAdapter.setOnItemClickCallback(eventIndex -> {
                    Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
                    intent.putExtra("eventIndex", eventIndex);
                    startActivity(intent);
                });
            }
        });

        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        if (EventsData.pastEvents.size() == 0) {
            Call<Events> eventsCall = mApiInterface.getPastEvents();
            eventsCall.enqueue(new Callback<Events>() {
                @Override
                public void onResponse(Call<Events> call, Response<Events> response) {
                    EventsData.pastEvents = response.body().getEvents();
                    gamesViewModel.setEventList(EventsData.getCombinedEvents());
                }

                @Override
                public void onFailure(Call<Events> call, Throwable t) {
                    Log.e("Retrofit Get", t.toString());
                }
            });
        }

        if (EventsData.nextEvents.size() == 0) {
            Call<Events> eventsCall = mApiInterface.getNextEvents();
            eventsCall.enqueue(new Callback<Events>() {
                @Override
                public void onResponse(Call<Events> call, Response<Events> response) {
                    EventsData.nextEvents = response.body().getEvents();
                    gamesViewModel.setEventList(EventsData.getCombinedEvents());
                }

                @Override
                public void onFailure(Call<Events> call, Throwable t) {
                    Log.e("Retrofit Get", t.toString());
                }
            });
        }

        gamesViewModel.setEventList(EventsData.getCombinedEvents());
    }
}