package com.example.emil.showsmanager.models.CastAndNextEpisode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Externals {

    @SerializedName("tvrage")
    @Expose
    private Integer tvrage;
    @SerializedName("thetvdb")
    @Expose
    private Integer thetvdb;
    @SerializedName("imdb")
    @Expose
    private String imdb;

    public Externals() {
    }


    public Externals(Integer tvrage, Integer thetvdb, String imdb) {
        this.tvrage = tvrage;
        this.thetvdb = thetvdb;
        this.imdb = imdb;
    }

    public Integer getTvrage() {
        return tvrage;
    }


    public void setTvrage(Integer tvrage) {
        this.tvrage = tvrage;
    }


    public Integer getThetvdb() {
        return thetvdb;
    }


    public void setThetvdb(Integer thetvdb) {
        this.thetvdb = thetvdb;
    }


    public String getImdb() {
        return imdb;
    }


    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

}