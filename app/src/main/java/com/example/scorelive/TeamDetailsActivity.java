package com.example.scorelive;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.scorelive.data.TeamsData;
import com.example.scorelive.database.AppDatabase;
import com.example.scorelive.database.DbFavorite;
import com.example.scorelive.entity.TeamFavorite;
import com.example.scorelive.model.Team;
import com.example.scorelive.notification.Notify;
import com.example.scorelive.ui.favorites.FavoritesFragment;
import com.example.scorelive.ui.teamdetails.TeamDetailsViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;

import com.example.scorelive.ui.teamdetails.TeamDetailsPagerAdapter;
import com.squareup.picasso.Picasso;

public class TeamDetailsActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);
        toolbar = findViewById(R.id.toolbarTeamDetails);
        setSupportActionBar(toolbar);

        int mTeamIndex = getIntent().getIntExtra("teamIndex", 0);

        TeamDetailsPagerAdapter teamDetailsPagerAdapter = new TeamDetailsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPagerTeamDetails);
        viewPager.setAdapter(teamDetailsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabsTeamDetails);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fabFavorite = findViewById(R.id.fabTeamFavorite);

        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                TeamFavorite teamFavorite = new TeamFavorite();
                Team team = TeamsData.teams.get(mTeamIndex);
                teamFavorite.setIdTeam(team.getIdTeam());
                insertFavoriteTeam(teamFavorite, getApplicationContext());
            }
        });

        TeamDetailsViewModel teamDetailsViewModel = new ViewModelProvider(this)
                                                    .get(TeamDetailsViewModel.class);

        teamDetailsViewModel.getTeam().observe(this, new Observer<Team>() {
            @Override
            public void onChanged(@Nullable Team team) {
                if (team != null) {
                    CollapsingToolbarLayout toolbarLayout = findViewById(R.id.collapsingToolbarTeamDetails);
                    ImageView imgHeader = findViewById(R.id.imgHeaderTeamDetails);
                    toolbarLayout.setTitle(team.getName());
                    Picasso.get()
                            .load(team.getStadiumThumb())
                            .fit()
                            .centerCrop()
                            .into(imgHeader);
                }
            }
        });

        teamDetailsViewModel.setIndex(mTeamIndex);
    }

    private static void insertFavoriteTeam(TeamFavorite teamFavorite, Context context) {
        Notify notify = Notify.getInstance();
        notify.getBuilder().setChannelId(Notify.FAVORITE_CHANNEL_ID);
        AppDatabase database = DbFavorite.getInstance().getDatabase();

        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                return database.teamFavoriteDAO().insert(teamFavorite);
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