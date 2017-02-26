package com.example.emil.showsmanager.episodedetails;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.base.BaseActivity;
import com.example.emil.showsmanager.models.FullShowInfoResponse.SingleEpisode;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EpisodeActivity extends BaseActivity implements EpisodeMvpView {

    private EpisodePresenter presenter = new EpisodePresenter();

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

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_episode, null, false);
        frameLayout.addView(contentView, 0);

        presenter.attachView(this);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String showId = intent.getStringExtra("showId");
        String episodeNumber = intent.getStringExtra("episodeNumber");
        String seasonNumber = intent.getStringExtra("seasonNumber");

        presenter.loadEpisodeData(showId, episodeNumber, seasonNumber);


    }

    public void showEpisodeData(SingleEpisode episode) {

        setToolbar(episode.getNumber().toString() + "x" + episode.getSeason().toString());

        if (episode.getImage() != null) {
            Picasso.with(getContext())
                    .load(episode.getImage().getOriginal())
                    .into(toolbarImage);
        } else {
            toolbarImage.setVisibility(View.GONE);
           // ResourcesCompat.getColor(getResources(), R.color.your_color, null);

            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimary)));

        }

        episodeDescription.setText(summaryFromHtml(episode.getSummary()));
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
