package com.example.emil.showsmanager.seasondetails;


import android.app.ProgressDialog;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.ShowsManagerApplication;
import com.example.emil.showsmanager.base.LoadingDialog;
import com.example.emil.showsmanager.models.FullShowInfoResponse.SingleSeason;
import com.example.emil.showsmanager.Presenter;
import com.example.emil.showsmanager.rest.TVMazeService;

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

        ProgressDialog loadingDialog = LoadingDialog.showProgressDialog(seasonMvpView.getContext(),
                seasonMvpView.getContext().getResources().getString(R.string.loading_dialog_msg));

        ShowsManagerApplication application = ShowsManagerApplication.get(seasonMvpView.getContext());
        TVMazeService tvMazeService = application.getTvMazeService();
        subscription = tvMazeService.getSeasonResponse(seasonId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Action1<SingleSeason>() {
                    @Override
                    public void call(SingleSeason season) {
                        seasonMvpView.showSeasonInfo(season);

                        if (loadingDialog.isShowing()) {
                            loadingDialog.dismiss();
                        }
                    }
                });
    }
}
