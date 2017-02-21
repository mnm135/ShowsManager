package com.example.emil.showsmanager.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.view.*;
import com.example.emil.showsmanager.view.UpcomingEpisodesActivity;

import butterknife.BindView;


public class BaseActivity extends AppCompatActivity {
    protected BottomNavigationView bottomNavigationView;
    protected FrameLayout frameLayout;

    @BindView(R.id.action_subscribed)
    View subscribeNavItem;

    @BindView(R.id.action_search)
    View searchNavItem;

    @BindView(R.id.action_upcoming)
    View upcomingNavItem;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setDisplayShowHomeEnabled(true);

        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_subscribed:
                            startActivity(new Intent(getApplicationContext(), SubscribedShowsActivity.class));
                            break;
                        case R.id.action_search:
                            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                            break;
                        case R.id.action_upcoming:
                            startActivity(new Intent(getApplicationContext(), UpcomingEpisodesActivity.class));
                            break;
                    }
                    return true;
                });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, com.example.emil.showsmanager.view.ShowDetailsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}































