package com.example.emil.showsmanager.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emil on 26.11.2016.
 */

public class Schedule {

    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("days")
    @Expose
    private List<Object> days = new ArrayList<Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Schedule() {
    }

    /**
     *
     * @param time
     * @param days
     */
    public Schedule(String time, List<Object> days) {
        this.time = time;
        this.days = days;
    }

    /**
     *
     * @return
     * The time
     */
    public String getTime() {
        return time;
    }

    /**
     *
     * @param time
     * The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     *
     * @return
     * The days
     */
    public List<Object> getDays() {
        return days;
    }

    /**
     *
     * @param days
     * The days
     */
    public void setDays(List<Object> days) {
        this.days = days;
    }

}
