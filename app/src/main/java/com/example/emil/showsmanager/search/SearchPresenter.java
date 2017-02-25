package com.example.emil.showsmanager.search;

import android.app.ProgressDialog;

import com.example.emil.showsmanager.R;
import com.example.emil.showsmanager.ShowsManagerApplication;
import com.example.emil.showsmanager.base.LoadingDialog;
import com.example.emil.showsmanager.models.SearchShowsResponse.ShowsListResponse;
import com.example.emil.showsmanager.Presenter;
import com.example.emil.showsmanager.rest.TVMazeService;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class SearchPresenter implements Presenter<SearchMvpView> {

    private SearchMvpView searchMvpView;
    private Subscription subscription;

    @Override
    public void attachView(SearchMvpView searchMvpView) {
        this.searchMvpView = searchMvpView;
    }

    @Override
    public void detachView() {
        this.searchMvpView = null;
        if (subscription != null) subscription.unsubscribe();
    }

    public void loadSearchResult(String searchQuery) {

        ProgressDialog loadingDialog = LoadingDialog.showProgressDialog(searchMvpView.getContext(),
                searchMvpView.getContext().getResources().getString(R.string.loading_dialog_msg));

        searchQuery = clearQuery(searchQuery);
        ShowsManagerApplication application = ShowsManagerApplication.get(searchMvpView.getContext());
        TVMazeService tvMazeService = application.getTvMazeService();
        subscription = tvMazeService.getSearchResult(searchQuery)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Action1<List<ShowsListResponse>>() {
                    @Override
                    public void call(List<ShowsListResponse> showsListResponses) {
                        searchMvpView.showSearchResults(showsListResponses);

                        if (loadingDialog.isShowing()) {
                            loadingDialog.dismiss();
                        }
                    }
                });
    }

    private String clearQuery(String searchQuery) {
        return searchQuery.replaceAll("[^\\w\\s]", "").replaceAll("\\s+", "-");
    }
}
