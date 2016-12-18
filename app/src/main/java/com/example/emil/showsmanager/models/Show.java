package com.example.emil.showsmanager.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emil on 26.11.2016.
 */

public class Show {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("genres")
    @Expose
    private List<String> genres = new ArrayList<String>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("runtime")
    @Expose
    private Integer runtime;
    @SerializedName("premiered")
    @Expose
    private String premiered;
    @SerializedName("schedule")
    @Expose
    private Schedule schedule;
    @SerializedName("rating")
    @Expose
    private Rating rating;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("network")
    @Expose
    private Network network;
    @SerializedName("webChannel")
    @Expose
    private Object webChannel;
    @SerializedName("externals")
    @Expose
    private Externals externals;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("updated")
    @Expose
    private Integer updated;


    public Show() {
    }


    public Show(Integer id, String url, String name, String type, String language, List<String> genres, String status, Integer runtime, String premiered, Schedule schedule, Rating rating, Integer weight, Network network, Object webChannel, Externals externals, Image image, String summary, Integer updated) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.type = type;
        this.language = language;
        this.genres = genres;
        this.status = status;
        this.runtime = runtime;
        this.premiered = premiered;
        this.schedule = schedule;
        this.rating = rating;
        this.weight = weight;
        this.network = network;
        this.webChannel = webChannel;
        this.externals = externals;
        this.image = image;
        this.summary = summary;
        this.updated = updated;

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

    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }


    public String getLanguage() {
        return language;
    }


    public void setLanguage(String language) {
        this.language = language;
    }


    public List<String> getGenres() {
        return genres;
    }


    public void setGenres(List<String> genres) {
        this.genres = genres;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public Integer getRuntime() {
        return runtime;
    }


    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }


    public String getPremiered() {
        return premiered;
    }


    public void setPremiered(String premiered) {
        this.premiered = premiered;
    }


    public Schedule getSchedule() {
        return schedule;
    }


    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }


    public Rating getRating() {
        return rating;
    }


    public void setRating(Rating rating) {
        this.rating = rating;
    }


    public Integer getWeight() {
        return weight;
    }


    public void setWeight(Integer weight) {
        this.weight = weight;
    }


    public Network getNetwork() {
        return network;
    }


    public void setNetwork(Network network) {
        this.network = network;
    }


    public Object getWebChannel() {
        return webChannel;
    }


    public void setWebChannel(Object webChannel) {
        this.webChannel = webChannel;
    }


    public Externals getExternals() {
        return externals;
    }


    public void setExternals(Externals externals) {
        this.externals = externals;
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


    public Integer getUpdated() {
        return updated;
    }


    public void setUpdated(Integer updated) {
        this.updated = updated;
    }



}