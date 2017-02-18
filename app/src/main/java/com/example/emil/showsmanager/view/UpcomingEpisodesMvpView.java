package com.example.emil.showsmanager.view;

import com.example.emil.showsmanager.models.firebase.FirebaseShow;

/**
 * Created by Emil on 12.02.2017.
 */

public interface UpcomingEpisodesMvpView extends MvpView {

    void showUpcomingEpisodes(final FirebaseShow show);
}
