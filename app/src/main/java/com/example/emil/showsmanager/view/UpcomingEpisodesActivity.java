package com.example.emil.showsmanager.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.activities.BaseActivity;
import com.example.emil.showsmanager.adapters.UpcomingEpisodesAdapter;
import com.example.emil.showsmanager.models.firebase.FirebaseShow;
import com.example.emil.showsmanager.presenter.UpcomingEpisodesPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpcomingEpisodesActivity extends BaseActivity implements UpcomingEpisodesMvpView {

    private UpcomingEpisodesPresenter presenter;

    @BindView(R.id.upcoming_episodes_recycler)
    RecyclerView upcomingEpisodesRecyclerView;

    private RecyclerView.Adapter adapter;

    private List<FirebaseShow> showList = new ArrayList<FirebaseShow>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.fragment_upcoming_episodes, frameLayout);

        showList.clear();
        presenter = new UpcomingEpisodesPresenter();
        presenter.attachView(this);

        ButterKnife.bind(this);
        initRecyclerView();
        bottomNavigationView.getMenu().getItem(2).setChecked(true);

        presenter.loadData();
    }

    public void showUpcomingEpisodes(final FirebaseShow show) {
        //showList.clear();
        showList.add(show);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
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
