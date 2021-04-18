package com.example.tuned.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.adamratzman.spotify.SpotifyAppApi;
import com.adamratzman.spotify.models.Album;
import com.example.tuned.R;
import com.example.tuned.Spotify.Spotify;
import com.example.tuned.adapters.ResultAdapter;
import com.example.tuned.models.Result;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    static Spotify spotify = new Spotify();
    static SpotifyAppApi api = spotify.api;
    static ArrayList<com.example.tuned.models.Album> newReleases = spotify.getNewReleases(api);

    public static ArrayList<Result> resultList = new ArrayList<Result>();

    private ListView listView;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpData();
        setUpList();
        setUpOnClickListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        SearchView searchView = (SearchView) view.findViewById(R.id.svBar);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<Album> filteredAlbums = new ArrayList<>();

                for (Album album : albumList) {
                    if (album.getName().toLowerCase().contains(s.toLowerCase())) {
                        filteredAlbums.add(album);
                    }
                }

                AlbumAdapter = new AlbumAdapter

                return false;
            }
        });
        */

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    private void setUpData() {
        //Result album = new Result()
    }

    private void setUpList() {
        listView = (ListView) getView().findViewById(R.id.lvResults);

        ResultAdapter adapter = new ResultAdapter(getContext(), 0, resultList);
        listView.setAdapter(adapter);
    }

    private void setUpOnClickListener() {

    }
}