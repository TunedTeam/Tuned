package com.example.tuned.fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.adamratzman.spotify.SpotifyAppApi;
import com.example.tuned.R;
import com.example.tuned.Spotify.Spotify;
import com.example.tuned.adapters.ResultAdapter;
import com.example.tuned.models.Album;
import com.example.tuned.models.SearchResults;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class CreateReviewSearchFragment extends Fragment {

    // List<T> arrList = new ArrayList<T>();
    private static final String TAG = "CreateReviewSearchFragment";

    static Spotify spotify = new Spotify();
    static SpotifyAppApi api = spotify.api;

    FloatingActionButton fabCreate;

    private ListView listView;

//    private TextView tvCancel;

    public CreateReviewSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setUpOnClickListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_create_review_search, container, false);

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
                } else {
                    listView.setAdapter(null);
                }

                return false;
            }
        });

//        tvCancel = view.findViewById(R.id.tvCancel);
//
//        tvCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().onBackPressed();
//            }
//        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // hides the bnv when keyboard pops up
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        fabCreate = view.findViewById(R.id.fabCreate);
//        fabCreate.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.lavender)));
        //fabCreate.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(Objects.requireNonNull(getContext()),R.color.lavender)));
    }

    private void setUpOnClickListener(ListView listView, ArrayList<SearchResults> searchResults) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String resultType = "";

                if (searchResults.get(i).getType().equals("album")) {
                    String albumId = searchResults.get(i).getId();
                    String albumImageUrl = searchResults.get(i).getImage();
                    String albumName = searchResults.get(i).getName();
                    String albumArtist = searchResults.get(i).getArtist();
                    int albumReleaseDate = searchResults.get(i).getReleaseDate();
                    resultType = "album";

                    Bundle bundle = new Bundle();

                    bundle.putString("resultId", albumId);
                    bundle.putString("resultImageUrl", albumImageUrl);
                    bundle.putString("resultName", albumName);
                    bundle.putString("resultArtist", albumArtist);
                    bundle.putInt("resultReleaseDate", albumReleaseDate);
                    bundle.putString("resultType", resultType);

                    CreateReviewFragment createReviewFragment = new CreateReviewFragment();
                    createReviewFragment.setArguments(bundle);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.flContainer, createReviewFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                } else if (searchResults.get(i).getType().equals("artist")) {
                    String artistId = searchResults.get(i).getId();
                    String artistImageUrl = searchResults.get(i).getImage();
                    String artistName = searchResults.get(i).getName();
                    resultType = "artist";

                    Bundle bundle = new Bundle();

                    bundle.putString("resultId", artistId);
                    bundle.putString("resultImageUrl", artistImageUrl);
                    bundle.putString("resultName", artistName);
                    bundle.putString("resultType", resultType);

                    CreateReviewFragment createReviewFragment = new CreateReviewFragment();
                    createReviewFragment.setArguments(bundle);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.flContainer, createReviewFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                } else if (searchResults.get(i).getType().equals("track")) {
                    String trackId = searchResults.get(i).getId();
                    String trackImageUrl = searchResults.get(i).getImage();
                    String trackName = searchResults.get(i).getName();
                    String trackArtist = searchResults.get(i).getArtist();
                    int trackReleaseDate = searchResults.get(i).getReleaseDate();
                    resultType = "track";

                    Bundle bundle = new Bundle();

                    bundle.putString("resultId", trackId);
                    bundle.putString("resultImageUrl", trackImageUrl);
                    bundle.putString("resultName", trackName);
                    bundle.putString("resultArtist", trackArtist);
                    bundle.putInt("resultReleaseDate", trackReleaseDate);
                    bundle.putString("resultType", resultType);

                    CreateReviewFragment createReviewFragment = new CreateReviewFragment();
                    createReviewFragment.setArguments(bundle);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.flContainer, createReviewFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            }
        });
    }
}