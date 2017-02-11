package com.example.emil.showsmanager.view;

import com.example.emil.showsmanager.models.ShowsListResponse;

import java.util.List;

public interface SearchMvpView extends MvpView {
    void showSearchResults(List<ShowsListResponse> searchResult);

}
