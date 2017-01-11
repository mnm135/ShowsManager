package com.example.emil.showsmanager.activities;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.emil.showsmanager.Fragments.SubscribedShowsFragment;
import com.example.emil.showsmanager.Fragments.UpcomingEpisodesFragment;
import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.TVShowsDB;

import java.util.ArrayList;
import java.util.List;

public class UpcomingEpisodesActivity extends MainActivity {
    ListView list;
    ArrayAdapter<String> adapter ;
    List<String> showTitles = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_upcoming_episodes, null, false);
        drawer.addView(contentView, 0);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new UpcomingEpisodesFragment())
                    .commit();
        }
    }

}
