package com.example.emil.showsmanager.subscribedshows;

import com.example.emil.showsmanager.models.firebase.FirebaseShow;
import com.example.emil.showsmanager.MvpView;

import java.util.List;

public interface SubscribedShowsMvpView extends MvpView {

    void showGridOfSubscribedShows(List<FirebaseShow> firebaseShows);
}
