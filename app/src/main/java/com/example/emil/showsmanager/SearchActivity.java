package com.example.emil.showsmanager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.emil.showsmanager.adapters.SearchResultsAdapter;
import com.example.emil.showsmanager.models.ShowsListResponse;
import com.example.emil.showsmanager.rest.ApiClient;
import com.example.emil.showsmanager.rest.ApiEndPoints;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class SearchActivity extends MainActivity {

    String name;
    int id;
    String premiereYear;
    String status;
    String countryCode;
    String airdate;
    Button button;

    RecyclerView mRecyclerView;
    List<ShowsListResponse> dataSource = new ArrayList<>();
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_search, null, false);
        drawer.addView(contentView, 0);




        final EditText editText = (EditText) findViewById(R.id.searchView);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String query = editText.getText().toString();
                    handled = true;
                    clearQuery(query);
                }
                return handled;
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.search_results_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchResultsAdapter(dataSource, R.layout.search_results_item, getApplicationContext());
        mRecyclerView.setAdapter(adapter);



    }

    public void clearQuery(String query) {
        query = query.replaceAll("[^\\w\\s]", "").replaceAll("\\s+", "-");
        loadSearchResults(query);
    }

    public void loadSearchResults(String query) {

        ApiEndPoints apiService = ApiClient.getClient().create(ApiEndPoints.class);

        Call<List<ShowsListResponse>> call = apiService.getResponse(query);

        System.out.println(call.request().url() + "AAAAAAAAAAAAAA");

        call.enqueue(new Callback<List<ShowsListResponse>>() {
            @Override
            public void onResponse(Call<List<ShowsListResponse>> call, retrofit2.Response<List<ShowsListResponse>> response) {
                System.out.println("aa" + response.body());
                dataSource.clear();
                dataSource.addAll(response.body());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<ShowsListResponse>> call, Throwable t) {
                t.printStackTrace();

            }
        });




        /*
        String url = "http://api.tvmaze.com/singlesearch/shows?q=" + query + "&embed=nextepisode";

        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
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
                            addItemToView(name, premiereYear, countryCode, status);

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

        */
    }
}



