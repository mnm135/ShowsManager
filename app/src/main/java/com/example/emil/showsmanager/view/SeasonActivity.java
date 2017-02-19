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
    String mEpisodeNumber;
    String mSeasonNumber;
    String mSeasonId;
  //  List<String> episodes = new ArrayList<>();

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
        for (int i=1; i<numOfEpisodes+1; i++) {
            episodes.add(i);
        }
        episodesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SeasonAdapter(episodes, seasonNum, mShowId, R.layout.episodes_single_item, this);

        episodesRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        //populateList(numOfEpisodes, seasonNum);

    }

   /* public static void setListViewHeightBasedOnChildren(ListView listView) {
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
*/
//@TODO CLEAN THIS
   /* public void getSeasonData(String mSeasonId) {
        SeasonEndPoints apiService = ApiClient.getClient().create(SeasonEndPoints.class);
        Call<SingleSeason> call = apiService.getResponse(mSeasonId);

        call.enqueue(new Callback<SingleSeason>() {
            @Override
            public void onResponse(Call<SingleSeason> call,
                                   Response<SingleSeason> response) {

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



            @Override
            public void onFailure(Call<SingleSeason> call, Throwable t) {

            }
        });
    }*/

  /*  public void populateList(int numOfEpisodes, int seasonNum) {
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

    }*/

/*    @Override
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
    }*/

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
