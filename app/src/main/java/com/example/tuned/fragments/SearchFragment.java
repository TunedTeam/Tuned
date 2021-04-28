package com.example.tuned.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SearchView;

import com.adamratzman.spotify.SpotifyAppApi;
import com.example.tuned.R;
import com.example.tuned.Spotify.Spotify;
import com.example.tuned.adapters.ResultAdapter;
import com.example.tuned.models.SearchResults;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

   // List<T> arrList = new ArrayList<T>();
    private static final String TAG = "SearchFragment";

    static Spotify spotify = new Spotify();
    static SpotifyAppApi api = spotify.api;

    BottomNavigationView bnv;

    private ListView listView;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpOnClickListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // hide the bnv when keyboard pops up
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        SearchView searchView = (SearchView) view.findViewById(R.id.svBar);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //TODO: add a message if no results appear
                listView = (ListView) getView().findViewById(R.id.lvResults);
                ResultAdapter adapter;

                if (!s.equals("")) {
                    ArrayList<SearchResults> searchResults = spotify.searchAlbumResult(api, s);
                    searchResults.addAll(spotify.searchTrackResult(api, s));
                    searchResults.addAll(spotify.searchArtistResult(api, s));

                    adapter = new ResultAdapter(getContext(), android.R.layout.simple_list_item_1, searchResults);
                    listView.setAdapter(adapter);
                }
                else {
                    listView.setAdapter(null);
                }

                return false;
            }
        });



        return view;
    }

    private void setUpOnClickListener() {
        Log.i(TAG, "Clicked on");
    }

    private void removeBNV(View view) {
        bnv = getView().findViewById(R.id.bottom_navigation);
        //bnv.setV
    }
}