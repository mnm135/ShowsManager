package com.example.emil.showsmanager.presenter;


import com.example.emil.showsmanager.models.firebase.SubscribedShow;
import com.example.emil.showsmanager.view.SubscribedShowsMvpView;
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

        String userId = getUserId();

        RxFirebaseDatabase.observeSingleValueEvent(databaseReference.child("users").child(userId).child("shows"),
                DataSnapshotMapper.listOf(SubscribedShow.class))
                .subscribe(showList -> {
                    subscribedShowsMvpView.showGridOfSubscribedShows(showList);

                    // process blogPost list item
                });
    }

    private String getUserId() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        return firebaseUser.getUid();
    }


}
























