package com.example.scorelive.model;

import com.google.gson.annotations.SerializedName;

public class Team {
    @SerializedName("idTeam")
    private int idTeam;
    @SerializedName("strTeam")
    private String name;
    @SerializedName("strStadium")
    private String stadium;
    @SerializedName("strDescriptionEN")
    private String description;
    @SerializedName("intStadiumCapacity")
    private int stadiumCapacity;
    @SerializedName("intFormedYear")
    private int formedYear;
    @SerializedName("strWebsite")
    private String website;
    @SerializedName("strStadiumLocation")
    private String stadiumLocation;
    @SerializedName("strTeamBadge")
    private String teamBadge;
    @SerializedName("strStadiumThumb")
    private String stadiumThumb;

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStadiumCapacity() {
        return stadiumCapacity;
    }

    public void setStadiumCapacity(int stadiumCapacity) {
        this.stadiumCapacity = stadiumCapacity;
    }

    public int getFormedYear() {
        return formedYear;
    }

    public void setFormedYear(int formedYear) {
        this.formedYear = formedYear;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getStadiumLocation() {
        return stadiumLocation;
    }

    public void setStadiumLocation(String stadiumLocation) {
        this.stadiumLocation = stadiumLocation;
    }

    public String getTeamBadge() {
        return teamBadge;
    }

    public void setTeamBadge(String teamBadge) {
        this.teamBadge = teamBadge;
    }

    public String getStadiumThumb() {
        return stadiumThumb;
    }

    public void setStadiumThumb(String stadiumThumb) {
        this.stadiumThumb = stadiumThumb;
    }

}
