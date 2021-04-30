package com.example.tuned.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import android.content.Intent;
import com.adamratzman.spotify.SpotifyAppApi;
import com.example.tuned.adapters.NewReleasesAdapter;
import com.example.tuned.adapters.PopularWeekAdapter;
import com.example.tuned.adapters.TopTracksAdapter;
import com.example.tuned.models.Album;
import com.example.tuned.R;
import com.example.tuned.Spotify.Spotify;
import com.example.tuned.models.SearchResults;
import com.example.tuned.models.Track;

import java.util.ArrayList;

public class DiscoverFeedFragment extends Fragment {

    private static final String TAG = "DiscoverFeedFragment";

    static Spotify spotify = new Spotify();
    static SpotifyAppApi api = spotify.api;
    static ArrayList<Album> newReleases = spotify.getNewReleases(api);
    static ArrayList<Track> popularWeek = spotify.getPopularWeek(api);
    static ArrayList<Track> topTracks = spotify.getTopTracks(api);

    private RecyclerView rvNewReleases;
    private RecyclerView rvPopularWeek;
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
        NewReleasesAdapter rvAdapterNewReleases = new NewReleasesAdapter(getContext(), newReleases);
        rvNewReleases.setAdapter(rvAdapterNewReleases);

        LinearLayoutManager layoutManagerTopTracks = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvTopTracks = view.findViewById(R.id.rvTopTracks);
        rvTopTracks.setLayoutManager(layoutManagerTopTracks);
        TopTracksAdapter rvAdapterTopTracks = new TopTracksAdapter(getContext(), topTracks);
        rvTopTracks.setAdapter(rvAdapterTopTracks);

        LinearLayoutManager layoutManagerPopularAlbum = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvPopularWeek = view.findViewById(R.id.rvPopularWeek);
        rvPopularWeek.setLayoutManager(layoutManagerPopularAlbum);
        PopularWeekAdapter rvAdapterPopularWeek = new PopularWeekAdapter(getContext(), popularWeek);
        rvPopularWeek.setAdapter(rvAdapterPopularWeek);



        return view;
    }


    private void setUpOnClickListener(ListView listView, ArrayList<Album> getNewReleases) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String resultType = "";

                if (getNewReleases.get(i).getType().equals("album")) {
                    String albumId = getNewReleases.get(i).getId();

                    resultType = "album";

                    Bundle bundle = new Bundle();

                    bundle.putString("resultId", albumId);

                    AlbumFragment albumFragment = new AlbumFragment();
                    albumFragment.setArguments(bundle);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.flContainer, albumFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            }
        });


    }

}