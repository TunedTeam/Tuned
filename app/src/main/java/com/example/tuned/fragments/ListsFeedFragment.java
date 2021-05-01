package com.example.tuned.fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuned.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;


public class ListsFeedFragment extends Fragment {

    private ExtendedFloatingActionButton fabDiscover;
    private ExtendedFloatingActionButton fabReviews;
    private ExtendedFloatingActionButton fabLists;

    public ListsFeedFragment() {
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
        return inflater.inflate(R.layout.fragment_lists_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fabDiscover = view.findViewById(R.id.fabDiscover);
        fabReviews = view.findViewById(R.id.fabReviews);
        fabLists = view.findViewById(R.id.fabLists);

        // set current fab color to purple to differentiate from other fabs
        fabLists.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple)));

        fabDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiscoverFeedFragment discoverFeedFragment = new DiscoverFeedFragment();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContainer, discoverFeedFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

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

    }
}