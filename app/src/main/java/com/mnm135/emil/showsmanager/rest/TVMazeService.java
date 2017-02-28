package com.mnm135.emil.showsmanager.rest;

import com.mnm135.emil.showsmanager.models.FullShowInfoResponse.FullShowInfo;
import com.mnm135.emil.showsmanager.models.FullShowInfoResponse.SingleEpisode;
import com.mnm135.emil.showsmanager.models.FullShowInfoResponse.SingleSeason;
import com.mnm135.emil.showsmanager.models.SearchShowsResponse.ShowsListResponse;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface TVMazeService {

    @GET("/shows/{id}")
    Observable<FullShowInfo> getResponse(@Path("id") String id,
                                         @Query("embed[]") String cast,
                                         @Query("embed[]") String nextepisode,
                                         @Query("embed[]") String seasons);

    @GET("/search/shows")
    Observable<List<ShowsListResponse>> getSearchResult(@Query("q") String tvShowTitle);

    @GET("/seasons/{id}")
    Observable<SingleSeason> getSeasonResponse(@Path("id") String id);

    @GET("/shows/{id}/episodebynumber")
    Observable<SingleEpisode> getEpisodeResponse(@Path("id") String id,
                                    @Query("season") String season,
                                    @Query("number") String episode);

    class Factory {
        public static TVMazeService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.tvmaze.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return retrofit.create(TVMazeService.class);
        }
    }
}
