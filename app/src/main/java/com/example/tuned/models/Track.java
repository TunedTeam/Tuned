package com.example.tuned.models;

public class Track implements SearchResults{

    public String trackId;
    public String trackImageUrl;
    public String trackName;
    public String trackArtist;
    public int trackReleaseDate;

    public Track(String trackId, String trackImageUrl, String trackName, String trackArtist, int trackReleaseDate) {
        this.trackId = trackId;
        this.trackImageUrl = trackImageUrl;
        this.trackName = trackName;
        this.trackArtist = trackArtist;
        this.trackReleaseDate = trackReleaseDate;
    }

    @Override
    public String getName() {
        return trackName;
    }

    @Override
    public String getType() {
        return "track";
    }

    @Override
    public String getImage() {
        return trackImageUrl;
    }

    @Override
    public String getId() {
        return trackId;
    }

    @Override
    public String getArtist() {
        return trackArtist;
    }

    public int getReleaseDate() {
        return trackReleaseDate;
    }
}
