package com.mnm135.emil.showsmanager.search;

import com.mnm135.emil.showsmanager.models.SearchShowsResponse.ShowsListResponse;
import com.mnm135.emil.showsmanager.MvpView;

import java.util.List;

public interface SearchMvpView extends MvpView {
    void showSearchResults(List<ShowsListResponse> searchResult);

}
