package com.example.emil.showsmanager.episodedetails;

import com.example.emil.showsmanager.models.FullShowInfoResponse.SingleEpisode;
import com.example.emil.showsmanager.MvpView;

public interface EpisodeMvpView extends MvpView {
    void showEpisodeData(SingleEpisode episode);
}
