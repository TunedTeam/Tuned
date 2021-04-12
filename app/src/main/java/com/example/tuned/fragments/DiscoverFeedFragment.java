package com.example.tuned.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adamratzman.spotify.SpotifyAppApi;
import com.example.tuned.adapters.NewReleasesAdapter;
import com.example.tuned.adapters.PopularAlbumsAdapter;
import com.example.tuned.adapters.TopTracksAdapter;
import com.example.tuned.models.Album;
import com.example.tuned.R;
import com.example.tuned.Spotify.Spotify;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFeedFragment extends Fragment {

    private static final String TAG = "DiscoverFeedFragment";

    static Spotify spotify = new Spotify();
    static SpotifyAppApi api = spotify.api;
    static ArrayList<Album> albums = spotify.getNewReleases(api);

    private RecyclerView rvNewReleases;
    private RecyclerView rvPopularAlbums;
    private RecyclerView rvTopTracks;


    public DiscoverFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_discover_feed, container, false);

        LinearLayoutManager layoutManagerNewReleases = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvNewReleases = view.findViewById(R.id.rvNewReleases);
        rvNewReleases.setLayoutManager(layoutManagerNewReleases);
        NewReleasesAdapter rvAdapterNewReleases = new NewReleasesAdapter(getContext(), albums);
        rvNewReleases.setAdapter(rvAdapterNewReleases);

        LinearLayoutManager layoutManagerPopularAlbum = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvPopularAlbums = view.findViewById(R.id.rvPopularAlbumWeek);
        rvPopularAlbums.setLayoutManager(layoutManagerPopularAlbum);
        PopularAlbumsAdapter rvAdapterPopularAlbum = new PopularAlbumsAdapter(getContext(), albums);
        rvPopularAlbums.setAdapter(rvAdapterPopularAlbum);

        LinearLayoutManager layoutManagerTopTracks = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvTopTracks = view.findViewById(R.id.rvTopTracks);
        rvTopTracks.setLayoutManager(layoutManagerTopTracks);
        TopTracksAdapter rvAdapterTopTracks = new TopTracksAdapter(getContext(), albums);
        rvTopTracks.setAdapter(rvAdapterTopTracks);

        return view;
    }

}