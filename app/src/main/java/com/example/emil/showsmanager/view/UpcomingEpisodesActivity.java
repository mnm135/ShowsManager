package com.example.emil.showsmanager.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.adapters.UpcomingEpisodesAdapter;
import com.example.emil.showsmanager.models.SubscribedShow;
import com.example.emil.showsmanager.presenter.UpcomingEpisodesPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpcomingEpisodesActivity extends AppCompatActivity implements UpcomingEpisodesMvpView {

    private UpcomingEpisodesPresenter presenter;

    @BindView(R.id.upcoming_episodes_recycler)
    RecyclerView upcomingEpisodesRecyclerView;

    private RecyclerView.Adapter adapter;

    private List<SubscribedShow> showList = new ArrayList<SubscribedShow>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new UpcomingEpisodesPresenter();
        presenter.attachView(this);



        setContentView(R.layout.fragment_upcoming_episodes);
        ButterKnife.bind(this);
        initRecyclerView();

        presenter.loadData();
    }

    public void showUpcomingEpisodes(final List<SubscribedShow> shows) {
        System.out.println(shows.get(1).getName());
        showList.clear();
        showList.addAll(shows);
        adapter.notifyDataSetChanged();
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

    private void initRecyclerView() {
        upcomingEpisodesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UpcomingEpisodesAdapter(showList, R.layout.upcoming_episodes_item, this);

        upcomingEpisodesRecyclerView.setAdapter(adapter);
    }
}
