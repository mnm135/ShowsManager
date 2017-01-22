package com.example.emil.showsmanager.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.emil.showsmanager.Fragments.SearchResultMoreDetails;
import com.example.emil.showsmanager.R;

public class SearchResultDetailsActivity extends BaseActivity {
    String showId;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       LayoutInflater inflater = (LayoutInflater) this
               .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View contentView = inflater.inflate(R.layout.activity_search_result_details, null, false);
       frameLayout.addView(contentView, 0);




       if (savedInstanceState == null) {
           getSupportFragmentManager().beginTransaction()
                   .add(R.id.container, new SearchResultMoreDetails())
                   .commit();
       }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        showId = intent.getStringExtra("showId");

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_result_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public String getShowId() {
        return showId;
    }
}
