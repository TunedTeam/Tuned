package com.example.tuned.models;

public class Album {

    public String albumId;
    public String albumImageUrl;
    public String albumName;
    public String albumArtist;



    public Album(String albumId, String albumImageUrl, String albumName, String albumArtist) {
        this.albumId = albumId;
        this.albumImageUrl = albumImageUrl;
        this.albumName = albumName;
        this.albumArtist = albumArtist;
    }

}
