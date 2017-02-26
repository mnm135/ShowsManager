package com.mnm135.emil.showsmanager.models.FullShowInfoResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Emil on 20.12.2016.
 */

public class SingleEpisode {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("season")
    @Expose
    private Integer season;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("airdate")
    @Expose
    private String airdate;
    @SerializedName("showAirdate")
    @Expose
    private String airtime;
    @SerializedName("runtime")
    @Expose
    private Integer runtime;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("summary")
    @Expose
    private String summary;


    public SingleEpisode() {
    }

    public SingleEpisode(Integer id, String url, String name, Integer season, Integer number, String airdate, String airtime, Integer runtime, Image image, String summary) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.runtime = runtime;
        this.season = season;
        this.number = number;
        this.airdate = airdate;
        this.airtime = airtime;


        this.image = image;
        this.summary = summary;

    }

    public Integer getSeason() {
        return season;
    }

    public Integer getNumber() {
        return number;
    }
    public String getAirdate() {
        return airdate;
    }

    public String getAirtime() {
        return airtime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }



    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }



}
