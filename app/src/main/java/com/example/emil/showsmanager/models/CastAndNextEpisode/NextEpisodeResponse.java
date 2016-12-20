package com.example.emil.showsmanager.models.CastAndNextEpisode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Emil on 20.12.2016.
 */

public class NextEpisodeResponse {

    @SerializedName("_embedded")
    @Expose
    private Embedded embedded;

    public NextEpisodeResponse() {
    }

    public NextEpisodeResponse(Embedded embedded) {

        this.embedded = embedded;
    }



    public Embedded getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this.embedded = embedded;
    }
}
