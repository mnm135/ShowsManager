package com.example.emil.showsmanager.subscribedshows;


import android.app.ProgressDialog;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.base.LoadingDialog;
import com.example.emil.showsmanager.models.firebase.FirebaseShow;
import com.example.emil.showsmanager.Presenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kelvinapps.rxfirebase.DataSnapshotMapper;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;

import rx.Subscription;

public class SubscribedShowsPresenter implements Presenter<SubscribedShowsMvpView> {

    private SubscribedShowsMvpView subscribedShowsMvpView;
    private Subscription subscription;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    public void attachView(SubscribedShowsMvpView subscribedShowsMvpView) {
        this.subscribedShowsMvpView = subscribedShowsMvpView;
    }

    @Override
    public void detachView() {
        this.subscribedShowsMvpView = null;
        if (subscription != null) subscription.unsubscribe();
    }


    public void loadSubscribedShows() {

        ProgressDialog loadingDialog = LoadingDialog.showProgressDialog(subscribedShowsMvpView.getContext(),
                subscribedShowsMvpView.getContext().getResources().getString(R.string.loading_dialog_msg));

        String userId = getUserId();

        RxFirebaseDatabase.observeSingleValueEvent(databaseReference.child("users").child(userId).child("shows"),
                DataSnapshotMapper.listOf(FirebaseShow.class))
                .subscribe(showList -> {
                    subscribedShowsMvpView.showGridOfSubscribedShows(showList);

                    if (loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                    }
                });
    }

    private String getUserId() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return firebaseUser.getUid();
    }


}
























