package com.example.emil.showsmanager.models.CastAndNextEpisode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Emil on 16.12.2016.
 */

public class Links___ {

    @SerializedName("self")
    @Expose
    private Self___ self;

    public Self___ getSelf() {
        return self;
    }

    public void setSelf(Self___ self) {
        this.self = self;
    }

}