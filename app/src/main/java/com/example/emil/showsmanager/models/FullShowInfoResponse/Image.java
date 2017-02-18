package com.example.emil.showsmanager.models.FullShowInfoResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("medium")
    @Expose
    private String medium;
    @SerializedName("original")
    @Expose
    private String original;

    public Image() {
    }


    public Image(String medium, String original) {
        this.medium = medium;
        this.original = original;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getOriginal() {
        return original;
    }


    public void setOriginal(String original) {
        this.original = original;
    }

}
