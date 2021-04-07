package com.example.tuned.adapters;

import com.adamratzman.spotify.SpotifyApiBuilderKt;
import com.adamratzman.spotify.SpotifyAppApi;
import com.adamratzman.spotify.javainterop.SpotifyContinuation;
import com.adamratzman.spotify.models.PagingObject;
import com.adamratzman.spotify.models.SimpleAlbum;

import org.jetbrains.annotations.NotNull;

public class NewReleasesAdapter {

    private static final String SPOTIFY_CLIENT_ID = "820ef0ead03b403184b46dfe49c3b92e";
    private static final String SPOTIFY_CLIENT_SECRET = "fbf6f2f272614dbda6b9c06c10b870af";

    static SpotifyAppApi api;

}
