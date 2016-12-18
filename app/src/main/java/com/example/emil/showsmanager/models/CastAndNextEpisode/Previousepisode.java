package com.example.emil.showsmanager.models.CastAndNextEpisode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Emil on 28.11.2016.
 */

public class Previousepisode {

    @SerializedName("href")
    @Expose
    private String href;

    public Previousepisode() {
    }

    public Previousepisode(String href) {
        this.href = href;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}
