package com.example.emil.showsmanager.activities;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.example.emil.showsmanager.Fragments.UpcomingEpisodesFragment;
import com.example.emil.showsmanager.R;

public class UpcomingEpisodesActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_upcoming_episodes, null, false);
        frameLayout.addView(contentView, 0);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, new UpcomingEpisodesFragment())
                    .commit();
        }
    }
}
