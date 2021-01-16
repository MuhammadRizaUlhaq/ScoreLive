package com.example.scorelive.data;

import android.os.Build;

import com.example.scorelive.model.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamsData {
    public static List<Team> teams = new ArrayList<>();

    public static Team findTeamById(int id) {
        if (Build.VERSION.SDK_INT < 24) {
            for (Team team: teams){
                if(team.getIdTeam() == id){
                    return team;
                }
            }

            return null;
        }

        return teams.stream().filter(team -> id == team.getIdTeam()).findFirst().orElse(null);
    }
}
