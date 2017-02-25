package com.example.emil.showsmanager.episodedetails;

import android.app.ProgressDialog;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.ShowsManagerApplication;
import com.example.emil.showsmanager.base.LoadingDialog;
import com.example.emil.showsmanager.models.FullShowInfoResponse.SingleEpisode;
import com.example.emil.showsmanager.Presenter;
import com.example.emil.showsmanager.rest.TVMazeService;

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

        ProgressDialog loadingDialog = LoadingDialog.showProgressDialog(episodeMvpView.getContext(),
                episodeMvpView.getContext().getResources().getString(R.string.loading_dialog_msg));

        ShowsManagerApplication application = ShowsManagerApplication.get(episodeMvpView.getContext());
        TVMazeService tvMazeService = application.getTvMazeService();
        subscription = tvMazeService.getEpisodeResponse(showId, season, episode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Action1<SingleEpisode>() {
                    @Override
                    public void call(SingleEpisode episodeResponse) {
                        episodeMvpView.showEpisodeData(episodeResponse);

                        if (loadingDialog.isShowing()) {
                            loadingDialog.dismiss();
                        }
                    }
                });
    }
}
