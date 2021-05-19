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

    @Override
    public String getImage() {
        return artistImageUrl;
    }

    @Override
    public String getId() {
        return artistId;
    }

    @Override
    public String getArtist() {
        return null;
    }

    public int getReleaseDate() {
        return 0;
    }

    public String getAlbumId() {
        return null;
    }

    public String getAlbumName() {
        return null;
    }

    public String getAlbumArtist() {
        return null;
    }

    public String getPreviewUrl() {
        return null;
    }
}
