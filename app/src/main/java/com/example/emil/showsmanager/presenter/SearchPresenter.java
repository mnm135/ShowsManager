package com.example.emil.showsmanager.presenter;

import com.example.emil.showsmanager.ShowsManagerApplication;
import com.example.emil.showsmanager.models.SearchShowsResponse.ShowsListResponse;
import com.example.emil.showsmanager.rest.TVMazeService;
import com.example.emil.showsmanager.view.SearchMvpView;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Emil on 11.02.2017.
 */

public class SearchPresenter implements Presenter<SearchMvpView> {

    private SearchMvpView searchMvpView;
    private Subscription subscription;

    @Override
    public void attachView(SearchMvpView searchMvpView) {
        this.searchMvpView = searchMvpView;
    }

    @Override
    public void detachView() {
        this.searchMvpView = null;
        if (subscription != null) subscription.unsubscribe();
    }

    public void loadSearchResult(String searchQuery) {
        searchQuery = clearQuery(searchQuery);
        ShowsManagerApplication application = ShowsManagerApplication.get(searchMvpView.getContext());
        TVMazeService tvMazeService = application.getTvMazeService();
        subscription = tvMazeService.getSearchResult(searchQuery)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Action1<List<ShowsListResponse>>() {
                    @Override
                    public void call(List<ShowsListResponse> showsListResponses) {
                        searchMvpView.showSearchResults(showsListResponses);
                    }
                });
    }

    private String clearQuery(String searchQuery) {
        return searchQuery.replaceAll("[^\\w\\s]", "").replaceAll("\\s+", "-");
    }
}
