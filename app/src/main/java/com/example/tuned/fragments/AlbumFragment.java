package com.example.tuned.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adamratzman.spotify.SpotifyAppApi;
import com.example.tuned.R;
import com.example.tuned.spotify.Spotify;
import com.example.tuned.adapters.AlbumAdapter;
import com.example.tuned.models.Track;

import java.util.ArrayList;

public class AlbumFragment extends Fragment {

    private static final String TAG = "AlbumFragment";

    static Spotify spotify = new Spotify();
    static SpotifyAppApi api = spotify.api;
    static ArrayList<Track> tracks = spotify.getAlbumTracks(api, "");

    private RecyclerView rvListTracks;

    public AlbumFragment() {
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
        View view = inflater.inflate(R.layout.fragment_album, container, false);

        LinearLayoutManager layoutManagerNewReleases = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvListTracks = view.findViewById(R.id.rvListTracks);
        rvListTracks.setLayoutManager(layoutManagerNewReleases);
        AlbumAdapter rvAdapterAlbum = new AlbumAdapter(getContext(), tracks);
        rvListTracks.setAdapter(rvAdapterAlbum);


        return view;
    }
}