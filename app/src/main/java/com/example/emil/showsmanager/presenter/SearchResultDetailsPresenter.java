package com.example.emil.showsmanager.presenter;

import com.example.emil.showsmanager.ShowsManagerApplication;
import com.example.emil.showsmanager.models.CastAndNextEpisode.ShowDetailsWithNextEpisodeResponse;
import com.example.emil.showsmanager.rest.TVMazeService;
import com.example.emil.showsmanager.view.SearchResultDetailsMvpView;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class SearchResultDetailsPresenter implements Presenter<SearchResultDetailsMvpView> {

    private SearchResultDetailsMvpView searchResultDetailsMvpView;
    private Subscription subscription;

    @Override
    public void attachView(SearchResultDetailsMvpView searchResultDetailsMvpView) {
        this.searchResultDetailsMvpView = searchResultDetailsMvpView;
    }

    @Override
    public void detachView() {
        this.searchResultDetailsMvpView = null;
        if (subscription != null) subscription.unsubscribe();
    }

    public void loadShow(String showId) {
        ShowsManagerApplication application = ShowsManagerApplication.get(searchResultDetailsMvpView.getContext());
        TVMazeService tvMazeService = application.getTvMazeService();
        subscription = tvMazeService.getResponse(showId, "cast", "nextepisode", "seasons")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Action1<ShowDetailsWithNextEpisodeResponse>() {
            @Override
            public void call(ShowDetailsWithNextEpisodeResponse showDetailsWithNextEpisodeResponse) {
                searchResultDetailsMvpView.bindShowData(showDetailsWithNextEpisodeResponse);
            }
        });
    }
}



























