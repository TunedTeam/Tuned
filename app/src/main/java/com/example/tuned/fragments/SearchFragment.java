package com.example.tuned.fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.adamratzman.spotify.SpotifyAppApi;
import com.example.tuned.activities.AlbumActivity;
import com.example.tuned.R;
import com.example.tuned.spotify.Spotify;
import com.example.tuned.adapters.ResultAdapter;
import com.example.tuned.models.SearchResults;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    // List<T> arrList = new ArrayList<T>();
    private static final String TAG = "SearchFragment";

    static Spotify spotify = new Spotify();
    static SpotifyAppApi api = spotify.api;

    BottomNavigationView bnv;

    private ListView listView;

    private ExtendedFloatingActionButton fabAll;
    private ExtendedFloatingActionButton fabAlbums;
    private ExtendedFloatingActionButton fabTracks;
    private ExtendedFloatingActionButton fabArtists;

    private boolean boolAlbum = false;
    private boolean boolTrack = false;
    private boolean boolArtist = false;

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

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        SearchView searchView = (SearchView) view.findViewById(R.id.svBar);

        fabAll = view.findViewById(R.id.fabAll);
        fabAlbums = view.findViewById(R.id.fabAlbums);
        fabTracks = view.findViewById(R.id.fabTracks);
        fabArtists = view.findViewById(R.id.fabArtists);

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

                    fabAll.setVisibility(View.VISIBLE);
                    fabAll.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple)));

                    fabAlbums.setVisibility(View.VISIBLE);
                    fabTracks.setVisibility(View.VISIBLE);
                    fabArtists.setVisibility(View.VISIBLE);

                    if (boolAlbum == true) {
                        searchResults.clear();
                        searchResults.addAll(spotify.searchAlbumResult(api, s));
                        adapter = new ResultAdapter(getContext(), android.R.layout.simple_list_item_1, searchResults);
                        listView.setAdapter(adapter);
                    }

                    if (boolTrack == true) {
                        searchResults.clear();
                        searchResults.addAll(spotify.searchTrackResult(api, s));
                        adapter = new ResultAdapter(getContext(), android.R.layout.simple_list_item_1, searchResults);
                        listView.setAdapter(adapter);
                    }

                    if (boolArtist == true) {
                        searchResults.clear();
                        searchResults.addAll(spotify.searchArtistResult(api, s));
                        adapter = new ResultAdapter(getContext(), android.R.layout.simple_list_item_1, searchResults);
                        listView.setAdapter(adapter);
                    }

                    fabAll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolAlbum = false;
                            boolTrack = false;
                            boolArtist = false;


                            fabAll.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple)));
                            fabAlbums.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_purple)));
                            fabTracks.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_purple)));
                            fabArtists.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_purple)));


                            searchResults.clear();
                            searchResults.addAll(spotify.searchAlbumResult(api, s));
                            searchResults.addAll(spotify.searchTrackResult(api, s));
                            searchResults.addAll(spotify.searchArtistResult(api, s));
                            ResultAdapter adapter = new ResultAdapter(getContext(), android.R.layout.simple_list_item_1, searchResults);
                            listView.setAdapter(adapter);

                        }
                    });

                    fabAlbums.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolAlbum = true;
                            boolTrack = false;
                            boolArtist = false;


                            fabAll.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_purple)));
                            fabAlbums.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple)));
                            fabTracks.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_purple)));
                            fabArtists.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_purple)));

                            searchResults.clear();
                            searchResults.addAll(spotify.searchAlbumResult(api, s));
                            ResultAdapter adapter = new ResultAdapter(getContext(), android.R.layout.simple_list_item_1, searchResults);
                            listView.setAdapter(adapter);
                        }
                    });

                    fabTracks.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolAlbum = false;
                            boolTrack = true;
                            boolArtist = false;


                            fabAll.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_purple)));
                            fabAlbums.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_purple)));
                            fabTracks.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple)));
                            fabArtists.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_purple)));


                            searchResults.clear();
                            searchResults.addAll(spotify.searchTrackResult(api, s));
                            ResultAdapter adapter = new ResultAdapter(getContext(), android.R.layout.simple_list_item_1, searchResults);
                            listView.setAdapter(adapter);
                        }
                    });

                    fabArtists.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolAlbum = false;
                            boolTrack = false;
                            boolArtist = true;


                            fabAll.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_purple)));
                            fabAlbums.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_purple)));
                            fabTracks.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_purple)));
                            fabArtists.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple)));


                            searchResults.clear();
                            searchResults.addAll(spotify.searchArtistResult(api, s));
                            ResultAdapter adapter = new ResultAdapter(getContext(), android.R.layout.simple_list_item_1, searchResults);
                            listView.setAdapter(adapter);
                        }
                    });

                    adapter = new ResultAdapter(getContext(), android.R.layout.simple_list_item_1, searchResults);
                    listView.setAdapter(adapter);
                    setUpOnClickListener(listView, searchResults);
                } else {
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
                    int totalTracks = searchResults.get(i).getTotalTracks();
                    int albumReleaseDate = searchResults.get(i).getReleaseDate();

                    Bundle bundle = new Bundle();

                    bundle.putString("albumId", albumId);
                    bundle.putString("albumImage", albumImage);
                    bundle.putString("albumName", albumName);
                    bundle.putString("albumArtist", albumArtist);
                    bundle.putInt("totalTracks", totalTracks);
                    bundle.putInt("albumReleaseDate", albumReleaseDate);
                    Intent intent = new Intent(getContext(), AlbumActivity.class);
                    intent.putExtras(bundle);
                    getContext().startActivity(intent);
                } else if (searchResults.get(i).getType().equals("track")) {

                    String albumId = searchResults.get(i).getAlbumId();
                    String albumImage = spotify.getAlbumImage(api, albumId);
                    String albumName = spotify.getAlbumName(api, albumId);
                    String albumArtist = spotify.getAlbumArtist(api, albumId);
                    int totalTracks = spotify.getTotalTracks(api, albumId);
                    int albumReleaseDate = spotify.getAlbumReleaseDate(api, albumId);

                    Bundle bundle = new Bundle();

                    bundle.putString("albumId", albumId);
                    bundle.putString("albumImage", albumImage);
                    bundle.putString("albumName", albumName);
                    bundle.putString("albumArtist", albumArtist);
                    bundle.putInt("totalTracks", totalTracks);
                    bundle.putInt("albumReleaseDate", albumReleaseDate);
                    Intent intent = new Intent(getContext(), AlbumActivity.class);
                    intent.putExtras(bundle);
                    getContext().startActivity(intent);
                }
            }
        });
    }

    public ArrayList<SearchResults> filterAlbums(SpotifyAppApi api, String s) {
        ArrayList<SearchResults> albumResults = spotify.searchAlbumResult(api, s);

        return albumResults;
    }

    public ArrayList<SearchResults> filterTracks(SpotifyAppApi api, String s) {
        ArrayList<SearchResults> trackResults = spotify.searchTrackResult(api, s);

        return trackResults;
    }

    public ArrayList<SearchResults> filterArtists(SpotifyAppApi api, String s) {
        ArrayList<SearchResults> artistResults = spotify.searchArtistResult(api, s);

        return artistResults;
    }
}