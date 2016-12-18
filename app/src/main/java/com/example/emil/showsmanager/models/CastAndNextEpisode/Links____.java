package com.example.emil.showsmanager.models.CastAndNextEpisode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Emil on 16.12.2016.
 */

public class Links____ {

    @SerializedName("self")
    @Expose
    private Self____ self;

    public Self____ getSelf() {
        return self;
    }

    public void setSelf(Self____ self) {
        this.self = self;
    }

}