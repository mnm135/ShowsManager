package com.example.emil.showsmanager.models;

import com.google.firebase.database.IgnoreExtraProperties;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Emil on 21.12.2016.
 */

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
//@TODO
    public SubscribedShow(String id, String name, String nextEpisodeAirdate, String imageUrl, String status) {
        this.id = id;
        this.name = name;
        this.nextEpisodeAirdate = nextEpisodeAirdate;
        this.imageUrl = imageUrl;
        this.status = status;
        //this.timeToNextEpisode = calculateTimeToNextEpisode(nextEpisodeAirdate);
    }

    public SubscribedShow(String id, String name, String nextEpisodeAirdate, String imageUrl,
                          String status, String airtime, String channel, String nextEpNumber,
                          String nextEpSeason) {
        this.id = id;
        this.name = name;
        this.nextEpisodeAirdate = nextEpisodeAirdate;
        this.imageUrl = imageUrl;
        this.status = status;
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



    /*

    public String timeToNextEpisode;
    public String status;
    public String airtime;
    public String channel;
    public String nextEpNumber;
    public String nextEpSeason;
     */

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


    //@TODO resources
    public String calculateTimeToNextEpisode(String airdate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String today = dateFormat.format(date);

        //String remainingTime =

        int days = Days.daysBetween(new LocalDate(date),
                new LocalDate(airdate)).getDays();

        if (days == 0) {
            return "today!";
        }
        return String.valueOf(days);


    }



}
