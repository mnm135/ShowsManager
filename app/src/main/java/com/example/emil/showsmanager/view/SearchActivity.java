package com.example.emil.showsmanager.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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

    @BindView(R.id.searchView)
    EditText searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_search, frameLayout);

        presenter = new SearchPresenter();
        presenter.attachView(this);

        ButterKnife.bind(this);


        bottomNavigationView.getMenu().getItem(1).setChecked(true);


        initSearchRecyclerView();

        searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchQuery = searchField.getText().toString();
                    hideKeyboard();
                    presenter.loadSearchResult(searchQuery);

                    handled = true;
                }
                return handled;
            }
        });
    }

    public void showSearchResults(List<ShowsListResponse> searchResult) {
        searchResultList.clear();
        searchResultList.addAll(searchResult);
        adapter.notifyDataSetChanged();
    }

    private void hideKeyboard() {
        searchField.clearFocus();
        InputMethodManager in = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(searchField.getWindowToken(), 0);
    }

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
        adapter = new SearchResultsAdapter(searchResultList, R.layout.search_results_item, this);

        searchResultRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
    }
}
