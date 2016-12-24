package com.example.emil.showsmanager.rest;

import com.example.emil.showsmanager.models.CastAndNextEpisode.EpisodeResponse;
import com.example.emil.showsmanager.models.CastAndNextEpisode.SeasonResponse;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface SeasonEndPoints {
    //http://api.tvmaze.com/seasons/263
    @GET("/seasons/{id}")
    Call<SeasonResponse> getResponse(@Path("id") String id);
}
