package com.example.emil.showsmanager.adapters;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.emil.showsmanager.Fragments.NextEpisodeDetailsFragment;
import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.models.firebase.FirebaseShow;

import java.util.List;

public class SeasonAdapter extends
        RecyclerView.Adapter<SeasonAdapter.SeasonViewHolder> {

    private List<Integer> episodes;
    private int rowLayout;
    private Context context;
    private int seasonNumber;

    public SeasonAdapter(List<Integer> episodes, int seasonNumber, int rowLayout, Context context) {
        this.episodes = episodes;
        this.rowLayout = rowLayout;
        this.context = context;
        this.seasonNumber = seasonNumber;
    }

    public List<Integer> getEpisodes() {
        return episodes;
    }
    public void setShowsListResponse(List<Integer> showsList) {
        this.episodes = showsList;
    }
    public int getRowLayout() {
        return rowLayout;
    }

    public void setRowLayout(int rowLayout) {
        this.rowLayout = rowLayout;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
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
        String text = String.format(resources.getString(R.string.episode_number_text), season, episode);


        holder.episodeNumber.setText(text);


     /*   final String nextEpNumber = episodes.get(position).getNextEpNumber();
        final String nextEpSeason = episodes.get(position).getNextEpSeason();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new NextEpisodeDetailsFragment();
                Bundle arguments = new Bundle();

                arguments.putString( "showId", id);
                arguments.putString( "episodeNumber", nextEpSeason);
                arguments.putString( "seasonNumber", nextEpNumber);

                newFragment.setArguments(arguments);

                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                transaction.replace(R.id.container, newFragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }


    public static class SeasonViewHolder extends RecyclerView.ViewHolder {
        LinearLayout episodesLinearView;
        TextView episodeNumber;

        public SeasonViewHolder(View view) {
            super(view);
            //@TODO change this below
            episodesLinearView = (LinearLayout) view.findViewById(R.id.episodes_item_layout);
            episodeNumber = (TextView) view.findViewById(R.id.episode_number);
        }
    }
}