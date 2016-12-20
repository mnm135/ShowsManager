package com.example.emil.showsmanager.rest;

import com.example.emil.showsmanager.models.CastAndNextEpisode.EpisodeResponse;
import com.example.emil.showsmanager.models.CastAndNextEpisode.NextEpisodeResponse;
import com.example.emil.showsmanager.models.CastAndNextEpisode.ShowDetailsWithNextEpisodeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Emil on 20.12.2016.
 */

public interface EpisodeEndPoints {

    //http://api.tvmaze.com/shows/73?embed=nextepisode

    @GET("/episodes/{id}")
    Call<EpisodeResponse> getResponse(@Path("id") String id);
}
