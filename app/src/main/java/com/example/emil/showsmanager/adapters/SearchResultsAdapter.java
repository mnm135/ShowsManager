package com.example.emil.showsmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.models.SearchShowsResponse.ShowsListResponse;

import com.example.emil.showsmanager.view.ShowDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder> {

    private List<ShowsListResponse> showsListResponse;
    private int rowLayout;
    private Context context;

    public SearchResultsAdapter(List<ShowsListResponse> showsListResponse, int rowLayout, Context context) {
        this.showsListResponse = showsListResponse;
        this.rowLayout = rowLayout;
        this.context = context;
    }
    public List<ShowsListResponse> getShowsListResponse() {
        return showsListResponse;
    }
    public void setShowsListResponse(List<ShowsListResponse> showsListResponse) {
        this.showsListResponse = showsListResponse;
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
    public SearchResultsAdapter.SearchResultsViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(rowLayout, parent, false);
        return new SearchResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchResultsViewHolder holder, final int position) {
        String name = showsListResponse.get(position).getShow().getName();
        holder.tvShowName.setText(name);

        final String id = showsListResponse.get(position).getShow().getId().toString();

        if (showsListResponse.get(position).getShow().getImage() != null) {
            String pictureUrl = showsListResponse.get(position).getShow().getImage().getMedium();
            Picasso.with(getContext()).load(pictureUrl).into(holder.tvShowPoster);
        } else {
            Picasso.with(getContext())
                    .load(R.mipmap.ic_no_image)
                    .resize(200, 300)
                    .into(holder.tvShowPoster);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowDetailsActivity.class);
                intent.putExtra("showId", id);
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return showsListResponse.size();
    }

    public static class SearchResultsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout searchResultsLayout;
        ImageView tvShowPoster;
        TextView tvShowName;

        public SearchResultsViewHolder(View view) {
            super(view);
            searchResultsLayout = (LinearLayout) view.findViewById(R.id.search_result_item_layout);
            tvShowPoster = (ImageView) view.findViewById(R.id.show_poster);
            tvShowName = (TextView) view.findViewById(R.id.show_name);
        }
    }
}
