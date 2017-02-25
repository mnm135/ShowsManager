package com.example.emil.showsmanager.seasondetails;


import com.example.emil.showsmanager.models.FullShowInfoResponse.SingleSeason;
import com.example.emil.showsmanager.MvpView;

public interface SeasonMvpView extends MvpView {
    void showSeasonInfo(SingleSeason season);

}
