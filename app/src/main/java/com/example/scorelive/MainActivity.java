package com.example.scorelive;

import android.os.Bundle;
import android.util.Log;

import com.example.scorelive.data.EventsData;
import com.example.scorelive.database.DbFavorite;
import com.example.scorelive.model.Events;
import com.example.scorelive.notification.Notify;
import com.example.scorelive.rest.ApiClient;
import com.example.scorelive.rest.ApiInterface;
import com.example.scorelive.ui.games.GamesViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DbFavorite.getInstance(getApplicationContext());
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Notify.DEFAULT_CHANNEL_ID);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        Notify notify = Notify.getInstance(builder, notificationManager);
        notify.createNotificationChannel(Notify.DEFAULT_CHANNEL_ID, "Miscellaneous", "Miscellaneous");
        notify.createNotificationChannel(Notify.FAVORITE_CHANNEL_ID, "Favorite", "Favorite");

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_games, R.id.navigation_teams, R.id.navigation_favorites)
                .build();

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        GamesViewModel gamesViewModel = new ViewModelProvider(this).get(GamesViewModel.class);
        ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);
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