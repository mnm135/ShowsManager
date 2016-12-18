package com.example.emil.showsmanager;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ManageActivity extends MainActivity {
    ListView list;
    ArrayAdapter<String> adapter ;
    List<String> showTitles = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_manage, null, false);
        drawer.addView(contentView, 0);

        list = (ListView) findViewById(android.R.id.list);

        TVShowsDB db = new TVShowsDB(this);
        List<TVShow> shows = db.getAllShows();
        System.out.println(shows);


        for (TVShow show : shows) {
            if (show.getName() != null)
            showTitles.add(show.getName());
        }

        adapter = new ArrayAdapter<String>(this, R.layout.row, R.id.Row, showTitles);
        list.setAdapter(adapter);
    }

    public void myClickHandler(View v) {
        RelativeLayout ParenRow = (RelativeLayout)v.getParent();
        TextView child = (TextView)ParenRow.getChildAt(0);
        String showToDelete = child.getText().toString();

        TVShowsDB db = new TVShowsDB(this);
        db.deleteShow(showToDelete);
        ParenRow.refreshDrawableState();

        final int position = list.getPositionForView((View) v.getParent());
        delete(position);
    }

    public void delete(int position) {
        showTitles.remove(position);
        adapter.notifyDataSetChanged();
    }
}
