package com.example.emil.showsmanager.rest;

import com.example.emil.showsmanager.models.CastAndNextEpisode.ShowDetailsWithNextEpisodeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Emil on 26.11.2016.
 */

public interface ShowDetailsEndPoints {
    //http://api.tvmaze.com/shows/73?&embed[]=cast&embed[]=nextepisode&embed[]=seasons

    @GET("/shows/{id}")
    Call<ShowDetailsWithNextEpisodeResponse> getResponse(@Path("id") String id,
                                                         @Query("embed[]") String cast,
                                                         @Query("embed[]") String nextepisode,
                                                         @Query("embed[]") String seasons);

}
