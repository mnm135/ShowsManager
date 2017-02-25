package com.example.emil.showsmanager.upcomingepisodes;

import com.example.emil.showsmanager.models.firebase.FirebaseShow;
import com.example.emil.showsmanager.MvpView;

/**
 * Created by Emil on 12.02.2017.
 */

public interface UpcomingEpisodesMvpView extends MvpView {

    void showUpcomingEpisodes(final FirebaseShow show);
}
