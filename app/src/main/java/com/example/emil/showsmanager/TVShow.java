package com.example.emil.showsmanager;


public class TVShow {

    String name;
    String status;
    String premiereYear;
    String countryCode;
    int id;
    String airdate;

    public TVShow() {}

    public TVShow(int id, String name, String premiereYear, String status, String countryCode, String airdate) {
        this.id = id;
        this.name = name;
        this.premiereYear = premiereYear;
        this.status = status;
        this.countryCode = countryCode;
        this.airdate = airdate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPremiereYear(String premiereYear) {
        this.premiereYear = premiereYear;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAirdate(String airdate) {
        this.airdate = airdate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPremiereYear() {
        return premiereYear;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getStatus() {
        return status;
    }

    public String getAirdate() {
        return airdate;
    }


}
