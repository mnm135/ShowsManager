package com.mnm135.emil.showsmanager.subscribedshows;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.mnm135.emil.showsmanager.R;
import com.mnm135.emil.showsmanager.base.BaseActivity;
import com.mnm135.emil.showsmanager.models.firebase.FirebaseShow;
import com.mnm135.emil.showsmanager.showdetails.ShowDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubscribedShowsActivity extends BaseActivity implements SubscribedShowsMvpView {

    private SubscribedShowsPresenter presenter = new SubscribedShowsPresenter();

    @BindView(R.id.gridview)
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_subscribed_shows, frameLayout);

        presenter.attachView(this);

        bottomNavigationView.getMenu().getItem(0).setChecked(true);

        ButterKnife.bind(this);

        setToolbar("Subscribed");
        presenter.loadSubscribedShows();
    }

    public void showGridOfSubscribedShows(List<FirebaseShow> firebaseShows) {
        gridView.setAdapter(new SubscribedShowsGridAdapter(this, firebaseShows));

        setOnClickListener(firebaseShows);
    }

    private void setOnClickListener(List<FirebaseShow> firebaseShows) {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                String showId = firebaseShows.get(position).getId();

                Intent intent = new Intent(v.getContext(), ShowDetailsActivity.class);
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

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
    }
}
