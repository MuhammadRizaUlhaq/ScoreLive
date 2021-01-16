package com.example.scorelive;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.scorelive.data.EventsData;
import com.example.scorelive.database.AppDatabase;
import com.example.scorelive.database.DbFavorite;
import com.example.scorelive.entity.EventFavorite;
import com.example.scorelive.model.Event;
import com.example.scorelive.notification.Notify;
import com.example.scorelive.ui.eventdetails.EventDetailsPagerAdapter;
import com.example.scorelive.ui.eventdetails.EventDetailsViewModel;
import com.example.scorelive.ui.favorites.FavoritesFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class EventDetailsActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        toolbar = findViewById(R.id.toolbarEventDetails);
        setSupportActionBar(toolbar);

        int mEventIndex = getIntent().getIntExtra("eventIndex", 0);

        EventDetailsPagerAdapter eventDetailsPagerAdapter = new EventDetailsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPagerEventDetails);
        viewPager.setAdapter(eventDetailsPagerAdapter);
//        TabLayout tabs = findViewById(R.id.tabsEventDetails);
//        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fabEventFavorite = findViewById(R.id.fabEventFavorite);

        fabEventFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event event = EventsData.getCombinedEvents().get(mEventIndex);
                EventFavorite eventFavorite = new EventFavorite();
                eventFavorite.setIdEvent(event.getIdEvent());
                insertFavoriteEvent(eventFavorite, getApplicationContext());
            }
        });

        EventDetailsViewModel eventDetailsViewModel = new ViewModelProvider(this)
                                                    .get(EventDetailsViewModel.class);

        eventDetailsViewModel.getEvent().observe(this, new Observer<Event>() {
            @Override
            public void onChanged(@Nullable Event event) {
                if (event != null) {
                    ImageView imgHeader = findViewById(R.id.imgHeaderEventDetails);
                    Picasso.get()
                            .load(event.getThumbnail())
                            .fit()
                            .centerCrop()
                            .into(imgHeader);
                }
            }
        });

        eventDetailsViewModel.setIndex(mEventIndex);
    }

    private static void insertFavoriteEvent(EventFavorite eventFavorite, Context context) {
        Notify notify = Notify.getInstance();
        notify.getBuilder().setChannelId(Notify.FAVORITE_CHANNEL_ID);
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                AppDatabase database = DbFavorite.getInstance().getDatabase();
                return database.eventFavoriteDAO().insert(eventFavorite);
            }

            @Override
            protected void onCancelled() {
//                Toast.makeText(context, "Failed to add", Toast.LENGTH_SHORT).show();
                notify.now("Failed", "Failed to add to Favorites");
            }

            @Override
            protected void onPostExecute(Long status) {
                notify.now(
                        "Success",
                        "Success add to Favorites",
                        context,
                        new Intent(context, FavoritesFragment.class)
                );
//                Toast.makeText(context, "Status Row " + status, Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}