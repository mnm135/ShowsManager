package com.example.emil.showsmanager.models.CastAndNextEpisode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Emil on 16.12.2016.
 */

public class Season {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("episodeOrder")
    @Expose
    private Object episodeOrder;
    @SerializedName("premiereDate")
    @Expose
    private Object premiereDate;
    @SerializedName("endDate")
    @Expose
    private Object endDate;
    @SerializedName("network")
    @Expose
    private Network_ network;
    @SerializedName("webChannel")
    @Expose
    private Object webChannel;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("_links")
    @Expose
    private Links____ links;

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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getEpisodeOrder() {
        return episodeOrder;
    }

    public void setEpisodeOrder(Object episodeOrder) {
        this.episodeOrder = episodeOrder;
    }

    public Object getPremiereDate() {
        return premiereDate;
    }

    public void setPremiereDate(Object premiereDate) {
        this.premiereDate = premiereDate;
    }

    public Object getEndDate() {
        return endDate;
    }

    public void setEndDate(Object endDate) {
        this.endDate = endDate;
    }

    public Network_ getNetwork() {
        return network;
    }

    public void setNetwork(Network_ network) {
        this.network = network;
    }

    public Object getWebChannel() {
        return webChannel;
    }

    public void setWebChannel(Object webChannel) {
        this.webChannel = webChannel;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Links____ getLinks() {
        return links;
    }

    public void setLinks(Links____ links) {
        this.links = links;
    }

}