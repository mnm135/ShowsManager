package com.example.emil.showsmanager.rest;

import com.example.emil.showsmanager.models.CastAndNextEpisode.EpisodeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Emil on 20.12.2016.
 */

public interface EpisodeEndPoints {

    //http://api.tvmaze.com/shows/73?embed=nextepisode
    //http://api.tvmaze.com/shows/73/episodebynumber?season=7&number=9

    @GET("/shows/{id}/episodebynumber")
    Call<EpisodeResponse> getResponse(@Path("id") String id,
                                      @Query("season") String season,
                                      @Query("number") String episode);
}
