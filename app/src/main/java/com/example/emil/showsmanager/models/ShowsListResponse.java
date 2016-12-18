package com.example.emil.showsmanager.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Emil on 26.11.2016.
 */

public class ShowsListResponse {

    @SerializedName("score")
    @Expose
    private Double score;
    @SerializedName("show")
    @Expose
    private Show show;


    public ShowsListResponse() {
    }

    public ShowsListResponse(Double score, Show show) {
        this.score = score;
        this.show = show;
    }


    public Double getScore() {
        return score;
    }


    public void setScore(Double score) {
        this.score = score;
    }


    public Show getShow() {
        return show;
    }


    public void setShow(Show show) {
        this.show = show;
    }

}