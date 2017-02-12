package com.example.emil.showsmanager.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.activities.SearchResultDetailsActivity;
import com.example.emil.showsmanager.adapters.SubscribedShowsGridAdapter;
import com.example.emil.showsmanager.models.SubscribedShow;
import com.example.emil.showsmanager.presenter.SubscribedShowsPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubscribedShowsActivity extends AppCompatActivity implements SubscribedShowsMvpView {

    private SubscribedShowsPresenter presenter;

    @BindView(R.id.gridview)
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SubscribedShowsPresenter();
        presenter.attachView(this);

        setContentView(R.layout.fragment_subscribed_shows);
        ButterKnife.bind(this);



        presenter.loadSubscribedShows();


    }


    public void showGridOfSubscribedShows(List<SubscribedShow> subscribedShows) {
        gridView.setAdapter(new SubscribedShowsGridAdapter(this, subscribedShows));

        setOnClickListeners(subscribedShows);
    }

    private void setOnClickListeners(List<SubscribedShow> subscribedShows) {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                String showId = subscribedShows.get(position).getId();

                Intent intent = new Intent(v.getContext(), SearchResultDetailsActivity.class);
                intent.putExtra("showId", showId);
                v.getContext().startActivity(intent);
            }
        });
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