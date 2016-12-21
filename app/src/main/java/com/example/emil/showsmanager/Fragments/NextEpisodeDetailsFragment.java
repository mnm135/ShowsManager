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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    String episodeId;
    TextView text;

    public NextEpisodeDetailsFragment() {
        // Required empty public constructor
    }


    public static NextEpisodeDetailsFragment newInstance(String param1, String param2) {
        NextEpisodeDetailsFragment fragment = new NextEpisodeDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_episode_details, container, false);
        ButterKnife.bind(this, view);

        Bundle arguments = getArguments();
        episodeId = arguments.getString("episodeId");

        getNextEpData(episodeId);

        return view;

    }

    public void getNextEpData(String episodeId) {
        EpisodeEndPoints apiService = ApiClient.getClient().create(EpisodeEndPoints.class);
        Call<EpisodeResponse> call = apiService.getResponse(episodeId);

        call.enqueue(new Callback<EpisodeResponse>() {
            @Override
            public void onResponse(Call<EpisodeResponse> call,
                                   Response<EpisodeResponse> response) {

                Picasso.with(getContext())
                        .load(response.body().getImage().getOriginal())
                        .into(toolbarImage);
                episodeDescription.setText(response.body().getSummary());
                episodeName.setText(response.body().getName());
                episodeSeason.setText(response.body().getSeason().toString());
                episodeNumber.setText(response.body().getNumber().toString());
                episodeRuntime.setText(response.body().getRuntime().toString());
                episodeAirdate.setText(response.body().getRuntime().toString());
                episodeAirtime.setText(response.body().getAirtime());

            }

            @Override
            public void onFailure(Call<EpisodeResponse> call, Throwable t) {

            }
        });
    }
}

