package com.mnm135.emil.showsmanager.models.FullShowInfoResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleSeason {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("episodeOrder")
    @Expose
    private Integer episodeOrder;
    @SerializedName("premiereDate")
    @Expose
    private String premiereDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("summary")
    @Expose
    private String summary;

    SingleSeason() {}

    SingleSeason(Integer id, Integer number, Integer episodeOrder, String premiereDate, String endDate,
                 Image image, String summary) {
        this.id = id;
        this.number = number;
        this.episodeOrder = episodeOrder;
        this.premiereDate = premiereDate;
        this.endDate = endDate;
        this.image = image;
        this.summary = summary;
    }

    public Integer getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getEpisodeOrder() {
        return episodeOrder;
    }

    public String getPremiereDate() {
        return premiereDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Image getImage() {
        return image;
    }

    public String getSummary() {
        return summary;
    }
}


