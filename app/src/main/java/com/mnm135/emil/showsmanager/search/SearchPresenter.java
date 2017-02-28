package com.mnm135.emil.showsmanager.search;

import android.app.ProgressDialog;

import com.mnm135.emil.showsmanager.R;
import com.mnm135.emil.showsmanager.ShowsManagerApplication;
import com.mnm135.emil.showsmanager.base.LoadingDialog;
import com.mnm135.emil.showsmanager.models.SearchShowsResponse.ShowsListResponse;
import com.mnm135.emil.showsmanager.Presenter;
import com.mnm135.emil.showsmanager.rest.TVMazeService;

import java.util.List;
import java.util.function.BiConsumer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


public class SearchPresenter implements Presenter<SearchMvpView> {

    private SearchMvpView searchMvpView;
    private Disposable disposable;

    @Override
    public void attachView(SearchMvpView searchMvpView) {
        this.searchMvpView = searchMvpView;
    }

    @Override
    public void detachView() {
        this.searchMvpView = null;
        if (disposable != null) disposable.dispose();
    }

    public void loadSearchResult(String searchQuery) {

        ProgressDialog loadingDialog = LoadingDialog.showProgressDialog(searchMvpView.getContext(),
                searchMvpView.getContext().getResources().getString(R.string.loading_dialog_msg));

        searchQuery = clearQuery(searchQuery);
        ShowsManagerApplication application = ShowsManagerApplication.get(searchMvpView.getContext());
        TVMazeService tvMazeService = application.getTvMazeService();
        disposable = tvMazeService.getSearchResult(searchQuery)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new Consumer<List<ShowsListResponse>>() {
                    @Override
                    public void accept(List<ShowsListResponse> showsListResponses) {
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
