package com.example.emil.showsmanager.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.models.CastAndNextEpisode.ShowDetailsWithNextEpisodeResponse;
import com.example.emil.showsmanager.presenter.ShowDetailsPresenter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowDetailsActivity extends AppCompatActivity implements ShowDetailsMvpView {

    private ShowDetailsPresenter presenter;

    @BindView(R.id.show_name) TextView showName;
    @BindView(R.id.show_years_first_section) TextView showYears;
    @BindView(R.id.show_description) TextView showDescription;
    @BindView(R.id.show_poster) ImageView showPoster;
    @BindView(R.id.show_status) TextView showStatus;
    @BindView(R.id.show_number_of_season) TextView numberOfSeasons;
    @BindView(R.id.show_number_of_episodes) TextView numberOfEpisodes;
    @BindView(R.id.show_runtime) TextView showRuntime;
    @BindView(R.id.show_airdate) TextView showAirdate;
    @BindView(R.id.show_language) TextView showLanguage;
    @BindView(R.id.show_network) TextView showNetwork;
    @BindView(R.id.show_country) TextView showCountry;
    @BindView(R.id.genres) TextView showGenres;
    @BindView(R.id.imdb_pic) ImageView imdbPicture;
    @BindView(R.id.tvmaze_pic) ImageView tvmazePicture;
    @BindView(R.id.thetvdb_pic) ImageView thetvdbPicture;
    @BindView(R.id.next_episode_bar) TextView nextEpisodeBar;
    @BindView(R.id.fab_subscribe) FloatingActionButton fabSubscribe;
    @BindView(R.id.image_toolbar) ImageView toolbarImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new ShowDetailsPresenter();
        presenter.attachView(this);

        setContentView(R.layout.fragment_search_result_more_details);
        ButterKnife.bind(this);


        presenter.loadShow("73");

    }

    public void bindShowData(final ShowDetailsWithNextEpisodeResponse show) {
        showDescription.setText(show.getSummary());
        showGenres.setText(show.getGenres().toString());
        showCountry.setText(show.getNetwork().getCountry().getName());
        showNetwork.setText(show.getLanguage());
        showLanguage.setText(show.getLanguage());
        showAirdate.setText(show.getPremiered());
        showStatus.setText(show.getStatus());
        showName.setText(show.getName());

        Picasso.with(getContext())
                .load(show.getImage().getOriginal())
                .into(toolbarImage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public Context getContext() {
        return this;
    }




























}
