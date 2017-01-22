package com.example.emil.showsmanager.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.adapters.SearchResultsAdapter;
import com.example.emil.showsmanager.models.ShowsListResponse;
import com.example.emil.showsmanager.rest.ApiClient;
import com.example.emil.showsmanager.rest.ApiEndPoints;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class SearchActivity extends BaseActivity {

    RecyclerView mRecyclerView;
    List<ShowsListResponse> dataSource = new ArrayList<>();
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_search, null, false);
        frameLayout.addView(contentView, 0);

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
        call.enqueue(new Callback<List<ShowsListResponse>>() {
            @Override
            public void onResponse(Call<List<ShowsListResponse>> call, retrofit2.Response<List<ShowsListResponse>> response) {
                dataSource.clear();
                dataSource.addAll(response.body());
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<ShowsListResponse>> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }
}



