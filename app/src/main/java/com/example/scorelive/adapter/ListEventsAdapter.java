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
import com.example.scorelive.model.Event;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListEventsAdapter extends RecyclerView.Adapter<ListEventsAdapter.ListViewHolder> {
    private final Context context;
    private List<Event> events;
    private ListEventsAdapter.OnItemClickCallback onItemClickCallback;

    public ListEventsAdapter(Context context, List<Event> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public ListEventsAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_event, parent, false);
        return new ListEventsAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListEventsAdapter.ListViewHolder holder, int position) {
        Event event = events.get(position);

        if (event == null) return;

        event.setHomeTeam();
        event.setAwayTeam();

        if (event.getHomeTeam() == null && event.getAwayTeam() == null) return;

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

        String versusText = String.format(
                context.getResources().getString(R.string.value_event_versus),
                event.getHomeTeam().getName(),
                event.getAwayTeam().getName()
        );

        holder.tvEventVersus.setText(versusText);

        if (event.getStatus().equals("Match Finished")) {
            holder.tvEventHomeScore.setText(String.valueOf(event.getHomeScore()));
            holder.tvEventAwayScore.setText(String.valueOf(event.getAwayScore()));
        } else {
            holder.tvEventHomeScore.setText(context.getResources().getString(R.string.text_dash));
            holder.tvEventAwayScore.setText(context.getResources().getString(R.string.text_dash));
        }

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

        holder.itemView.setOnClickListener(v -> onItemClickCallback.onItemClicked(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setOnItemClickCallback(ListEventsAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public List<Event> getEvents() { return events; }

    public void setEvents(List<Event> eventList) {
        this.events = eventList;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHomeTeam, imgAwayTeam;
        TextView tvEventVersus, tvEventTime, tvEventDate, tvEventHomeScore, tvEventAwayScore;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            imgHomeTeam = itemView.findViewById(R.id.imgEventHomeTeam_Item);
            imgAwayTeam = itemView.findViewById(R.id.imgEventAwayTeam_Item);
            tvEventHomeScore = itemView.findViewById(R.id.tvEventHomeScore_Item);
            tvEventAwayScore = itemView.findViewById(R.id.tvEventAwayScore_Item);
            tvEventVersus = itemView.findViewById(R.id.tvEventVersus_Item);
            tvEventTime = itemView.findViewById(R.id.tvEventTime_Item);
            tvEventDate = itemView.findViewById(R.id.tvEventDate_Item);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(int eventIndex);
    }
}
