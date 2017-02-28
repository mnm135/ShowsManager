package com.mnm135.emil.showsmanager.subscribedshows;


import android.app.ProgressDialog;

import com.mnm135.emil.showsmanager.R;
import com.mnm135.emil.showsmanager.base.LoadingDialog;
import com.mnm135.emil.showsmanager.models.firebase.FirebaseShow;
import com.mnm135.emil.showsmanager.Presenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kelvinapps.rxfirebase.DataSnapshotMapper;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;

import io.reactivex.disposables.Disposable;


public class SubscribedShowsPresenter implements Presenter<SubscribedShowsMvpView> {

    private SubscribedShowsMvpView subscribedShowsMvpView;
    private Disposable disposable;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    public void attachView(SubscribedShowsMvpView subscribedShowsMvpView) {
        this.subscribedShowsMvpView = subscribedShowsMvpView;
    }

    @Override
    public void detachView() {
        this.subscribedShowsMvpView = null;
        if (disposable != null) disposable.dispose();
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
























