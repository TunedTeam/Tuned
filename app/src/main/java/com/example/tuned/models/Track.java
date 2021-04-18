package com.example.tuned.models;

public class Track {

    public String trackId;
    public String trackImageUrl;
    public String trackName;
    public String trackArtist;



    public Track(String trackId, String trackImageUrl, String trackName, String trackArtist) {
        this.trackId = trackId;
        this.trackImageUrl = trackImageUrl;
        this.trackName = trackName;
        this.trackArtist = trackArtist;
    }
}
