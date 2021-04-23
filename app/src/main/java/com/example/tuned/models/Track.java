package com.example.tuned.models;

public class Track implements SearchResults{

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

    @Override
    public String getName() {
        return trackName;
    }

    @Override
    public String getType() {
        return "track";
    }

    public String getImage() {
        return trackImageUrl;
    }
}
