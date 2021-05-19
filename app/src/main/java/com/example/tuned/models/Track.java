package com.example.tuned.models;

public class Track implements SearchResults{

    public String trackId;
    public String trackImageUrl;
    public String trackName;
    public String trackArtist;
    public int trackReleaseDate;
    public String trackAlbumId;
    public String trackAlbumName;
    public String trackAlbumArtist;
    public String trackPreviewUrl;


    public Track(String trackId, String trackImageUrl, String trackName, String trackArtist, int trackReleaseDate, String trackAlbumId, String trackAlbumName, String trackAlbumArtist, String trackPreviewUrl) {
        this.trackId = trackId;
        this.trackImageUrl = trackImageUrl;
        this.trackName = trackName;
        this.trackArtist = trackArtist;
        this.trackReleaseDate = trackReleaseDate;
        this.trackAlbumId = trackAlbumId;
        this.trackAlbumName = trackAlbumName;
        this.trackAlbumArtist = trackAlbumArtist;
        this.trackPreviewUrl = trackPreviewUrl;
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

    public String getAlbumId() {
        return trackAlbumId;
    }

    public String getAlbumName() {
        return trackAlbumName;
    }

    public String getAlbumArtist() {
        return trackAlbumArtist;
    }

    @Override
    public String getPreviewUrl() {
        return trackPreviewUrl;
    }
}
