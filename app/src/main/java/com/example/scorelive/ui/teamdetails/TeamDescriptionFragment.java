package com.example.scorelive.ui.teamdetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.scorelive.R;
import com.example.scorelive.model.Team;

public class TeamDescriptionFragment extends Fragment {

    private TeamDetailsViewModel teamDetailsViewModel;

    public TeamDescriptionFragment() {}

    public static TeamDescriptionFragment newInstance() {
        TeamDescriptionFragment fragment = new TeamDescriptionFragment();
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        teamDetailsViewModel = (new ViewModelProvider(requireActivity())).get(TeamDetailsViewModel.class);
        return inflater.inflate(R.layout.fragment_team_description, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final TextView tvTeamDescription = view.findViewById(R.id.tv_teamDescription);

        teamDetailsViewModel.getTeam().observe(getViewLifecycleOwner(), new Observer<Team>() {
            @Override
            public void onChanged(@Nullable Team team) {
                if (team != null) {
                    tvTeamDescription.setText(team.getDescription());
                }
            }
        });
    }
}