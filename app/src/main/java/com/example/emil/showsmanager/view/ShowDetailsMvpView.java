package com.example.emil.showsmanager.view;

import com.example.emil.showsmanager.models.CastAndNextEpisode.ShowDetailsWithNextEpisodeResponse;

public interface ShowDetailsMvpView extends MvpView {

    void bindShowData(final ShowDetailsWithNextEpisodeResponse show);
    void changeIcon(boolean subscribed);
}
