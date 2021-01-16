package com.example.scorelive.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scorelive.R;
import com.example.scorelive.model.Team;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListTeamsAdapter extends RecyclerView.Adapter<ListTeamsAdapter.ListViewHolder> {
    private List<Team> teams;
    private OnItemClickCallback onItemClickCallback;

    public ListTeamsAdapter(List<Team> teams) {
        this.teams = teams;
    }

    public List<Team> getTeams() { return teams; }
    public void setTeams(List<Team> teams) { this.teams = teams; }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_team, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Team team = teams.get(position);
        Picasso.get()
                .load(team.getTeamBadge())
                .fit()
                .centerCrop()
                .into(holder.imgTeam);

        holder.tvTeamTitle.setText(team.getName());
        holder.tvStadium.setText(team.getStadium());

        holder.itemView.setOnClickListener(v -> onItemClickCallback.onItemClicked(position));
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgTeam;
        TextView tvTeamTitle, tvStadium;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            imgTeam = itemView.findViewById(R.id.imgTeam_Item);
            tvTeamTitle = itemView.findViewById(R.id.tvTeamTitle_Item);
            tvStadium = itemView.findViewById(R.id.tvTeamStadium_Item);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(int teamIndex);
    }
}
