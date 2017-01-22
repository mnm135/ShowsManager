package com.example.emil.showsmanager.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.models.CastAndNextEpisode.SeasonResponse;
import com.example.emil.showsmanager.rest.ApiClient;
import com.example.emil.showsmanager.rest.SeasonEndPoints;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeasonsFragment extends ListFragment {

    @BindView(R.id.image_toolbar) ImageView toolbarImage;
    @BindView(R.id.season_description) TextView seasonDescription;
    @BindView(R.id.season_end_date) TextView seasonEndDate;
    @BindView(android.R.id.list) ListView episodesListView;
    @BindView(R.id.season_number) TextView seasonNumber;
    @BindView(R.id.season_premiere_date) TextView seasonPremiereDate;
    @BindView(R.id.season_number_of_episodes) TextView numberOfEpisods;

    String mShowId;
    String mEpisodeNumber;
    String mSeasonNumber;
    String mSeasonId;
    List<String> episodes = new ArrayList<>();

    public SeasonsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_seasons, container, false);
        ButterKnife.bind(this, view);

        Bundle arguments = getArguments();
        mSeasonId = arguments.getString("seasonId");
        mShowId = arguments.getString("showId");

        getSeasonData(mSeasonId);

        return view;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

//@TODO CLEAN THIS
    public void getSeasonData(String mSeasonId) {
        SeasonEndPoints apiService = ApiClient.getClient().create(SeasonEndPoints.class);
        Call<SeasonResponse> call = apiService.getResponse(mSeasonId);

        call.enqueue(new Callback<SeasonResponse>() {
            @Override
            public void onResponse(Call<SeasonResponse> call,
                                   Response<SeasonResponse> response) {

                if (response.body().getImage() != null) {
                    Picasso.with(getContext())
                            .load(response.body().getImage().getOriginal())
                            .into(toolbarImage);
                }




                seasonDescription.setText(response.body().getSummary());
                seasonEndDate.setText(response.body().getEndDate());
                seasonPremiereDate.setText(response.body().getPremiereDate());

                seasonNumber.setText(response.body().getNumber().toString());
                numberOfEpisods.setText(response.body().getEpisodeOrder().toString());
                int numOfEpisodes = response.body().getEpisodeOrder();
                int seasonNum = response.body().getNumber();

                mSeasonNumber = String.valueOf(seasonNum);

                populateList(numOfEpisodes, seasonNum);
            }

            @Override
            public void onFailure(Call<SeasonResponse> call, Throwable t) {

            }
        });
    }

    public void populateList(int numOfEpisodes, int seasonNum) {
        episodes.clear();

        for(int i=0; i<numOfEpisodes; i++) {
            int num = i+1;
            String epName = String.valueOf(seasonNum) + "x" + num;
            episodes.add(epName);
            System.out.println("DUPA" + epName);
        }

        ListAdapter adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.season_episodes_single_item,
                R.id.list_episode_number,
                episodes
        );

        episodesListView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(episodesListView);

    }

    @Override
    public void onListItemClick(ListView lv, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(lv, v, position, id);

        mEpisodeNumber = String.valueOf(position+1);


        Fragment newFragment = new NextEpisodeDetailsFragment();
        Bundle arguments = new Bundle();
        arguments.putString( "showId", mShowId);
        arguments.putString( "episodeNumber", mEpisodeNumber);
        arguments.putString( "seasonNumber", mSeasonNumber);
        newFragment.setArguments(arguments);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }







}
