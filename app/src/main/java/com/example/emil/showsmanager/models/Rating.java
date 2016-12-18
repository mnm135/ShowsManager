package com.example.emil.showsmanager.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Emil on 26.11.2016.
 */

public class Rating {

    @SerializedName("average")
    @Expose
    private float average;

    /**
     * No args constructor for use in serialization
     *
     */
    public Rating() {
    }

    /**
     *
     * @param average
     */
    public Rating(float average) {
        this.average = average;
    }

    /**
     *
     * @return
     * The average
     */
    public float getAverage() {
        return average;
    }

    /**
     *
     * @param average
     * The average
     */
    public void setAverage(Integer average) {
        this.average = average;
    }

}