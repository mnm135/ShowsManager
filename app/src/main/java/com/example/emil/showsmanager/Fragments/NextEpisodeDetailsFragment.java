package com.example.emil.showsmanager.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.models.CastAndNextEpisode.EpisodeResponse;
import com.example.emil.showsmanager.rest.ApiClient;
import com.example.emil.showsmanager.rest.EpisodeEndPoints;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NextEpisodeDetailsFragment extends Fragment {



    @BindView(R.id.image_toolbar) ImageView toolbarImage;
    @BindView(R.id.episode_description) TextView episodeDescription;
    @BindView(R.id.episode_name) TextView episodeName;
    @BindView(R.id.episode_season) TextView episodeSeason;
    @BindView(R.id.episode_number) TextView episodeNumber;
    @BindView(R.id.episode_runtime) TextView episodeRuntime;
    @BindView(R.id.episode_airdate) TextView episodeAirdate;
    @BindView(R.id.episode_airtime) TextView episodeAirtime;


    public NextEpisodeDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_episode_details, container, false);
        ButterKnife.bind(this, view);

        Bundle arguments = getArguments();

        String showId = arguments.getString("showId");
        String epNumber = arguments.getString("episodeNumber");
        String seasonNumber = arguments.getString("seasonNumber");

        getNextEpData(showId, epNumber, seasonNumber);

        return view;
    }

    public void getNextEpData(String showId, String epNumber, String seasonNumber) {
        EpisodeEndPoints apiService = ApiClient.getClient().create(EpisodeEndPoints.class);
        Call<EpisodeResponse> call = apiService.getResponse(showId, seasonNumber, epNumber);
        call.enqueue(new Callback<EpisodeResponse>() {
            @Override
            public void onResponse(Call<EpisodeResponse> call,
                                   Response<EpisodeResponse> response) {

                if (response.body().getImage() != null) {
                    Picasso.with(getContext())
                            .load(response.body().getImage().getOriginal())
                            .into(toolbarImage);
                } else {
                    Picasso.with(getContext())
                            .load(R.mipmap.ic_no_image)
                            .into(toolbarImage);

                }

                String episodeDescriptionString = response.body().getSummary();
                String episodeNameString = response.body().getName();
                String episodeSeasonString = response.body().getSeason().toString();
                String episodeNumberString = response.body().getNumber().toString();
                String episodeRuntimeString = response.body().getRuntime().toString();
                String episodeAirdateString = response.body().getAirdate();
                String episodeAirtimeString = response.body().getAirtime();

                episodeDescription.setText(episodeDescriptionString);
                episodeName.setText(episodeNameString);
                episodeSeason.setText(episodeSeasonString);
                episodeNumber.setText(episodeNumberString);
                episodeRuntime.setText(episodeRuntimeString);
                episodeAirdate.setText(episodeAirdateString);
                episodeAirtime.setText(episodeAirtimeString);

            }
            @Override
            public void onFailure(Call<EpisodeResponse> call, Throwable t) {
            }
        });
    }
}

