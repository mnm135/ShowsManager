package com.example.emil.showsmanager;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class Upcoming extends MainActivity {

    String name;
    int id;
    String premiereYear;
    String status;
    String countryCode;
    String airdate;
    int counter = 0;
    int length;

    List<TVShow> listOfShowsToUpdate = new ArrayList<TVShow>();
    List<String> titlesToUpdate = new ArrayList<String>();
    ListView listup;

    TVShowsDB db = new TVShowsDB(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_upcoming, null, false);
        drawer.addView(contentView, 0);

        listup = (ListView) findViewById(R.id.listup);

        List<TVShow> shows = db.getAllShows();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today =  dateFormat.format(date);

        for (TVShow show : shows) {
            if (show.getAirdate().compareTo(today.toString()) < 0 && show.getStatus().equals("Running")) {
                titlesToUpdate.add(show.getName());
            }
        }

        if(titlesToUpdate.size() > 0) {
            length = titlesToUpdate.size();
            updateNextEpisodesDates(titlesToUpdate);
        } else {
            populateList();
        }
    }

    public void updateNextEpisodesDates(List<String> titles) {
        length = titles.size();
        for (String title : titles) {
            title = title.replaceAll("[^\\w\\s]", "").replaceAll("\\s+", "-");
            String url = "http://api.tvmaze.com/singlesearch/shows?q=" + title + "&embed=nextepisode";

            JsonObjectRequest request = new JsonObjectRequest
                    (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            counter++;
                            try {

                                id = response.getInt("id");
                                name = response.getString("name");
                                premiereYear = response.getString("premiered");
                                status = response.getString("status");
                                countryCode = response.getJSONObject("network").getJSONObject("country").getString("code");

                                if(status.equals("Running") && response.has("_embedded")) {
                                    airdate = response.getJSONObject("_embedded").getJSONObject("nextepisode").getString("airdate");
                                } else {
                                    if(status.equals("Running"))
                                        airdate = "TBA";
                                    else
                                        airdate = "Show is not running";
                                }

                                TVShow show = new TVShow(id, name, premiereYear, status, countryCode, airdate);
                                listOfShowsToUpdate.add(show);

                                if (counter == length) { //making async volley sync
                                    for (TVShow showToUpdate : listOfShowsToUpdate) {
                                        db.updateShows(showToUpdate);
                                    }
                                    populateList();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    System.out.println(error);
                                }
                            });
            MySingleton.getInstance(this).addToRequestQueue(request);
        }
    }

    public void populateList() {
        List<TVShow> listOfShows = db.getAllShows();
        List<HashMap<String, String>> dataToPutOnTheView= new ArrayList<HashMap<String, String>>();

        for(TVShow show : listOfShows) {
            if(!Objects.equals(show.getAirdate(), "Show is not running")) {
                HashMap<String, String> nameAndDateMap = new HashMap<String, String>();
                nameAndDateMap.put("show_name", show.getName());
                nameAndDateMap.put("show_airdate", show.getAirdate());
                dataToPutOnTheView.add(nameAndDateMap);
            }
        }
        ListAdapter adapter = new SimpleAdapter(
             this, dataToPutOnTheView ,R.layout.row_upcoming, new String[] { "show_name", "show_airdate" },
                new int[] { R.id.text1,
                R.id.text2 });
        listup.setAdapter(adapter);
    }
}

