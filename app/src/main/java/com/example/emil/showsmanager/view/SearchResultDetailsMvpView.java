package com.example.emil.showsmanager.view;

import com.example.emil.showsmanager.models.CastAndNextEpisode.ShowDetailsWithNextEpisodeResponse;

/**
 * Created by Emil on 11.02.2017.
 */

public interface SearchResultDetailsMvpView extends MvpView {

    void bindShowData(final ShowDetailsWithNextEpisodeResponse show);
}
