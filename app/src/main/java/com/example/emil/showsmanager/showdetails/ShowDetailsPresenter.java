package com.example.emil.showsmanager.showdetails;

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
    private FullShowInfo showResponse;
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

        ProgressDialog loadingDialog = LoadingDialog.showProgressDialog(showDetailsMvpView.getContext(),
                showDetailsMvpView.getContext().getResources().getString(R.string.loading_dialog_msg));

        ShowsManagerApplication application = ShowsManagerApplication.get(showDetailsMvpView.getContext());
        TVMazeService tvMazeService = application.getTvMazeService();
        subscription = tvMazeService.getResponse(showId, "cast", "nextepisode", "seasons")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Action1<FullShowInfo>() {
            @Override
            public void call(FullShowInfo fullShowInfo) {
                showResponse = fullShowInfo;
                showDetailsMvpView.bindShowData(fullShowInfo);

                if (loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
            }
        });
    }

    public void onSubscribeClick() {
        if (!showSubscribed) {
            subscribeShow();
        } else {
            unsubscribeShow();
        }
        showDetailsMvpView.showSnackbar(showSubscribed);
    }

    private void subscribeShow() {
        String userId = getUserId();
        String showId = showResponse.getId().toString();
        String showName = showResponse.getName();
        String imageurl = showResponse.getImage().getMedium();
        String showStatus = showResponse.getStatus();
        String showAirdate = showResponse.getPremiered();
        String showChannel = showResponse.getNetwork().getName();

        FirebaseShow show;

        if (showResponse.getEmbedded().getNextepisode() != null) {
            String nextEpisodeAirdate = showResponse.getEmbedded().getNextepisode().getAirdate();
            String nextEpisodeSeasonNumber = showResponse.getEmbedded().getNextepisode().getSeason().toString();
            String nextEpisodeNumber = showResponse.getEmbedded().getNextepisode().getNumber().toString();
            String showAirtime = showResponse.getSchedule().getTime();

            show = new FirebaseShow.ShowBuilder(showId, showName, showStatus, imageurl)
                    .showAirtime(showAirtime)
                    .channel(showChannel)
                    .nextEpisodeAitdate(nextEpisodeAirdate)
                    .nextEpisodeNumber(nextEpisodeNumber)
                    .nextEpisodeSeason(nextEpisodeSeasonNumber)
                    .build();
        } else {
            show = new FirebaseShow.ShowBuilder(showId, showName, showStatus, imageurl).build();
        }


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



























