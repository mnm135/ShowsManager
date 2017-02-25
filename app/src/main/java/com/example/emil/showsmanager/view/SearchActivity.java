package com.example.emil.showsmanager.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.activities.BaseActivity;
import com.example.emil.showsmanager.adapters.SearchResultsAdapter;
import com.example.emil.showsmanager.models.SearchShowsResponse.ShowsListResponse;
import com.example.emil.showsmanager.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchActivity extends BaseActivity implements SearchMvpView {

    private SearchPresenter presenter;
    private RecyclerView.Adapter adapter;

    private List<ShowsListResponse> searchResultList = new ArrayList<>();

    @BindView(R.id.search_results_recycler)
    RecyclerView searchResultRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_search, frameLayout);

        presenter = new SearchPresenter();
        presenter.attachView(this);

        ButterKnife.bind(this);


        bottomNavigationView.getMenu().getItem(1).setChecked(true);

        initSearchRecyclerView();

        setToolbar("Search");

    }

    /*private void handleIntent(Intent intent) {
        //String searchQuery;
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String searchQuery = intent.getStringExtra(SearchManager.QUERY);
            hideKeyboard();
            presenter.loadSearchResult(searchQuery);
        }
    }*/

    public void showSearchResults(List<ShowsListResponse> searchResult) {
        searchResultList.clear();
        searchResultList.addAll(searchResult);
        adapter.notifyDataSetChanged();
    }

   /* private void hideKeyboard() {
        searchField.clearFocus();
        InputMethodManager in = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(searchField.getWindowToken(), 0);
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void initSearchRecyclerView() {
        searchResultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchResultsAdapter(searchResultList, R.layout.single_list_item_search_results, this);

        searchResultRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_toolbar, menu);

        // Associate searchable configuration with the SearchView
        MenuItem searchViewMenuItem = menu.findItem( R.id.search);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        //searchView.setSearchableInfo(
       //         searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // removing search query underline,
        // using xml properties does not work on all Android versions
        View searchplate = (View)searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        searchplate.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast like print;
                if( ! searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                presenter.loadSearchResult(query);
                searchViewMenuItem.collapseActionView();
                hideSoftKeyboard(searchViewMenuItem);
                searchView.clearFocus();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                return false;
            }
        });


        return true;
    }

    public void hideSoftKeyboard(MenuItem item) {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        item.collapseActionView();
    }


}
