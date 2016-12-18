package com.example.emil.showsmanager.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Emil on 26.11.2016.
 */

public class Network {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("country")
    @Expose
    private Country country;

    /**
     * No args constructor for use in serialization
     *
     */
    public Network() {
    }

    /**
     *
     * @param id
     * @param name
     * @param country
     */
    public Network(Integer id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The country
     */
    public Country getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(Country country) {
        this.country = country;
    }

}
