package com.example.emil.showsmanager.models.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.Date;


@IgnoreExtraProperties
public class FirebaseShow {
    private String id;
    private String name;
    private String nextEpisodeAirdate;
    private String imageUrl;

    private String timeToNextEpisode;
    private String status;
    private String airtime;
    private String channel;
    private String nextEpNumber;
    private String nextEpSeason;

    public FirebaseShow() {
    }

    public FirebaseShow(String id, String name, String nextEpisodeAirdate, String imageUrl, String status) {
        this.id = id;
        this.name = name;
        this.nextEpisodeAirdate = nextEpisodeAirdate;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public FirebaseShow(String id, String name, String nextEpisodeAirdate, String imageUrl,
                        String status, String airtime, String channel, String nextEpNumber,
                        String nextEpSeason) {
        this(id, name, nextEpisodeAirdate, imageUrl, status);
        this.airtime = airtime;
        this.channel = channel;
        this.nextEpNumber = nextEpNumber;
        this.nextEpSeason = nextEpSeason;
        this.timeToNextEpisode = calculateTimeToNextEpisode(nextEpisodeAirdate);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setNextEpisodeAirdate(String nextEpisodeAirdate) {
        this.nextEpisodeAirdate = nextEpisodeAirdate;
    }
    public String getNextEpisodeAirdate() {
        return nextEpisodeAirdate;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setTimeToNextEpisode(String timeToNextEpisode) {
        this.timeToNextEpisode = timeToNextEpisode;
    }

    public String getTimeToNextEpisode() {
        return timeToNextEpisode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setAirtime(String airtime) {
        this.airtime = airtime;
    }

    public String getAirtime() {
        return airtime;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    public void setNextEpNumber(String nextEpNumber) {
        this.nextEpNumber = nextEpNumber;
    }

    public String getNextEpNumber() {
        return nextEpNumber;
    }

    public void setNextEpSeason(String nextEpSeason) {
        this.nextEpSeason = nextEpSeason;
    }

    public String getNextEpSeason() {
        return nextEpSeason;
    }

    public void setTimeToNextEpisode() {
        this.timeToNextEpisode = calculateTimeToNextEpisode(this.nextEpisodeAirdate);
    }

    public String getDaysToNextEpisode() {
        return timeToNextEpisode;
    }

    //@TODO resources + timezone support
    private String calculateTimeToNextEpisode(String airdate) {
        Date date = new Date();
        LocalDate today = new LocalDate(date);
        int days = Days.daysBetween(today, new LocalDate(airdate)).getDays();

        return String.valueOf(days);
    }

    private FirebaseShow(ShowBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.nextEpisodeAirdate = builder.nextEpisodeAirdate;
        this.imageUrl = builder.imageUrl;
        this.status = builder.status;
        this.airtime = builder.airtime;
        this.channel = builder.channel;
        this.nextEpNumber = builder.nextEpNumber;
        this.nextEpSeason = builder.nextEpSeason;
        if (builder.nextEpisodeAirdate != null) {
            this.timeToNextEpisode = calculateTimeToNextEpisode(builder.nextEpisodeAirdate);
        }

    }

    public static class ShowBuilder {
        private String id;
        private String name;
        private String nextEpisodeAirdate;
        private String imageUrl;
        private String status;
        private String airtime;
        private String channel;
        private String nextEpNumber;
        private String nextEpSeason;

        public ShowBuilder(String id, String name, String status, String imageUrl) {
            this.id = id;
            this.name = name;
            this.status = status;
            this.imageUrl = imageUrl;
        }

        public ShowBuilder nextEpisodeAitdate(String nextEpisodeAirdate) {
            this.nextEpisodeAirdate = nextEpisodeAirdate;
            return this;
        }

        public ShowBuilder showAirtime(String airtime) {
            this.airtime = airtime;
            return this;
        }

        public ShowBuilder channel(String channel) {
            this.channel = channel;
            return this;
        }

        public ShowBuilder nextEpisodeNumber(String nextEpNumber) {
            this.nextEpNumber = nextEpNumber;
            return this;
        }

        public ShowBuilder nextEpisodeSeason(String nextEpSeason) {
            this.nextEpSeason = nextEpSeason;
            return this;
        }

        public FirebaseShow build() {
            return new FirebaseShow(this);
        }
    }
}
