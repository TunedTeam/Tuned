package com.example.tuned.fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;
import android.widget.TextView;

import com.adamratzman.spotify.SpotifyAppApi;
import com.example.tuned.adapters.NewReleasesAdapter;
import com.example.tuned.adapters.PopularWeekAdapter;
import com.example.tuned.adapters.TopTracksAdapter;
import com.example.tuned.models.Album;
import com.example.tuned.R;
import com.example.tuned.spotify.Spotify;
import com.example.tuned.models.Track;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.parse.ParseUser;

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

    private ExtendedFloatingActionButton fabDiscover;
    private ExtendedFloatingActionButton fabReviews;
    private ExtendedFloatingActionButton fabLists;

    private ScrollView scrollView;

    private TextView tvWelcome;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fabDiscover = view.findViewById(R.id.fabDiscover);
        fabReviews = view.findViewById(R.id.fabReviews);
        fabLists = view.findViewById(R.id.fabLists);

        scrollView = view.findViewById(R.id.scrollView);

        tvWelcome = view.findViewById(R.id.tvWelcome);

        ParseUser parseUser = ParseUser.getCurrentUser();

        tvWelcome.setText("Welcome back, " + parseUser.getUsername() + "!");

        hideFabScroll(scrollView);

        // set current fab color to purple to differentiate from other fabs
        fabDiscover.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple)));

        fabReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReviewsFeedFragment reviewsFeedFragment = new ReviewsFeedFragment();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContainer, reviewsFeedFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        fabLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListsFeedFragment listsFeedFragment = new ListsFeedFragment();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContainer, listsFeedFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }


    public void hideFabScroll(ScrollView scrollView) {

        Animation animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        Animation animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY(); // For ScrollView
                int scrollX = scrollView.getScrollX(); // For HorizontalScrollView

                // Hide top FABs when scrolling, reappear when at the top
                if (scrollY < 0 && !fabDiscover.isShown() && !fabReviews.isShown() && !fabLists.isShown()) {
                    fabDiscover.startAnimation(animScaleDown);
                    fabReviews.startAnimation(animScaleDown);
                    fabLists.startAnimation(animScaleDown);
                } else if (scrollY > 0 && fabDiscover.isShown() && fabReviews.isShown() && fabLists.isShown()) {
                    fabDiscover.startAnimation(animScaleUp);
                    fabReviews.startAnimation(animScaleUp);
                    fabLists.startAnimation(animScaleUp);
                }
            }
        });
    }


}