package com.mnm135.emil.showsmanager.seasondetails;


import com.mnm135.emil.showsmanager.models.FullShowInfoResponse.SingleSeason;
import com.mnm135.emil.showsmanager.MvpView;

public interface SeasonMvpView extends MvpView {
    void showSeasonInfo(SingleSeason season);

}
