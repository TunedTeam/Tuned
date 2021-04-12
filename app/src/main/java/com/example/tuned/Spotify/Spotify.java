package com.example.tuned.Spotify;

import com.adamratzman.spotify.SpotifyApiBuilderKt;
import com.adamratzman.spotify.SpotifyAppApi;
import com.adamratzman.spotify.models.PagingObject;
import com.adamratzman.spotify.models.SimpleAlbum;
import com.example.tuned.models.Album;

import java.util.ArrayList;

public class Spotify{

    private static final String SPOTIFY_CLIENT_ID = "820ef0ead03b403184b46dfe49c3b92e";
    private static final String SPOTIFY_CLIENT_SECRET = "fbf6f2f272614dbda6b9c06c10b870af";

    public static SpotifyAppApi api = SpotifyApiBuilderKt.spotifyAppApi(SPOTIFY_CLIENT_ID, SPOTIFY_CLIENT_SECRET).buildRestAction(true).complete();

    public static ArrayList<Album> getNewReleases(SpotifyAppApi api) {

        PagingObject<SimpleAlbum> browseNewReleases = api.getBrowse().getNewReleasesRestAction(10, null, null).complete();

        ArrayList<Album> albumArrayList = new ArrayList<>();

        Album albumInfo;

        String albumIds;
        String albumImageUrls;
        String albumNames;
        String albumArtists;


        for (SimpleAlbum album : browseNewReleases.getItems()) {

            albumIds = album.getId();
            albumImageUrls = album.getImages().get(0).getUrl();
            albumNames = album.getName();
            albumArtists = album.getArtists().get(0).getName();

            albumInfo = new Album(albumIds, albumImageUrls, albumNames, albumArtists);

            albumArrayList.add(albumInfo);
        }

        return albumArrayList;
    }



}