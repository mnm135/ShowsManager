package com.example.emil.showsmanager.upcomingepisodes;

import android.app.ProgressDialog;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.ShowsManagerApplication;
import com.example.emil.showsmanager.base.LoadingDialog;
import com.example.emil.showsmanager.models.FullShowInfoResponse.FullShowInfo;
import com.example.emil.showsmanager.models.firebase.FirebaseShow;
import com.example.emil.showsmanager.Presenter;
import com.example.emil.showsmanager.rest.TVMazeService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kelvinapps.rxfirebase.DataSnapshotMapper;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class UpcomingEpisodesPresenter implements Presenter<UpcomingEpisodesMvpView> {

    private UpcomingEpisodesMvpView upcomingEpisodesMvpView;
    private Subscription subscription;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    public void attachView(UpcomingEpisodesMvpView upcomingEpisodesMvpView) {
        this.upcomingEpisodesMvpView = upcomingEpisodesMvpView;
    }

    @Override
    public void detachView() {
        this.upcomingEpisodesMvpView = null;
        if (subscription != null) subscription.unsubscribe();
    }

    public void loadData() {
        String userId = getUserId();

        RxFirebaseDatabase.observeSingleValueEvent(databaseReference.child("users").child(userId).child("shows"),
                DataSnapshotMapper.listOf(FirebaseShow.class))
                .subscribe(this::updateShows);
    }


    private void updateShows(List<FirebaseShow> shows) {
        ShowsManagerApplication application = ShowsManagerApplication.get(upcomingEpisodesMvpView.getContext());
        TVMazeService tvMazeService = application.getTvMazeService();

        Observable<FirebaseShow> ob = Observable.from(shows);

        ProgressDialog loadingDialog = LoadingDialog.showProgressDialog(upcomingEpisodesMvpView.getContext(),
                upcomingEpisodesMvpView.getContext().getResources().getString(R.string.loading_dialog_msg));


        //@TODO change it / temporary solution / need to learn more about rx to implement it nicely
        ob.toList()
                .doOnNext(new Action1<List<FirebaseShow>>() {
                    @Override
                    public void call(List<FirebaseShow> firebaseShows) {

                            for (FirebaseShow show : firebaseShows) {

                                if (show.getStatus().equals("Ended")) {
                                    continue;
                                }

                                show.setTimeToNextEpisode();
                                updateInFirebase(show);
                                if (Integer.valueOf(show.getDaysToNextEpisode()) >= 0 && show.getStatus().equals("Running")) {
                                    upcomingEpisodesMvpView.showUpcomingEpisodes(show);
                                    shows.remove(show);
                                } else {


                                    subscription = tvMazeService.getResponse(show.getId(), "cast", "nextepisode", "seasons")
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribeOn(application.defaultSubscribeScheduler())
                                            .subscribe(new Action1<FullShowInfo>() {
                                                @Override
                                                public void call(FullShowInfo fullShowInfo) {
                                                    updateModel(show, fullShowInfo);
                                                    updateInFirebase(show);

                                                    if (!show.getStatus().equals("Ended")) {
                                                        upcomingEpisodesMvpView.showUpcomingEpisodes(show);
                                                    }
                                                }
                                            });
                                }
                                if (loadingDialog.isShowing()) {
                                    loadingDialog.dismiss();
                                }
                            }

                        }

                }).subscribe();
    }

    private void updateModel(FirebaseShow show, FullShowInfo showResponse) {
        show.setStatus(showResponse.getStatus());
        if (!showResponse.getStatus().equals("Ended")) {
            show.setNextEpisodeAirdate(showResponse.getEmbedded().getNextepisode().getAirdate());
            show.setNextEpNumber(showResponse.getEmbedded().getNextepisode().getNumber().toString());
            show.setNextEpSeason(showResponse.getEmbedded().getNextepisode().getSeason().toString());
            show.setTimeToNextEpisode();
        }
    }

    private void updateInFirebase(FirebaseShow show) {
        String userId = getUserId();
        String showId = show.getId();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(userId).child("shows").child(showId).setValue(show);
    }

    private String getUserId() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return firebaseUser.getUid();
    }

}

