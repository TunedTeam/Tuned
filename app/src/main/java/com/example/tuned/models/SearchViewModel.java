package com.example.tuned.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.adamratzman.spotify.SpotifyAppApi;
import com.example.tuned.Spotify.Spotify;

import java.util.ArrayList;

public class SearchViewModel extends ViewModel {

    static Spotify spotify = new Spotify();
    static SpotifyAppApi api = spotify.api;

    String query = "abc";

    ArrayList<Album> searchAlbumResult;
    ArrayList<Artist> searchArtistResult;
    ArrayList<Artist> searchTrackResult;

    public void grabAlbumResults(String query) {
        ArrayList<Album> searchAlbumResult = spotify.searchAlbumResult(api, query);
    }

    public void grabArtistResults(String query) {
        ArrayList<Artist> searchArtistResult = spotify.searchArtistResult(api, query);
    }

    public void grabTrackResults(String query) {
        ArrayList<Track> searchTrackResult = spotify.searchTrackResult(api, query);
    }


    LiveData<ArrayList<Album>> albumsLiveData = spotify.searchAlbumResult(api, query);
    LiveData<ArrayList<Artist>> artistsLiveData = searchArtistResult;
    LiveData<ArrayList<Track>> tracksLiveData = searchTrackResult;

    MediatorLiveData<ArrayList> liveDataMerger = new MediatorLiveData<ArrayList>();

    //liveDataMerger.
}
