package com.example.emil.showsmanager.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.activities.BaseActivity;
import com.example.emil.showsmanager.models.FullShowInfoResponse.SingleEpisode;
import com.example.emil.showsmanager.presenter.EpisodePresenter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EpisodeActivity extends BaseActivity implements EpisodeMvpView {

    private EpisodePresenter presenter;

    @BindView(R.id.image_toolbar) ImageView toolbarImage;
    @BindView(R.id.episode_description) TextView episodeDescription;
    @BindView(R.id.episode_name) TextView episodeName;
    @BindView(R.id.episode_season) TextView episodeSeason;
    @BindView(R.id.episode_number) TextView episodeNumber;
    @BindView(R.id.episode_runtime) TextView episodeRuntime;
    @BindView(R.id.episode_airdate) TextView episodeAirdate;
    @BindView(R.id.episode_airtime) TextView episodeAirtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_episode_details);

        presenter = new EpisodePresenter();
        presenter.attachView(this);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String showId = intent.getStringExtra("showId");
        String episodeNumber = intent.getStringExtra("episodeNumber");
        String seasonNumber = intent.getStringExtra("seasonNumber");

        presenter.loadEpisodeData(showId, episodeNumber, seasonNumber);


    }

    public void showEpisodeData(SingleEpisode episode) {

        if (episode.getImage() != null) {
            Picasso.with(getContext())
                    .load(episode.getImage().getOriginal())
                    .into(toolbarImage);
        } else {
            Picasso.with(getContext())
                    .load(R.mipmap.ic_no_image)
                    .into(toolbarImage);

        }

        episodeDescription.setText(episode.getSummary());
        episodeName.setText(episode.getName());
        episodeSeason.setText(String.valueOf(episode.getSeason()));
        episodeNumber.setText(String.valueOf(episode.getNumber()));
        episodeRuntime.setText(String.valueOf(episode.getRuntime()));
        episodeAirdate.setText(episode.getAirdate());
        episodeAirtime.setText(episode.getAirtime());

    }

    @Override
    public Context getContext() {
        return this;
    }
}
