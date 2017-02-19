package com.example.emil.showsmanager.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.activities.BaseActivity;
import com.example.emil.showsmanager.adapters.SeasonAdapter;
import com.example.emil.showsmanager.models.FullShowInfoResponse.SingleSeason;
import com.example.emil.showsmanager.presenter.SeasonPresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeasonActivity extends BaseActivity implements SeasonMvpView {

    SeasonPresenter presenter;

    @BindView(R.id.image_toolbar) ImageView toolbarImage;
    @BindView(R.id.season_description) TextView seasonDescription;
    @BindView(R.id.season_end_date) TextView seasonEndDate;
    @BindView(R.id.season_number) TextView seasonNumber;
    @BindView(R.id.season_premiere_date) TextView seasonPremiereDate;
    @BindView(R.id.season_number_of_episodes) TextView numberOfEpisods;

    @BindView(R.id.episodes_recycler)
    RecyclerView episodesRecyclerView;

    private RecyclerView.Adapter adapter;

    String mShowId;
    String mSeasonNumber;
    String mSeasonId;

    public SeasonActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.fragment_seasons, null, false);
        frameLayout.addView(contentView, 0);

        ButterKnife.bind(this);

        presenter = new SeasonPresenter();
        presenter.attachView(this);

        Intent intent = getIntent();
        mSeasonId = intent.getStringExtra("seasonId");
        mShowId = intent.getStringExtra("showId");

        presenter.loadSeasonInfo(mSeasonId);


    }

    public void showSeasonInfo(SingleSeason season) {
        if (season.getImage() != null) {
            Picasso.with(getContext())
                    .load(season.getImage().getOriginal())
                    .into(toolbarImage);
        }

        seasonDescription.setText(season.getSummary());
        seasonEndDate.setText(season.getEndDate());
        seasonPremiereDate.setText(season.getPremiereDate());
        seasonNumber.setText(String.valueOf(season.getNumber()));
        numberOfEpisods.setText(String.valueOf(season.getEpisodeOrder()));

        int numOfEpisodes = season.getEpisodeOrder();
        int seasonNum = season.getNumber();


        mSeasonNumber = String.valueOf(seasonNum);

        List<Integer> episodes = new ArrayList<Integer>();
        for (int i = 1; i < numOfEpisodes + 1; i++) {
            episodes.add(i);
        }
        episodesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SeasonAdapter(episodes, seasonNum, mShowId, R.layout.episodes_single_item, this);

        episodesRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

}
