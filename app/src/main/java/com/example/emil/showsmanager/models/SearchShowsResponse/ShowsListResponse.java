package com.example.emil.showsmanager.models.SearchShowsResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowsListResponse {

    @SerializedName("show")
    @Expose
    private Show show;


    public ShowsListResponse() {
    }

    public ShowsListResponse(Show show) {
        this.show = show;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

}