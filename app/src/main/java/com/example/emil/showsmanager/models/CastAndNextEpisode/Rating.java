package com.example.emil.showsmanager.models.CastAndNextEpisode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("average")
    @Expose
    private Double average;

    public Rating() { }

    public Rating(Double average) {
        this.average = average;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

}