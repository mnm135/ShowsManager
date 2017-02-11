package com.example.emil.showsmanager.presenter;

import com.example.emil.showsmanager.ShowsManagerApplication;
import com.example.emil.showsmanager.models.CastAndNextEpisode.ShowDetailsWithNextEpisodeResponse;
import com.example.emil.showsmanager.rest.TVMazeService;
import com.example.emil.showsmanager.view.ShowDetailsMvpView;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class ShowDetailsPresenter implements Presenter<ShowDetailsMvpView> {

    private ShowDetailsMvpView showDetailsMvpView;
    private Subscription subscription;

    @Override
    public void attachView(ShowDetailsMvpView showDetailsMvpView) {
        this.showDetailsMvpView = showDetailsMvpView;
    }

    @Override
    public void detachView() {
        this.showDetailsMvpView = null;
        if (subscription != null) subscription.unsubscribe();
    }

    public void loadShow(String showId) {
        ShowsManagerApplication application = ShowsManagerApplication.get(showDetailsMvpView.getContext());
        TVMazeService tvMazeService = application.getTvMazeService();
        subscription = tvMazeService.getResponse(showId, "cast", "nextepisode", "seasons")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Action1<ShowDetailsWithNextEpisodeResponse>() {
            @Override
            public void call(ShowDetailsWithNextEpisodeResponse showDetailsWithNextEpisodeResponse) {
                showDetailsMvpView.bindShowData(showDetailsWithNextEpisodeResponse);
            }
        });
    }
}



























