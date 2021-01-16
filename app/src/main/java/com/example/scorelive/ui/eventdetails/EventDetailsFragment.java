package com.example.scorelive.ui.eventdetails;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.scorelive.R;
import com.example.scorelive.model.Event;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventDetailsFragment extends Fragment {

    private EventDetailsViewModel eventDetailsViewModel;

    public EventDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EventDetailsFragment.
     */
    public static EventDetailsFragment newInstance() {
        EventDetailsFragment fragment = new EventDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        eventDetailsViewModel = (new ViewModelProvider(requireActivity()))
                                .get(EventDetailsViewModel.class);
        return inflater.inflate(R.layout.fragment_event_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvEventVideoLink = view.findViewById(R.id.tvEventDetailsVideoLink);
        tvEventVideoLink.setMovementMethod(LinkMovementMethod.getInstance());

        eventDetailsViewModel.getEvent().observe(getViewLifecycleOwner(), new Observer<Event>() {
            @Override
            public void onChanged(@Nullable Event event) {
                if (event != null) {
                    event.setHomeTeam();
                    event.setAwayTeam();

                    ImageView imgHomeTeam = view.findViewById(R.id.imgEventDetailsHomeTeam);
                    ImageView imgAwayTeam = view.findViewById(R.id.imgEventDetailsAwayTeam);
                    TextView tvEventHomeScore = view.findViewById(R.id.tvEventDetailsHomeScore);
                    TextView tvEventAwayScore = view.findViewById(R.id.tvEventDetailsAwayScore);
                    TextView tvEventVersus = view.findViewById(R.id.tvEventDetailsVersus);
                    TextView tvEventTime = view.findViewById(R.id.tvEventDetailsTime);
                    TextView tvEventDate = view.findViewById(R.id.tvEventDetailsDate);
                    TextView tvEventVenue = view.findViewById(R.id.tvEventDetailsVenue);
                    TextView tvEventSeason = view.findViewById(R.id.tvEventDetailsSeason);

                    Picasso.get()
                            .load(event.getHomeTeam().getTeamBadge())
                            .fit()
                            .centerCrop()
                            .into(imgHomeTeam);

                    Picasso.get()
                            .load(event.getAwayTeam().getTeamBadge())
                            .fit()
                            .centerCrop()
                            .into(imgAwayTeam);

                    if (event.getStatus().equals("Match Finished")) {
                        tvEventHomeScore.setText(String.valueOf(event.getHomeScore()));
                        tvEventAwayScore.setText(String.valueOf(event.getAwayScore()));
                    }

                    String versusText = String.format(
                            getResources().getString(R.string.value_event_versus),
                            event.getHomeTeam().getName(),
                            event.getAwayTeam().getName()
                    );
                    tvEventVersus.setText(versusText);

                    try {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
                        Date eventDate = format.parse(event.getTimestamp());

                        if (eventDate != null) {
                            format = new SimpleDateFormat(getResources().getString(R.string.value_event_date), Locale.getDefault());
                            String stringDate = format.format(eventDate);
                            format = new SimpleDateFormat(getResources().getString(R.string.value_event_time), Locale.getDefault());
                            String stringTime = format.format(eventDate);

                            tvEventDate.setText(stringDate);
                            tvEventTime.setText(stringTime);
                        }
                    } catch (ParseException e) {
                        Log.e(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace()));
                    }

                    tvEventVenue.setText(event.getVenue());
                    tvEventSeason.setText(event.getSeason());
                    tvEventVideoLink.setText(event.getVideoLink());
                }
            }
        });
    }
}