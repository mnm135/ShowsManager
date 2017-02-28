package com.mnm135.emil.showsmanager.episodedetails;

import android.app.ProgressDialog;

import com.mnm135.emil.showsmanager.R;
import com.mnm135.emil.showsmanager.ShowsManagerApplication;
import com.mnm135.emil.showsmanager.base.LoadingDialog;
import com.mnm135.emil.showsmanager.models.FullShowInfoResponse.SingleEpisode;
import com.mnm135.emil.showsmanager.Presenter;
import com.mnm135.emil.showsmanager.rest.TVMazeService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class EpisodePresenter implements Presenter<EpisodeMvpView> {

    private EpisodeMvpView episodeMvpView;
    private Disposable disposable;

    @Override
    public void attachView(EpisodeMvpView episodeMvpView) {
        this.episodeMvpView = episodeMvpView;
    }

    @Override
    public void detachView() {
        this.episodeMvpView = null;
        if (disposable != null) disposable.dispose();
    }

    public void loadEpisodeData(String showId, String episode, String season) {

        ProgressDialog loadingDialog = LoadingDialog.showProgressDialog(episodeMvpView.getContext(),
                episodeMvpView.getContext().getResources().getString(R.string.loading_dialog_msg));

        ShowsManagerApplication application = ShowsManagerApplication.get(episodeMvpView.getContext());
        TVMazeService tvMazeService = application.getTvMazeService();
        disposable = tvMazeService.getEpisodeResponse(showId, season, episode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Consumer<SingleEpisode>() {
                    @Override
                    public void accept(SingleEpisode episodeResponse) {
                        episodeMvpView.showEpisodeData(episodeResponse);

                        if (loadingDialog.isShowing()) {
                            loadingDialog.dismiss();
                        }
                    }
                });
    }
}
