package com.example.tuned.models;

import java.io.Serializable;

public class Album implements SearchResults, Serializable {

    public String albumId;
    public String albumImageUrl;
    public String albumName;
    public String albumArtist;
    public int albumReleaseDate;
    public int totalTracks;

    public Album(String albumId, String albumImageUrl, String albumName, String albumArtist, int albumReleaseDate, int totalTracks) {
        this.albumId = albumId;
        this.albumImageUrl = albumImageUrl;
        this.albumName = albumName;
        this.albumArtist = albumArtist;
        this.albumReleaseDate = albumReleaseDate;
        this.totalTracks = totalTracks;
    }

    @Override
    public String getName() {
        return albumName;
    }

    @Override
    public String getType() {
        return "album";
    }

    @Override
    public String getImage() {
        return albumImageUrl;
    }

    @Override
    public String getId() {
        return albumId;
    }

    @Override
    public String getArtist() {
        return albumArtist;
    }

    @Override
    public int getReleaseDate() {
        return albumReleaseDate;
    }

    public int getTotalTracks(){
        return totalTracks;
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
