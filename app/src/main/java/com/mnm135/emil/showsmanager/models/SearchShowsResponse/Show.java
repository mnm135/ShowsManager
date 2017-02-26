package com.mnm135.emil.showsmanager.models.SearchShowsResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Show {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private Image image;


    public Show() {
    }

    public Show(String id,String name, Image image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }


    public void setImage(Image image) {
        this.image = image;
    }

}