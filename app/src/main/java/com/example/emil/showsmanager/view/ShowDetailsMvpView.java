package com.example.emil.showsmanager.view;

import com.example.emil.showsmanager.models.FullShowInfoResponse.FullShowInfo;

public interface ShowDetailsMvpView extends MvpView {

    void bindShowData(final FullShowInfo show);
    void changeIcon(boolean subscribed);
    void showSnackbar(boolean subscribed);
}
