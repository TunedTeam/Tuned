package com.example.tuned.Spotify;

import com.adamratzman.spotify.SpotifyApiBuilderKt;
import com.adamratzman.spotify.SpotifyAppApi;

public class Spotify{
    private static SpotifyAppApi instance;

    public static SpotifyAppApi getSpotifyApi () {
        if (Spotify.instance == null) {
            Spotify.instance = (SpotifyAppApi) SpotifyApiBuilderKt
                    .spotifyAppApi("clientId", "clientSecret").build(true, null);
        }
        return Spotify.instance;
    }


}