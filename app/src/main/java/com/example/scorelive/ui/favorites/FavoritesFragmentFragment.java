package com.example.scorelive.ui.favorites;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scorelive.R;
import com.example.scorelive.adapter.ListFavoritesAdapter;
import com.example.scorelive.database.AppDatabase;
import com.example.scorelive.database.DbFavorite;
import com.example.scorelive.entity.EventFavorite;
import com.example.scorelive.entity.TeamFavorite;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragmentFragment extends Fragment {
    private FavoritesViewModel favoritesViewModel;
    private RecyclerView rvFavorites;
    public FavoritesFragmentFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FavoritesFragment.
     */
    public static FavoritesFragmentFragment newInstance(int position) {
        FavoritesFragmentFragment fragment = new FavoritesFragmentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_fragment_favorites, container, false);
        rvFavorites = root.findViewById(R.id.rvFavoritesList);
        rvFavorites.setHasFixedSize(true);
        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getArguments() != null) {
            int position = getArguments().getInt("POSITION");
            if (position == 0) {
                ListFavoritesAdapter<EventFavorite> adapter = new ListFavoritesAdapter<>(
                        getContext(),
                        new ArrayList<>(),
                        R.layout.item_row_event
                );
                rvFavorites.setAdapter(adapter);

                setMatchesFavorite(root);
            } else {
                ListFavoritesAdapter<TeamFavorite> adapter = new ListFavoritesAdapter<>(
                        getContext(),
                        new ArrayList<>(),
                        R.layout.item_row_team
                );
                rvFavorites.setAdapter(adapter);

                setTeamsFavorite(root);
            }
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private static void setTeamsFavorite(View view) {
        new AsyncTask<Void, Void, List<TeamFavorite>>() {
            @Override
            protected List<TeamFavorite> doInBackground(Void... voids) {
                AppDatabase database = DbFavorite.getInstance().getDatabase();
                return database.teamFavoriteDAO().get();
            }

            @Override
            protected void onCancelled(List<TeamFavorite> teamFavorites) {
                super.onCancelled(teamFavorites);
                Toast.makeText(view.getContext(), "Cancelled Teams", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onPostExecute(List<TeamFavorite> teamFavorites) {
                ListFavoritesAdapter<TeamFavorite> adapter = new ListFavoritesAdapter<>(
                        view.getContext(),
                        teamFavorites,
                        R.layout.item_row_team
                );
                RecyclerView rvFavorites = view.findViewById(R.id.rvFavoritesList);
                rvFavorites.setAdapter(adapter);
            }
        }.execute();
    }

    private static void setMatchesFavorite(View view) {
        new AsyncTask<Void, Void, List<EventFavorite>>() {
            @Override
            protected List<EventFavorite> doInBackground(Void... voids) {
                AppDatabase database = DbFavorite.getInstance().getDatabase();
                return database.eventFavoriteDAO().get();
            }

            @Override
            protected void onCancelled(List<EventFavorite> eventFavorites) {
                super.onCancelled(eventFavorites);
                Toast.makeText(view.getContext(), "Cancelled Events", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onPostExecute(List<EventFavorite> eventFavorites) {
                ListFavoritesAdapter<EventFavorite> adapter = new ListFavoritesAdapter<>(
                        view.getContext(),
                        eventFavorites,
                        R.layout.item_row_event
                );
                RecyclerView rvFavorites = view.findViewById(R.id.rvFavoritesList);
                rvFavorites.setAdapter(adapter);
            }
        }.execute();
    }
}
