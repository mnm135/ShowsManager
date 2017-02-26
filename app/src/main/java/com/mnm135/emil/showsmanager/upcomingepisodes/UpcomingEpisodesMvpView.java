package com.mnm135.emil.showsmanager.upcomingepisodes;

import com.mnm135.emil.showsmanager.models.firebase.FirebaseShow;
import com.mnm135.emil.showsmanager.MvpView;

/**
 * Created by Emil on 12.02.2017.
 */

public interface UpcomingEpisodesMvpView extends MvpView {

    void showUpcomingEpisodes(final FirebaseShow show);
}
