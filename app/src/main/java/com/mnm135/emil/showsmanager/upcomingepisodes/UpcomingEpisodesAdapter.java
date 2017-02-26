package com.mnm135.emil.showsmanager.upcomingepisodes;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.mnm135.emil.showsmanager.R;
import com.mnm135.emil.showsmanager.models.firebase.FirebaseShow;
import com.mnm135.emil.showsmanager.episodedetails.EpisodeActivity;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class UpcomingEpisodesAdapter extends
        RecyclerView.Adapter<UpcomingEpisodesAdapter.UpcomingEpisodesViewHolder>{
    private List<FirebaseShow> showsList;
    private int rowLayout;
    private Context context;

    public UpcomingEpisodesAdapter(List<FirebaseShow> showsList, int rowLayout, Context context) {
        this.showsList = showsList;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public UpcomingEpisodesAdapter.UpcomingEpisodesViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(rowLayout, parent, false);
        return new UpcomingEpisodesAdapter.UpcomingEpisodesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UpcomingEpisodesAdapter.UpcomingEpisodesViewHolder holder, final int position) {
        final String showId = showsList.get(position).getId();
        final String episodeNumber = showsList.get(position).getNextEpNumber();
        final String episodeSeason = showsList.get(position).getNextEpSeason();

        holder.tvShowName.setText(showsList.get(position).getName());
        holder.nextEpNetwork.setText(showsList.get(position).getChannel());
        holder.nextEpDate.setText(showsList.get(position).getNextEpisodeAirdate());
        holder.nextEpAirtime.setText(showsList.get(position).getAirtime());

        Integer daysToNextEp = Integer.valueOf(showsList.get(position).getDaysToNextEpisode());
        Resources res = context.getResources();

        if (daysToNextEp > 0) {
            String daysToNextEpString = res.getQuantityString(R.plurals.days_to_next_episode, daysToNextEp, daysToNextEp);
            holder.daysToNextEp.setText(daysToNextEpString);
        } else {
            holder.daysToNextEp.setText(res.getString(R.string.today_text));
        }

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, EpisodeActivity.class);
            intent.putExtra( "showId", showId);
            intent.putExtra( "episodeNumber", episodeNumber);
            intent.putExtra( "seasonNumber", episodeSeason);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return showsList.size();
    }

    public static class UpcomingEpisodesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.upcoming_episodes_item_layout)
        LinearLayout upcomingEpisodesLinearView;

        @BindView(R.id.time_to_next_ep)
        TextView daysToNextEp;

        @BindView(R.id.show_name)
        TextView tvShowName;

        @BindView(R.id.next_ep_airtime)
        TextView nextEpAirtime;

        @BindView(R.id.next_ep_date)
        TextView nextEpDate;

        @BindView(R.id.next_ep_network)
        TextView nextEpNetwork;

        public UpcomingEpisodesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}