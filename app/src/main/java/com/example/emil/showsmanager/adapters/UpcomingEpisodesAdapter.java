package com.example.emil.showsmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.activities.SearchResultDetailsActivity;
import com.example.emil.showsmanager.models.SubscribedShow;


import java.util.List;


public class UpcomingEpisodesAdapter extends
        RecyclerView.Adapter<UpcomingEpisodesAdapter.UpcomingEpisodesViewHolder>{
    private List<SubscribedShow> showsList;
    private int rowLayout;
    private Context context;

    public UpcomingEpisodesAdapter(List<SubscribedShow> showsList, int rowLayout, Context context) {
        this.showsList = showsList;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    public List<SubscribedShow> getShowsList() {
        return showsList;
    }
    public void setShowsListResponse(List<SubscribedShow> showsList) {
        this.showsList = showsList;
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
    public UpcomingEpisodesAdapter.UpcomingEpisodesViewHolder onCreateViewHolder(ViewGroup parent,
                                                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(rowLayout, parent, false);
        return new UpcomingEpisodesAdapter.UpcomingEpisodesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UpcomingEpisodesAdapter.UpcomingEpisodesViewHolder holder, final int position) {
        String name = showsList.get(position).getName();
        final String id = showsList.get(position).getId();
        String imgUrl = showsList.get(position).getImageUrl();

        String daysToNextEp = showsList.get(position).getDaysToNextEpisode();
        holder.tvShowName.setText(name);
        holder.daysToNextEp.setText(daysToNextEp);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("CLICK");


                Intent intent = new Intent(context, SearchResultDetailsActivity.class);
                intent.putExtra("showId", id);

                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return showsList.size();
    }


    public static class UpcomingEpisodesViewHolder extends RecyclerView.ViewHolder {
        LinearLayout upcomingEpisodesLinearView;
        TextView daysToNextEp;
        TextView tvShowName;

        public UpcomingEpisodesViewHolder(View view) {
            super(view);
            //@TODO change this below
            upcomingEpisodesLinearView = (LinearLayout) view.findViewById(R.id.upcoming_episodes_item_layout);
            daysToNextEp = (TextView) view.findViewById(R.id.time_to_next_ep);
            tvShowName = (TextView) view.findViewById(R.id.show_name);
        }
    }
}