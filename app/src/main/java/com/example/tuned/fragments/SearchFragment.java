package com.example.tuned.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.adamratzman.spotify.SpotifyAppApi;
import com.example.tuned.R;
import com.example.tuned.Spotify.Spotify;
import com.example.tuned.adapters.ResultAdapter;
import com.example.tuned.models.Album;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;


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

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        SearchView searchView = (SearchView) view.findViewById(R.id.svBar);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            //.findViewById(R.id.menu_bottom_navigation);
            @Override
            public boolean onQueryTextChange(String s) {
                //ArrayList<Album> searchAlbumResult = spotify.searchAlbumResult(api, s);
                listView = (ListView) getView().findViewById(R.id.lvResults);
                ResultAdapter adapter;
                if (!s.equals("")) {
                    ArrayList<Album> searchAlbumResult = spotify.searchAlbumResult(api, s);
                    adapter = new ResultAdapter(getContext(), android.R.layout.simple_list_item_1, searchAlbumResult);
                    listView.setAdapter(adapter);
                }
                else {
                    listView.setAdapter(null);
                }

                //setUpList(searchAlbumResult);

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
        //setMenuVisibility(bnv.GONE);
    }
}