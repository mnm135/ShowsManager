package com.mnm135.emil.showsmanager.seasondetails;


import android.app.ProgressDialog;

import com.mnm135.emil.showsmanager.R;
import com.mnm135.emil.showsmanager.ShowsManagerApplication;
import com.mnm135.emil.showsmanager.base.LoadingDialog;
import com.mnm135.emil.showsmanager.models.FullShowInfoResponse.SingleSeason;
import com.mnm135.emil.showsmanager.Presenter;
import com.mnm135.emil.showsmanager.rest.TVMazeService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class SeasonPresenter implements Presenter<SeasonMvpView> {

    private Disposable disposable;
    private SeasonMvpView seasonMvpView;

    @Override
    public void attachView(SeasonMvpView seasonMvpView) {
        this.seasonMvpView = seasonMvpView;
    }

    @Override
    public void detachView() {
        this.seasonMvpView = null;
        if (disposable != null) disposable.dispose();
    }

    public void loadSeasonInfo(String seasonId) {

        ProgressDialog loadingDialog = LoadingDialog.showProgressDialog(seasonMvpView.getContext(),
                seasonMvpView.getContext().getResources().getString(R.string.loading_dialog_msg));

        ShowsManagerApplication application = ShowsManagerApplication.get(seasonMvpView.getContext());
        TVMazeService tvMazeService = application.getTvMazeService();
        disposable = tvMazeService.getSeasonResponse(seasonId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Consumer<SingleSeason>() {
                    @Override
                    public void accept(SingleSeason season) {
                        seasonMvpView.showSeasonInfo(season);

                        if (loadingDialog.isShowing()) {
                            loadingDialog.dismiss();
                        }
                    }
                });
    }
}
