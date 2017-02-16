package com.example.emil.showsmanager.presenter;

import com.example.emil.showsmanager.ShowsManagerApplication;
import com.example.emil.showsmanager.models.CastAndNextEpisode.ShowDetailsWithNextEpisodeResponse;
import com.example.emil.showsmanager.models.firebase.SubscribedShow;
import com.example.emil.showsmanager.rest.TVMazeService;
import com.example.emil.showsmanager.view.ShowDetailsMvpView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class ShowDetailsPresenter implements Presenter<ShowDetailsMvpView> {

    private ShowDetailsMvpView showDetailsMvpView;
    private Subscription subscription;
    private ShowDetailsWithNextEpisodeResponse showResponse;
    private boolean showSubscribed;
    private ValueEventListener firebaseListener;
    private DatabaseReference mDatabase;

    @Override
    public void attachView(ShowDetailsMvpView showDetailsMvpView) {
        this.showDetailsMvpView = showDetailsMvpView;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void detachView() {
        this.showDetailsMvpView = null;
        if (subscription != null) subscription.unsubscribe();
        if (firebaseListener != null) mDatabase.removeEventListener(firebaseListener);
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
                showResponse = showDetailsWithNextEpisodeResponse;
                showDetailsMvpView.bindShowData(showDetailsWithNextEpisodeResponse);
            }
        });
    }

    public void onSubscribeClick() {
        if (!showSubscribed) {
            subscribeShow();
        } else {
            unsubscribeShow();
        }
    }

    private void subscribeShow() {
        String userId = getUserId();
        String showId = showResponse.getId().toString();
        String showName = showResponse.getName();
        String nextEpisodeAirdate = showResponse.getEmbedded().getNextepisode().getAirdate();
        String imageurl = showResponse.getImage().getMedium();
        String showStatus = showResponse.getStatus();
        String showAirdate = showResponse.getPremiered();
        String showChannel = showResponse.getNetwork().getName();

        String nextEpisodeSeasonNumber = showResponse.getEmbedded().getNextepisode().getNumber().toString();
        String nextEpisodeNumber = showResponse.getEmbedded().getNextepisode().getSeason().toString();

        SubscribedShow show = new SubscribedShow.ShowBuilder(showId, showName, showStatus, imageurl)
                .showAirdate(showAirdate)
                .channel(showChannel)
                .nextEpisodeAitdate(nextEpisodeAirdate)
                .nextEpisodeNumber(nextEpisodeNumber)
                .nextEpisodeSeason(nextEpisodeSeasonNumber)
                .build();

        mDatabase.child("users").child(userId).child("shows").child(showId).setValue(show);
    }

    private void unsubscribeShow() {
        String userId = getUserId();
        String showId = showResponse.getId().toString();
        mDatabase.child("users").child(userId).child("shows").child(showId).removeValue();
    }

    public void startFabIconListener(String showId) {
        String userId = getUserId();

        firebaseListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showSubscribed = dataSnapshot.child("users").child(userId).child("shows").hasChild(showId);

                showDetailsMvpView.changeIcon(showSubscribed);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        mDatabase.addValueEventListener(firebaseListener);
    }
    private String getUserId() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return firebaseUser.getUid();
    }
}



























