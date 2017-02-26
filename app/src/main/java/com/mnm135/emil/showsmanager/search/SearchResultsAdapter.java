package com.mnm135.emil.showsmanager.search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mnm135.emil.showsmanager.R;
import com.mnm135.emil.showsmanager.models.SearchShowsResponse.ShowsListResponse;

import com.mnm135.emil.showsmanager.showdetails.ShowDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchResultsViewHolder> {

    private List<ShowsListResponse> showsListResponse;
    private int rowLayout;
    private Context context;

    public SearchResultsAdapter(List<ShowsListResponse> showsListResponse, int rowLayout, Context context) {
        this.showsListResponse = showsListResponse;
        this.rowLayout = rowLayout;
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

        final String id = String.valueOf(showsListResponse.get(position).getShow().getId());

        if (showsListResponse.get(position).getShow().getImage() != null) {
            String pictureUrl = showsListResponse.get(position).getShow().getImage().getMedium();
            Picasso.with(context).load(pictureUrl).into(holder.tvShowPoster);
        } else {
            Picasso.with(context)
                    .load(R.mipmap.ic_no_image)
                    .resize(200, 300)
                    .into(holder.tvShowPoster);
        }

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ShowDetailsActivity.class);
            intent.putExtra("showId", id);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return showsListResponse.size();
    }

    public static class SearchResultsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.search_result_item_layout)
        LinearLayout searchResultsLayout;

        @BindView(R.id.show_poster)
        ImageView tvShowPoster;

        @BindView(R.id.show_name)
        TextView tvShowName;

        public SearchResultsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
