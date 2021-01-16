package com.example.scorelive.ui.teams;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scorelive.TeamDetailsActivity;
import com.example.scorelive.R;
import com.example.scorelive.adapter.ListTeamsAdapter;
import com.example.scorelive.data.TeamsData;
import com.example.scorelive.model.Teams;
import com.example.scorelive.rest.ApiClient;
import com.example.scorelive.rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamsFragment extends Fragment {

    private RecyclerView rvTeams;
    private TeamsViewModel teamsViewModel;
    private ListTeamsAdapter listTeamsAdapter;
    private ApiInterface mApiInterface;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_teams, container, false);
        teamsViewModel = new ViewModelProvider(this).get(TeamsViewModel.class);
        rvTeams = root.findViewById(R.id.rvTeamsList);
        rvTeams.setHasFixedSize(true);
        rvTeams.setLayoutManager(new LinearLayoutManager(getContext()));
        listTeamsAdapter = new ListTeamsAdapter(new ArrayList<>());
        rvTeams.setAdapter(listTeamsAdapter);
        return root;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        teamsViewModel.getTeamList().observe(getViewLifecycleOwner(), teamList -> {
            listTeamsAdapter.setTeams(teamList);
            listTeamsAdapter.notifyDataSetChanged();
            listTeamsAdapter.setOnItemClickCallback(teamIndex -> {
                Intent intent = new Intent(getActivity(), TeamDetailsActivity.class);
                intent.putExtra("teamIndex", teamIndex);
                startActivity(intent);
            });
        });

        if (TeamsData.teams.size() == 0) {
            mApiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<Teams> teamsCall = mApiInterface.getTeams();
            teamsCall.enqueue(new Callback<Teams>() {
                @Override
                public void onResponse(Call<Teams> call, Response<Teams> response) {
                    TeamsData.teams = response.body().getTeams();
                    Log.d("Retrofit Get", "Jumlah data team: " + TeamsData.teams.size());
                    teamsViewModel.setTeamList(TeamsData.teams);
                }

                @Override
                public void onFailure(Call<Teams> call, Throwable t) {
                    Log.e("Retrofit Get", t.toString());
                }
            });
        }

        teamsViewModel.setTeamList(TeamsData.teams);
    }
}