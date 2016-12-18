package com.example.emil.showsmanager.models.CastAndNextEpisode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Emil on 16.12.2016.
 */

public class Links__ {

    @SerializedName("self")
    @Expose
    private Self__ self;

    public Self__ getSelf() {
        return self;
    }

    public void setSelf(Self__ self) {
        this.self = self;
    }

}