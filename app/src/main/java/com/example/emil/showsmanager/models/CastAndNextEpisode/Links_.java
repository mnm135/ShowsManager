package com.example.emil.showsmanager.models.CastAndNextEpisode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links_ {

    @SerializedName("self")
    @Expose
    private Self_ self;


    public Links_() {
    }

    public Links_(Self_ self) {
        this.self = self;
    }

    public Self_ getSelf() {
        return self;
    }


    public void setSelf(Self_ self) {
        this.self = self;
    }

}
