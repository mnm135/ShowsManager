package com.example.emil.showsmanager.view;

import com.example.emil.showsmanager.models.FullShowInfoResponse.SingleEpisode;

public interface EpisodeMvpView extends MvpView {
    void showEpisodeData(SingleEpisode episode);
}
