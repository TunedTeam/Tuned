package com.example.tuned.models;

public class Search {
    private final AlbumSub albumSub;
    private final ArtistSub artistSub;
    private final TrackSub trackSub;

    public Search (AlbumSub albumSub, ArtistSub artistSub, TrackSub trackSub) {
        this.albumSub = albumSub;
        this.artistSub = artistSub;
        this.trackSub = trackSub;
    }
}

class AlbumSub {

    public String albumId;
    public String albumImageUrl;
    public String albumName;
    public String albumArtist;

    public AlbumSub(String albumId, String albumImageUrl, String albumName, String albumArtist) {
        this.albumId = albumId;
        this.albumImageUrl = albumImageUrl;
        this.albumName = albumName;
        this.albumArtist = albumArtist;
    }

    public AlbumSub() {

    }
}

class ArtistSub {

    public String artistId;
    public String artistImageUrl;
    public String artistName;

    public ArtistSub(String artistId, String artistImageUrl, String artistName) {
        this.artistId = artistId;
        this.artistImageUrl = artistImageUrl;
        this.artistName = artistName;
    }

    public ArtistSub() {

    }
}

class TrackSub {

    public String trackId;
    public String trackImageUrl;
    public String trackName;
    public String trackArtist;

    public TrackSub(String trackId, String trackImageUrl, String trackName, String trackArtist) {
        this.trackId = trackId;
        this.trackImageUrl = trackImageUrl;
        this.trackName = trackName;
        this.trackArtist = trackArtist;
    }

    public TrackSub() {

    }
}


