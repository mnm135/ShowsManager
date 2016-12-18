package com.example.emil.showsmanager.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Emil on 26.11.2016.
 */

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

    /**
     * No args constructor for use in serialization
     *
     */
    public Externals() {
    }

    /**
     *
     * @param thetvdb
     * @param imdb
     * @param tvrage
     */
    public Externals(Integer tvrage, Integer thetvdb, String imdb) {
        this.tvrage = tvrage;
        this.thetvdb = thetvdb;
        this.imdb = imdb;
    }

    /**
     *
     * @return
     * The tvrage
     */
    public Integer getTvrage() {
        return tvrage;
    }

    /**
     *
     * @param tvrage
     * The tvrage
     */
    public void setTvrage(Integer tvrage) {
        this.tvrage = tvrage;
    }

    /**
     *
     * @return
     * The thetvdb
     */
    public Integer getThetvdb() {
        return thetvdb;
    }

    /**
     *
     * @param thetvdb
     * The thetvdb
     */
    public void setThetvdb(Integer thetvdb) {
        this.thetvdb = thetvdb;
    }

    /**
     *
     * @return
     * The imdb
     */
    public String getImdb() {
        return imdb;
    }

    /**
     *
     * @param imdb
     * The imdb
     */
    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

}