package com.example.emil.showsmanager.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.models.CastAndNextEpisode.NextEpisodeResponse;
import com.example.emil.showsmanager.models.CastAndNextEpisode.ShowDetailsWithNextEpisodeResponse;
import com.example.emil.showsmanager.rest.ApiClient;
import com.example.emil.showsmanager.rest.NextEpisodeEndPoints;
import com.example.emil.showsmanager.rest.ShowDetailsEndPoints;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NextEpisodeDetailsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    String showId;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_next_episode_details, container, false);

        Bundle arguments = getArguments();
        showId = arguments.getString("showId");
        text = (TextView) view.findViewById(R.id.text);

        getNextEpData(showId);



        return view;

    }

    public void getNextEpData(String showId) {




        NextEpisodeEndPoints apiService = ApiClient.getClient().create(NextEpisodeEndPoints.class);
        Call<NextEpisodeResponse> call = apiService.getResponse(showId, "nextepisode");

        call.enqueue(new Callback<NextEpisodeResponse>() {
            @Override
            public void onResponse(Call<NextEpisodeResponse> call,
                                   Response<NextEpisodeResponse> response) {

                text.setText(response.body().getEmbedded().getNextepisode().getAirdate());





            }

            @Override
            public void onFailure(Call<NextEpisodeResponse> call, Throwable t) {

            }
        });
    }
}

