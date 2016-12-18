package com.example.emil.showsmanager.models.CastAndNextEpisode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Embedded {

    @SerializedName("cast")
    @Expose
    private List<Cast> cast = null;
    @SerializedName("nextepisode")
    @Expose
    private Nextepisode_ nextepisode;
    @SerializedName("seasons")
    @Expose
    private List<Season> seasons = null;

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public Nextepisode_ getNextepisode() {
        return nextepisode;
    }

    public void setNextepisode(Nextepisode_ nextepisode) {
        this.nextepisode = nextepisode;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

}
