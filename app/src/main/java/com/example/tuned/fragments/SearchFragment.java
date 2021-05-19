package com.example.tuned.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.adamratzman.spotify.SpotifyAppApi;
import com.example.tuned.AlbumActivity;
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
                    setUpOnClickListener(listView, searchResults);
                }
                else {
                    listView.setAdapter(null);
                }

                return false;
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // hides the bnv when keyboard pops up
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void setUpOnClickListener(ListView listView, ArrayList<SearchResults> searchResults) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String resultType = "";

                if (searchResults.get(i).getType().equals("album")) {
                    String albumId = searchResults.get(i).getId();
                    String albumImage = searchResults.get(i).getImage();
                    String albumName = searchResults.get(i).getName();
                    String albumArtist = searchResults.get(i).getArtist();

                    Bundle bundle = new Bundle();

                    bundle.putString("albumId",albumId);
                    bundle.putString("albumImage",albumImage);
                    bundle.putString("albumName",albumName);
                    bundle.putString("albumArtist",albumArtist);
                    Intent intent = new Intent(getContext(), AlbumActivity.class);
                    intent.putExtras(bundle);
                    getContext().startActivity(intent);
                }
                else if (searchResults.get(i).getType().equals("track")) {

                    String albumId = searchResults.get(i).getAlbumId();
                    String albumImage = spotify.getAlbumImage(api, albumId);
                    String albumName = spotify.getAlbumName(api, albumId);
                    String albumArtist = spotify.getAlbumArtist(api, albumId);

                    Bundle bundle = new Bundle();

                    bundle.putString("albumId",albumId);
                    bundle.putString("albumImage",albumImage);
                    bundle.putString("albumName",albumName);
                    bundle.putString("albumArtist",albumArtist);
                    Intent intent = new Intent(getContext(), AlbumActivity.class);
                    intent.putExtras(bundle);
                    getContext().startActivity(intent);
                }
            }
        });
    }
}