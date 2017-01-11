package com.example.emil.showsmanager.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.emil.showsmanager.Fragments.SubscribedShowsFragment;
import com.example.emil.showsmanager.MySingleton;
import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.TVShow;
import com.example.emil.showsmanager.TVShowsDB;
import com.example.emil.showsmanager.activities.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class SubscribedShowsActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_subscribed_shows, null, false);
        drawer.addView(contentView, 0);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SubscribedShowsFragment())
                    .commit();
        }
    }


}

