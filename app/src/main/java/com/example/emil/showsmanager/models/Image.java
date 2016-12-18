package com.example.emil.showsmanager.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Emil on 26.11.2016.
 */


public class Image {

    @SerializedName("medium")
    @Expose
    private String medium;
    @SerializedName("original")
    @Expose
    private String original;

    /**
     * No args constructor for use in serialization
     *
     */
    public Image() {
    }

    /**
     *
     * @param original
     * @param medium
     */
    public Image(String medium, String original) {
        this.medium = medium;
        this.original = original;
    }

    /**
     *
     * @return
     * The medium
     */
    public String getMedium() {
        return medium;
    }

    /**
     *
     * @param medium
     * The medium
     */
    public void setMedium(String medium) {
        this.medium = medium;
    }

    /**
     *
     * @return
     * The original
     */
    public String getOriginal() {
        return original;
    }

    /**
     *
     * @param original
     * The original
     */
    public void setOriginal(String original) {
        this.original = original;
    }

}