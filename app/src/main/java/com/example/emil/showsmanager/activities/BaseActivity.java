package com.example.emil.showsmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.example.emil.showsmanager.R;


public class BaseActivity extends AppCompatActivity {
    protected BottomNavigationView bottomNavigationView;
    protected FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_subscribed:
                                startActivity(new Intent(getApplicationContext(), SubscribedShowsActivity.class));
                                break;
                            case R.id.action_search:
                                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                                break;
                            case R.id.action_upcoming:
                                startActivity(new Intent(getApplicationContext(), UpcomingEpisodesActivity.class));;
                                break;
                        }
                        return true;
                    }
                });
    }
}































