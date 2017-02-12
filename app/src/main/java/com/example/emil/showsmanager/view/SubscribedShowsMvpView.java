package com.example.emil.showsmanager.view;

import com.example.emil.showsmanager.models.SubscribedShow;

import java.util.List;

public interface SubscribedShowsMvpView extends MvpView {

    void showGridOfSubscribedShows(List<SubscribedShow> subscribedShows);
}
