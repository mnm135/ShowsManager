package com.example.emil.showsmanager.presenter;

import com.example.emil.showsmanager.ShowsManagerApplication;
import com.example.emil.showsmanager.models.CastAndNextEpisode.ShowDetailsWithNextEpisodeResponse;
import com.example.emil.showsmanager.models.SubscribedShow;
import com.example.emil.showsmanager.rest.TVMazeService;
import com.example.emil.showsmanager.view.UpcomingEpisodesMvpView;
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
                DataSnapshotMapper.listOf(SubscribedShow.class))
                .subscribe((shows) -> {
                    for (SubscribedShow show : shows) {
                        if (show.getStatus().equals("Ended")) {
                            shows.remove(show);
                        }
                    }
                    updateShows(shows);
                });

    }




    private void updateShows(List<SubscribedShow> shows) {
        ShowsManagerApplication application = ShowsManagerApplication.get(upcomingEpisodesMvpView.getContext());
        TVMazeService tvMazeService = application.getTvMazeService();

        Observable<SubscribedShow> ob = Observable.from(shows);


        //@TODO change it / temporary solution / need to learn more about rx to implement it nicely
        ob.toList()
                .doOnNext(new Action1<List<SubscribedShow>>() {
                    @Override
                    public void call(List<SubscribedShow> subscribedShows) {

                            for (SubscribedShow show : subscribedShows) {

                                show.setTimeToNextEpisode();
                                updateInFirebase(show);
                                if (Integer.valueOf(show.getDaysToNextEpisode()) >= 0 && show.getStatus().equals("Running")) {
                                    upcomingEpisodesMvpView.showUpcomingEpisodes(show);
                                    shows.remove(show);
                                } else {


                                    subscription = tvMazeService.getResponse(show.getId(), "cast", "nextepisode", "seasons")
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribeOn(application.defaultSubscribeScheduler())
                                            .subscribe(new Action1<ShowDetailsWithNextEpisodeResponse>() {
                                                @Override
                                                public void call(ShowDetailsWithNextEpisodeResponse showDetailsWithNextEpisodeResponse) {
                                                    updateModel(show, showDetailsWithNextEpisodeResponse);
                                                    updateInFirebase(show);

                                                    if (!show.getStatus().equals("Ended")) {
                                                        upcomingEpisodesMvpView.showUpcomingEpisodes(show);
                                                    }
                                                }
                                            });
                                }
                            }
                        }

                }).subscribe();

    }


    private void updateModel(SubscribedShow show, ShowDetailsWithNextEpisodeResponse showResponse) {
        show.setStatus(showResponse.getStatus());
        if (!showResponse.getStatus().equals("Ended")) {
            show.setNextEpisodeAirdate(showResponse.getEmbedded().getNextepisode().getAirdate());
            show.setNextEpNumber(showResponse.getEmbedded().getNextepisode().getNumber().toString());
            show.setNextEpSeason(showResponse.getEmbedded().getNextepisode().getSeason().toString());
            show.setTimeToNextEpisode();
        }
    }

    private void updateInFirebase(SubscribedShow show) {
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

