package com.example.emil.showsmanager.presenter;


import com.example.emil.showsmanager.ShowsManagerApplication;
import com.example.emil.showsmanager.models.FullShowInfoResponse.SingleSeason;
import com.example.emil.showsmanager.rest.TVMazeService;
import com.example.emil.showsmanager.view.SeasonMvpView;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class SeasonPresenter implements Presenter<SeasonMvpView> {

    private Subscription subscription;
    private SeasonMvpView seasonMvpView;

    @Override
    public void attachView(SeasonMvpView seasonMvpView) {
        this.seasonMvpView = seasonMvpView;
    }

    @Override
    public void detachView() {
        this.seasonMvpView = null;
        if (subscription != null) subscription.unsubscribe();
    }

    public void loadSeasonInfo(String seasonId) {
        ShowsManagerApplication application = ShowsManagerApplication.get(seasonMvpView.getContext());
        TVMazeService tvMazeService = application.getTvMazeService();
        subscription = tvMazeService.getSeasonResponse(seasonId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Action1<SingleSeason>() {
                    @Override
                    public void call(SingleSeason season) {
                        seasonMvpView.showSeasonInfo(season);
                    }
                });
    }
}
