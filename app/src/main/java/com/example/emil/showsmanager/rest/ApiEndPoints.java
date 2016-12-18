package com.example.emil.showsmanager.rest;

import com.example.emil.showsmanager.models.ShowsListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Emil on 26.11.2016.
 */

public interface ApiEndPoints {

    @GET("/search/shows")
    Call<List<ShowsListResponse>> getResponse(@Query("q") String tvShowTitle);
}

