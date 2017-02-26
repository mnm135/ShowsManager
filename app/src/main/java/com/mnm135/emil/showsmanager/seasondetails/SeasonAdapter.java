package com.mnm135.emil.showsmanager.seasondetails;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mnm135.emil.showsmanager.R;
import com.mnm135.emil.showsmanager.episodedetails.EpisodeActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeasonAdapter extends
        RecyclerView.Adapter<SeasonAdapter.SeasonViewHolder> {

    private List<Integer> episodes;
    private int rowLayout;
    private Context context;
    private int seasonNumber;
    private String showId;

    public SeasonAdapter(List<Integer> episodes, int seasonNumber, String showId, int rowLayout, Context context) {
        this.episodes = episodes;
        this.rowLayout = rowLayout;
        this.context = context;
        this.seasonNumber = seasonNumber;
        this.showId = showId;
    }

    @Override
    public SeasonAdapter.SeasonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(rowLayout, parent, false);
        return new SeasonAdapter.SeasonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SeasonAdapter.SeasonViewHolder holder, final int position) {

        String season = String.valueOf(seasonNumber);
        String episode = String.valueOf(episodes.get(position));

        Resources resources = context.getResources();
        String episodeAndSeason = String.format(resources.getString(R.string.episode_number_text), season, episode);

        holder.episodeNumber.setText(episodeAndSeason);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, EpisodeActivity.class);
            intent.putExtra( "showId", showId);
            intent.putExtra( "episodeNumber", season);
            intent.putExtra( "seasonNumber", episode);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    public static class SeasonViewHolder extends RecyclerView.ViewHolder {

        //@BindView(R.id.episodes_item_layout)
        //LinearLayout episodesLinearView;

        @BindView(R.id.card_view)
        CardView cardView;

        @BindView(R.id.episode_number)
        TextView episodeNumber;

        public SeasonViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}