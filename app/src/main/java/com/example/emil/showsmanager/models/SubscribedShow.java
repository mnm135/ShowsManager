package com.example.emil.showsmanager.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Emil on 21.12.2016.
 */

@IgnoreExtraProperties
public class SubscribedShow {
    public String id;
    public String name;
    public String nextEpisodeAirdate;
    public String imageUrl;

    public SubscribedShow() {

    }

    public SubscribedShow(String id, String name, String nextEpisodeAirdate, String imageUrl) {
        this.id = id;
        this.name = name;
        this.nextEpisodeAirdate = nextEpisodeAirdate;
        this.imageUrl = imageUrl;
    }


}
