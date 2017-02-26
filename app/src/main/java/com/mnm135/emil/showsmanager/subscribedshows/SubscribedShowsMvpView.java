package com.mnm135.emil.showsmanager.subscribedshows;

import com.mnm135.emil.showsmanager.models.firebase.FirebaseShow;
import com.mnm135.emil.showsmanager.MvpView;

import java.util.List;

public interface SubscribedShowsMvpView extends MvpView {

    void showGridOfSubscribedShows(List<FirebaseShow> firebaseShows);
}
