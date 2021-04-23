package com.example.tuned.models;

public class Artist implements SearchResults{

    public String artistId;
    public String artistImageUrl;
    public String artistName;

    public Artist(String artistId, String artistImageUrl, String artistName) {
        this.artistId = artistId;
        this.artistImageUrl = artistImageUrl;
        this.artistName = artistName;
    }

    @Override
    public String getName() {
        return artistName;
    }

    @Override
    public String getType() {
        return "artist";
    }

    public String getImage() {
        return artistImageUrl;
    }
}
