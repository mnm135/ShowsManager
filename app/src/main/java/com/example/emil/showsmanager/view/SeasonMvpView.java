package com.example.emil.showsmanager.view;


import com.example.emil.showsmanager.models.FullShowInfoResponse.SingleSeason;

public interface SeasonMvpView extends MvpView {
    void showSeasonInfo(SingleSeason season);

}
