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

    @Override
    public int getReleaseDate() {
        return 0;
    }

    @Override
    public String getAlbumId() {
        return null;
    }

    @Override
    public String getAlbumName() {
        return null;
    }

    @Override
    public String getAlbumArtist() {
        return null;
    }

    @Override
    public String getPreviewUrl() {
        return null;
    }

    @Override
    public String getISRC() {
        return null;
    }

    @Override
    public int getTotalTracks() {
        return 0;
    }

    @Override
    public String getTrackURI() {
        return null;
    }
}
