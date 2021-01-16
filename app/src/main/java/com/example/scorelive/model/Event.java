package com.example.scorelive.model;

import com.example.scorelive.data.TeamsData;
import com.google.gson.annotations.SerializedName;

public class Event {
    @SerializedName("idEvent")
    private int idEvent;
    @SerializedName("idHomeTeam")
    private int idHomeTeam;
    @SerializedName("idAwayTeam")
    private int idAwayTeam;
    @SerializedName("intHomeScore")
    private int homeScore;
    @SerializedName("intAwayScore")
    private int awayScore;
    @SerializedName("strVenue")
    private String venue;
    @SerializedName("strTimestamp")
    private String timestamp;
    @SerializedName("strSeason")
    private String season;
    @SerializedName("strThumb")
    private String thumbnail;
    @SerializedName("strVideo")
    private String videoLink;
    @SerializedName("strStatus")
    private String status;

    private Team homeTeam;
    private Team awayTeam;

    public int getIdHomeTeam() {
        return idHomeTeam;
    }

    public void setIdHomeTeam(int idHomeTeam) {
        this.idHomeTeam = idHomeTeam;
    }

    public int getIdAwayTeam() {
        return idAwayTeam;
    }

    public void setIdAwayTeam(int idAwayTeam) {
        this.idAwayTeam = idAwayTeam;
        this.awayTeam = TeamsData.findTeamById(this.idAwayTeam);
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam() { this.homeTeam = TeamsData.findTeamById(this.idHomeTeam); }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam() { this.awayTeam = TeamsData.findTeamById(this.idAwayTeam); }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
}
