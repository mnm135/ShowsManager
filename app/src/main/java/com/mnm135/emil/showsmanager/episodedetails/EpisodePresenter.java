package com.mnm135.emil.showsmanager.episodedetails;

import android.app.ProgressDialog;

import com.mnm135.emil.showsmanager.R;
import com.mnm135.emil.showsmanager.ShowsManagerApplication;
import com.mnm135.emil.showsmanager.base.LoadingDialog;
import com.mnm135.emil.showsmanager.models.FullShowInfoResponse.SingleEpisode;
import com.mnm135.emil.showsmanager.Presenter;
import com.mnm135.emil.showsmanager.rest.TVMazeService;

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
