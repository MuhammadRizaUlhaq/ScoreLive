package com.example.scorelive.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scorelive.R;
import com.example.scorelive.data.EventsData;
import com.example.scorelive.data.TeamsData;
import com.example.scorelive.entity.EventFavorite;
import com.example.scorelive.entity.TeamFavorite;
import com.example.scorelive.model.Event;
import com.example.scorelive.model.Team;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListFavoritesAdapter<T> extends RecyclerView.Adapter<ListFavoritesAdapter.ListViewHolder> {
    private final Context context;
    private final int layoutItemId;
    private final List<T> list;

    public ListFavoritesAdapter(Context context, List<T> list, int layoutItemId) {
        this.context = context;
        this.list = list;
        this.layoutItemId = layoutItemId;
    }

    @NonNull
    @Override
    public ListFavoritesAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutItemId, parent, false);
        return new ListFavoritesAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFavoritesAdapter.ListViewHolder holder, int position) {
        T data = list.get(position);

        if (data.getClass().equals(EventFavorite.class)) {
            EventFavorite eventFavorite = (EventFavorite) data;
            Event event = EventsData.findEventById(eventFavorite.getIdEvent());

            if (event == null) return;

            event.setHomeTeam();
            event.setAwayTeam();

            Picasso.get()
                    .load(event.getHomeTeam().getTeamBadge())
                    .fit()
                    .centerCrop()
                    .into(holder.imgHomeTeam);

            Picasso.get()
                    .load(event.getAwayTeam().getTeamBadge())
                    .fit()
                    .centerCrop()
                    .into(holder.imgAwayTeam);

            if (event.getStatus().equals("Match Finished")) {
                holder.tvEventHomeScore.setText(String.valueOf(event.getHomeScore()));
                holder.tvEventAwayScore.setText(String.valueOf(event.getAwayScore()));
            }

            String versusText = String.format(
                    context.getResources().getString(R.string.value_event_versus),
                    event.getHomeTeam().getName(),
                    event.getAwayTeam().getName()
            );
            holder.tvEventVersus.setText(versusText);
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
                Date eventDate = format.parse(event.getTimestamp());

                if (eventDate != null) {
                    format = new SimpleDateFormat(context.getResources().getString(R.string.value_event_date), Locale.getDefault());
                    String stringDate = format.format(eventDate);
                    format = new SimpleDateFormat(context.getResources().getString(R.string.value_event_time), Locale.getDefault());
                    String stringTime = format.format(eventDate);

                    holder.tvEventDate.setText(stringDate);
                    holder.tvEventTime.setText(stringTime);
                }
            } catch (ParseException e) {
                Log.e(e.getClass().getSimpleName(), Arrays.toString(e.getStackTrace()));
            }
        } else if(data.getClass().equals(TeamFavorite.class)) {
            TeamFavorite teamFavorite = (TeamFavorite) data;
            Team team = TeamsData.findTeamById(teamFavorite.getIdTeam());

            if (team == null) return;

            Picasso.get()
                    .load(team.getTeamBadge())
                    .fit()
                    .centerCrop()
                    .into(holder.imgTeam);

            holder.tvTeamTitle.setText(team.getName());
            holder.tvTeamStadium.setText(team.getStadium());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHomeTeam, imgAwayTeam, imgTeam;
        TextView tvEventVersus, tvEventTime, tvEventDate, tvEventHomeScore, tvEventAwayScore, tvTeamTitle, tvTeamStadium;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            imgHomeTeam = itemView.findViewById(R.id.imgEventHomeTeam_Item);
            imgAwayTeam = itemView.findViewById(R.id.imgEventAwayTeam_Item);
            tvEventHomeScore = itemView.findViewById(R.id.tvEventHomeScore_Item);
            tvEventAwayScore = itemView.findViewById(R.id.tvEventAwayScore_Item);
            tvEventVersus = itemView.findViewById(R.id.tvEventVersus_Item);
            tvEventTime = itemView.findViewById(R.id.tvEventTime_Item);
            tvEventDate = itemView.findViewById(R.id.tvEventDate_Item);

            imgTeam = itemView.findViewById(R.id.imgTeam_Item);
            tvTeamTitle = itemView.findViewById(R.id.tvTeamTitle_Item);
            tvTeamStadium = itemView.findViewById(R.id.tvTeamStadium_Item);
        }
    }

}
