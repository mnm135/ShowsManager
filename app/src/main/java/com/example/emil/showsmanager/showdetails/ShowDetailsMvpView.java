package com.example.emil.showsmanager.showdetails;

import com.example.emil.showsmanager.models.FullShowInfoResponse.FullShowInfo;
import com.example.emil.showsmanager.MvpView;

public interface ShowDetailsMvpView extends MvpView {

    void bindShowData(final FullShowInfo show);
    void changeIcon(boolean subscribed);
    void showSnackbar(boolean subscribed);
}
