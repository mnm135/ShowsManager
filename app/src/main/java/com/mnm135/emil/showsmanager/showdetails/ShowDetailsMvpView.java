package com.mnm135.emil.showsmanager.showdetails;

import com.mnm135.emil.showsmanager.models.FullShowInfoResponse.FullShowInfo;
import com.mnm135.emil.showsmanager.MvpView;

public interface ShowDetailsMvpView extends MvpView {

    void bindShowData(final FullShowInfo show);
    void changeIcon(boolean subscribed);
    void showSnackbar(boolean subscribed);
}
