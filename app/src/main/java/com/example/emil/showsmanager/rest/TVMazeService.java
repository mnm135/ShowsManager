package com.example.emil.showsmanager.rest;

import com.example.emil.showsmanager.models.CastAndNextEpisode.ShowDetailsWithNextEpisodeResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;


public interface TVMazeService {

    @GET("/shows/{id}")
    Observable<ShowDetailsWithNextEpisodeResponse> getResponse(@Path("id") String id,
                                                               @Query("embed[]") String cast,
                                                               @Query("embed[]") String nextepisode,
                                                               @Query("embed[]") String seasons);




    class Factory {
        public static TVMazeService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.tvmaze.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(TVMazeService.class);
        }
    }
}