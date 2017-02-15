package com.example.emil.showsmanager.models;

import com.google.firebase.database.IgnoreExtraProperties;


import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.joda.time.Period;


import java.util.Date;


@IgnoreExtraProperties
public class SubscribedShow {
    public String id;
    public String name;
    public String nextEpisodeAirdate;
    public String imageUrl;

    public String timeToNextEpisode;
    public String status;
    public String airtime;
    public String channel;
    public String nextEpNumber;
    public String nextEpSeason;

    public SubscribedShow() {
    }

    public SubscribedShow(String id, String name, String nextEpisodeAirdate, String imageUrl, String status) {
        this.id = id;
        this.name = name;
        this.nextEpisodeAirdate = nextEpisodeAirdate;
        this.imageUrl = imageUrl;
        this.status = status;
    }

    public SubscribedShow(String id, String name, String nextEpisodeAirdate, String imageUrl,
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
}
