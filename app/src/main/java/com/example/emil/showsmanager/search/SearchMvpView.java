package com.example.emil.showsmanager.search;

import com.example.emil.showsmanager.models.SearchShowsResponse.ShowsListResponse;
import com.example.emil.showsmanager.MvpView;

import java.util.List;

public interface SearchMvpView extends MvpView {
    void showSearchResults(List<ShowsListResponse> searchResult);

}
