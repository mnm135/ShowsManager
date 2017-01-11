package com.example.emil.showsmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.activities.SearchResultDetailsActivity;
import com.example.emil.showsmanager.models.ShowsListResponse;
import com.example.emil.showsmanager.models.SubscribedShow;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Emil on 02.01.2017.
 */

public class SubscribedShowsAdapter extends
        RecyclerView.Adapter<SubscribedShowsAdapter.SubscribedShowsViewHolder> {
    private List<SubscribedShow> showsList;
    private int rowLayout;
    private Context context;

    public SubscribedShowsAdapter(List<SubscribedShow> showsList, int rowLayout, Context context) {
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
    public SubscribedShowsAdapter.SubscribedShowsViewHolder onCreateViewHolder(ViewGroup parent,
                                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(rowLayout, parent, false);
        return new SubscribedShowsAdapter.SubscribedShowsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubscribedShowsAdapter.SubscribedShowsViewHolder holder, final int position) {
        String name = showsList.get(position).getName();
        final String id = showsList.get(position).getId();
        String imgUrl = showsList.get(position).getImageUrl();

        holder.tvShowName.setText(name);
        Picasso.with(getContext()).load(imgUrl).into(holder.tvShowPoster);



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


    public static class SubscribedShowsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout subscribedShowsCardView;
        ImageView tvShowPoster;
        TextView tvShowName;

        public SubscribedShowsViewHolder(View view) {
            super(view);
            //@TODO change this below
            subscribedShowsCardView = (LinearLayout) view.findViewById(R.id.subscribed_shows_item_layout);
            tvShowPoster = (ImageView) view.findViewById(R.id.show_poster);
            tvShowName = (TextView) view.findViewById(R.id.show_name);
        }
    }
}
