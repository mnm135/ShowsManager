package com.mnm135.emil.showsmanager.episodedetails;

import com.mnm135.emil.showsmanager.models.FullShowInfoResponse.SingleEpisode;
import com.mnm135.emil.showsmanager.MvpView;

public interface EpisodeMvpView extends MvpView {
    void showEpisodeData(SingleEpisode episode);
}
