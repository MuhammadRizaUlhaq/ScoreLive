package com.example.scorelive.ui.teamdetails;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.scorelive.R;
import com.example.scorelive.model.Team;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeamDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamDetailsFragment extends Fragment {

    private TeamDetailsViewModel teamDetailsViewModel;

    public TeamDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TeamDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamDetailsFragment newInstance() {
        TeamDetailsFragment fragment = new TeamDetailsFragment();
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
        teamDetailsViewModel = (new ViewModelProvider(requireActivity()))
                                .get(TeamDetailsViewModel.class);
        return inflater.inflate(R.layout.fragment_team_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        teamDetailsViewModel.getTeam().observe(getViewLifecycleOwner(), new Observer<Team>() {
            @Override
            public void onChanged(@Nullable Team team) {
                if (team != null) {
                    TextView tvTeamFounded = view.findViewById(R.id.tvTeamFounded);
                    TextView tvTeamStadium = view.findViewById(R.id.tvTeamStadium);
                    TextView tvTeamStadiumLocation = view.findViewById(R.id.tvTeamStadiumLocation);
                    TextView tvTeamStadiumCapacity = view.findViewById(R.id.tvStadiumCapacity);
                    TextView tvTeamWebsite = view.findViewById(R.id.tvTeamWebsite);

                    tvTeamFounded.setText(String.valueOf(team.getFormedYear()));
                    tvTeamStadium.setText(team.getStadium());
                    tvTeamStadiumLocation.setText(team.getStadiumLocation());
                    String stadiumCapacity = String.format(
                            getString(R.string.value_stadium_capacity), team.getStadiumCapacity()
                    );
                    tvTeamStadiumCapacity.setText(stadiumCapacity);
                    tvTeamWebsite.setText(team.getWebsite());
                }
            }
        });
    }
}