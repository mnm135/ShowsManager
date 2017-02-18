package com.example.emil.showsmanager.presenter;

import com.example.emil.showsmanager.ShowsManagerApplication;
import com.example.emil.showsmanager.models.FullShowInfoResponse.SingleEpisode;
import com.example.emil.showsmanager.models.SearchShowsResponse.ShowsListResponse;
import com.example.emil.showsmanager.presenter.Presenter;
import com.example.emil.showsmanager.rest.TVMazeService;
import com.example.emil.showsmanager.view.EpisodeMvpView;

import java.util.List;

import rx.Single;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class EpisodePresenter implements Presenter<EpisodeMvpView> {

    private EpisodeMvpView episodeMvpView;
    private Subscription subscription;

    @Override
    public void attachView(EpisodeMvpView episodeMvpView) {
        this.episodeMvpView = episodeMvpView;
    }

    @Override
    public void detachView() {
        this.episodeMvpView = null;
        if (subscription != null) subscription.unsubscribe();
    }

    public void loadEpisodeData(String showId, String episode, String season) {
        ShowsManagerApplication application = ShowsManagerApplication.get(episodeMvpView.getContext());
        TVMazeService tvMazeService = application.getTvMazeService();
        subscription = tvMazeService.getEpisodeResponse(showId, season, episode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Action1<SingleEpisode>() {
                    @Override
                    public void call(SingleEpisode episodeResponse) {
                        episodeMvpView.showEpisodeData(episodeResponse);
                    }
                });
    }
}
