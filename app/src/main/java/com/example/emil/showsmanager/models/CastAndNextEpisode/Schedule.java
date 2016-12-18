package com.example.emil.showsmanager.models.CastAndNextEpisode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Schedule {

    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("days")
    @Expose
    private List<String> days = new ArrayList<String>();

    public Schedule() {
    }

    public Schedule(String time, List<String> days) {
        this.time = time;
        this.days = days;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

}
