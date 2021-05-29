package com.example.tuned.Spotify;

import android.content.Intent;
import android.util.Log;

import com.adamratzman.spotify.SpotifyApiBuilderKt;
import com.adamratzman.spotify.SpotifyAppApi;
import com.adamratzman.spotify.SpotifyRestAction;
import com.adamratzman.spotify.javainterop.SpotifyContinuation;
import com.adamratzman.spotify.models.PagingObject;
import com.adamratzman.spotify.models.PlayableUri;
import com.adamratzman.spotify.models.PlaylistTrack;
import com.adamratzman.spotify.models.ReleaseDate;
import com.adamratzman.spotify.models.SimpleAlbum;
import com.adamratzman.spotify.models.SimpleArtist;
import com.adamratzman.spotify.models.SimpleTrack;
import com.adamratzman.spotify.models.SpotifySearchResult;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.tuned.StreamingActivity;
import com.example.tuned.models.Album;
import com.example.tuned.models.Artist;
import com.example.tuned.models.SearchResults;
import com.example.tuned.models.Track;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kotlin.coroutines.Continuation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Spotify {

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
        int albumReleaseDate;
        int totalTracks;


        for (SimpleAlbum album : browseNewReleases.getItems()) {

            albumIds = album.getId();
            albumImageUrls = album.getImages().get(0).getUrl();
            albumNames = album.getName();
            albumArtists = album.getArtists().get(0).getName();
            albumReleaseDate = album.getReleaseDate().getYear();
            totalTracks = album.getTotalTracks();

            albumInfo = new Album(albumIds, albumImageUrls, albumNames, albumArtists, albumReleaseDate, totalTracks);

            albumArrayList.add(albumInfo);
        }

        return albumArrayList;
    }

    public static ArrayList<Track> getPopularWeek(SpotifyAppApi api) {
        // Playlist - Billboard Top 100 - Popular Songs of the Week
        PagingObject<PlaylistTrack> playlistTopTracks = api.getPlaylists().getPlaylistTracksRestAction("6UeSakyzhiEt4NB3UAd6NQ", 10, null, null).complete();

        ArrayList<Track> tracksArrayList = new ArrayList<>();

        Track trackInfo;

        String trackIds;
        String trackAlbumIds;
        String trackImageUrls;
        String trackNames;
        String trackAlbumNames;
        String trackArtists;
        String trackAlbumArtists;
        int trackReleaseDate;
        String trackPreviewUrl;
        String trackISRC;

        for (PlaylistTrack track : playlistTopTracks.getItems()) {

            trackIds = track.getTrack().getAsTrack().getId();
            trackAlbumIds = track.getTrack().getAsTrack().getAlbum().getId();
            trackImageUrls = track.getTrack().getAsTrack().getAlbum().getImages().get(0).getUrl();
            trackNames = track.getTrack().getAsTrack().getName();
            trackAlbumNames = track.getTrack().getAsTrack().getAlbum().getName();
            trackArtists = track.getTrack().getAsTrack().getAlbum().getArtists().get(0).getName();
            trackAlbumArtists = track.getTrack().getAsTrack().getAlbum().getArtists().get(0).getName();
            trackReleaseDate = track.getTrack().getAsTrack().getAlbum().getReleaseDate().getYear();
            trackPreviewUrl = track.getTrack().getAsTrack().getPreviewUrl();
            trackISRC = track.getTrack().getAsTrack().getExternalIds().get(0).getId();

            trackInfo = new Track(trackIds, trackImageUrls, trackNames, trackArtists, trackReleaseDate, trackAlbumIds, trackAlbumNames, trackAlbumArtists, trackPreviewUrl, trackISRC, null);

            tracksArrayList.add(trackInfo);
        }

        return tracksArrayList;
    }


    public static ArrayList<Track> getTopTracks(SpotifyAppApi api) {
        // Playlist - Top 100 Songs Global
        PagingObject<PlaylistTrack> playlistTopTracks = api.getPlaylists().getPlaylistTracksRestAction("37i9dQZEVXbNG2KDcFcKOF", 10, null, null).complete();

        ArrayList<Track> tracksArrayList = new ArrayList<>();

        Track trackInfo;

        String trackIds;
        String trackAlbumIds;
        String trackImageUrls;
        String trackNames;
        String trackAlbumNames;
        String trackArtists;
        String trackAlbumArtists;
        int trackReleaseDate;
        String trackPreviewUrl;
        String trackISRC;

        for (PlaylistTrack track : playlistTopTracks.getItems()) {

            trackIds = track.getTrack().getAsTrack().getId();
            trackAlbumIds = track.getTrack().getAsTrack().getAlbum().getId();
            trackImageUrls = track.getTrack().getAsTrack().getAlbum().getImages().get(0).getUrl();
            trackNames = track.getTrack().getAsTrack().getName();
            trackAlbumNames = track.getTrack().getAsTrack().getAlbum().getName();
            trackArtists = track.getTrack().getAsTrack().getAlbum().getArtists().get(0).getName();
            trackAlbumArtists = track.getTrack().getAsTrack().getAlbum().getArtists().get(0).getName();
            trackReleaseDate = track.getTrack().getAsTrack().getAlbum().getReleaseDate().getYear();
            trackPreviewUrl = track.getTrack().getAsTrack().getPreviewUrl();
            trackISRC = track.getTrack().getAsTrack().getExternalIds().get(0).getId();

            trackInfo = new Track(trackIds, trackImageUrls, trackNames, trackArtists, trackReleaseDate, trackAlbumIds, trackAlbumNames, trackAlbumArtists, trackPreviewUrl, trackISRC, null);

            tracksArrayList.add(trackInfo);
        }

        return tracksArrayList;
    }

    public static ArrayList<SearchResults> searchAlbumResult(SpotifyAppApi api, String query) {

        SpotifyRestAction<SpotifySearchResult> searchResult = api.getSearch().searchAllTypesRestAction(query, 10, null, null);

        ArrayList<SearchResults> albumArrayList = new ArrayList<>();

        Album albumInfo;

        String albumIds;
        String albumImageUrls;
        String albumNames;
        String albumArtists;
        int albumReleaseDate;
        int totalTracks;

        for (SimpleAlbum album : searchResult.complete().getAlbums().getItems()) {

            albumIds = album.getId();
            albumImageUrls = album.getImages().get(0).getUrl();
            albumNames = album.getName();
            albumArtists = album.getArtists().get(0).getName();
            albumReleaseDate = album.getReleaseDate().getYear();
            totalTracks = album.getTotalTracks();

            albumInfo = new Album(albumIds, albumImageUrls, albumNames, albumArtists, albumReleaseDate, totalTracks);

            albumArrayList.add(albumInfo);
        }

        return albumArrayList;
    }

    public static ArrayList<SearchResults> searchArtistResult(SpotifyAppApi api, String query) {

        SpotifyRestAction<SpotifySearchResult> searchResult = api.getSearch().searchAllTypesRestAction(query, 10, null, null);

        ArrayList<SearchResults> artistArrayList = new ArrayList<>();

        Artist artistInfo;

        String artistIds;
        String artistImageUrls;
        String artistNames;


        for (com.adamratzman.spotify.models.Artist artist : searchResult.complete().getArtists().getItems()) {

            artistIds = artist.getId();
            try {
                artistImageUrls = artist.getImages().get(0).getUrl();
            } catch (Exception e) {
                artistImageUrls = "";
            }
            artistNames = artist.getName();

            artistInfo = new Artist(artistIds, artistImageUrls, artistNames);

            artistArrayList.add(artistInfo);
        }

        return artistArrayList;
    }

    public static ArrayList<SearchResults> searchTrackResult(SpotifyAppApi api, String query) {

        SpotifyRestAction<SpotifySearchResult> searchResult = api.getSearch().searchAllTypesRestAction(query, 10, null, null);

        ArrayList<SearchResults> trackArrayList = new ArrayList<>();

        Track trackInfo;

        String trackIds;
        String trackAlbumIds;
        String trackImageUrls;
        String trackNames;
        String trackAlbumNames;
        String trackArtists;
        String trackAlbumArtists;
        int trackReleaseDate;
        String trackPreviewUrl;
        String trackISRC;

        for (com.adamratzman.spotify.models.Track track : searchResult.complete().getTracks().getItems()) {

            trackIds = track.getAsTrack().getId();
            trackAlbumIds = track.getAlbum().getId();
            trackImageUrls = track.getAsTrack().getAlbum().getImages().get(0).getUrl();
            trackNames = track.getAsTrack().getName();
            trackAlbumNames = track.getAlbum().getName();
            trackArtists = track.getArtists().get(0).getName();
            trackAlbumArtists = track.getAlbum().getArtists().get(0).getName();
            trackReleaseDate = track.getAsTrack().getAlbum().getReleaseDate().getYear();
            trackPreviewUrl = track.getAsTrack().getPreviewUrl();
            trackISRC = track.getAsTrack().getExternalIds().get(0).getId();

            trackInfo = new Track(trackIds, trackImageUrls, trackNames, trackArtists, trackReleaseDate, trackAlbumIds, trackAlbumNames, trackAlbumArtists, trackPreviewUrl, trackISRC, null);

            trackArrayList.add(trackInfo);
        }

        return trackArrayList;
    }

    public static ArrayList<Track> getAlbumTracks(SpotifyAppApi api, String albumId) {

        SpotifyRestAction<PagingObject<SimpleTrack>> albumTracks = api.getAlbums().getAlbumTracksRestAction(albumId, 50, null, null);

        ArrayList<Track> trackArrayList = new ArrayList<>();

        Track trackInfo;

        String trackId;
        String trackName;
        String trackArtists;
        String trackPreviewUrl;
        String trackAlbumId;
        String trackISRC;

        for (SimpleTrack track : albumTracks.complete().getItems()) {

            trackId = track.getId();
            trackName = track.getName();
            trackArtists = track.getArtists().get(0).getName();
            trackPreviewUrl = track.getPreviewUrl();
            trackAlbumId = albumId;
            trackISRC = getTrackISRC(api, track.getId());

            trackInfo = new Track(trackId, "", trackName, trackArtists, 0, trackAlbumId, null, null, trackPreviewUrl, trackISRC, null);

            trackArrayList.add(trackInfo);
        }

        return trackArrayList;
    }


    public static String getAlbumName(SpotifyAppApi api, String albumId) {
        SpotifyRestAction<com.adamratzman.spotify.models.Album> albumRestAction = api.getAlbums().getAlbumRestAction(albumId, null);

        String albumName = albumRestAction.complete().getName();

        return albumName;
    }

    public static String getAlbumArtist(SpotifyAppApi api, String albumId) {
        SpotifyRestAction<com.adamratzman.spotify.models.Album> albumRestAction = api.getAlbums().getAlbumRestAction(albumId, null);

        String albumArtist = albumRestAction.complete().getArtists().get(0).getName();

        return albumArtist;
    }

    public static String getAlbumImage(SpotifyAppApi api, String albumId) {
        SpotifyRestAction<com.adamratzman.spotify.models.Album> albumRestAction = api.getAlbums().getAlbumRestAction(albumId, null);

        String albumArtist = albumRestAction.complete().getImages().get(0).getUrl();

        return albumArtist;
    }

    public static String getTrackISRC(SpotifyAppApi api, String trackId) {
        SpotifyRestAction<com.adamratzman.spotify.models.Track> trackRestAction = api.getTracks().getTrackRestAction(trackId, null);

        String trackISRC = trackRestAction.complete().getExternalIds().get(0).getId();

        return trackISRC;
    }

    public static int getAlbumReleaseDate(SpotifyAppApi api, String albumId) {
        SpotifyRestAction<com.adamratzman.spotify.models.Album> albumRestAction = api.getAlbums().getAlbumRestAction(albumId, null);

        int albumReleaseDate = albumRestAction.complete().getReleaseDate().getYear();

        return albumReleaseDate;
    }

    public static int getTotalTracks(SpotifyAppApi api, String albumId) {
        SpotifyRestAction<com.adamratzman.spotify.models.Album> albumRestAction = api.getAlbums().getAlbumRestAction(albumId, null);

        int totalTracks = albumRestAction.complete().getTotalTracks();

        return totalTracks;
    }
}