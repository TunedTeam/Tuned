package com.example.tuned.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tuned.R;

/**
 * A simple {@link Fragment} subclass..
 */
public class DiscoverFragment extends Fragment {

    private Button btnDiscover;
    private Button btnReviews;
    private Button btnLists;


    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here. E.g. view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentManager childFragment = getChildFragmentManager();

        FragmentTransaction discoverTransaction = childFragment.beginTransaction();
        Fragment discoverFeedFragment = new DiscoverFeedFragment();
        discoverTransaction.replace(R.id.child_fragment_container, discoverFeedFragment).commit();

        btnDiscover = view.findViewById(R.id.btnDiscover);
        btnReviews = view.findViewById(R.id.btnReviews);
        btnLists = view.findViewById(R.id.btnLists);

        // TODO: show a darker color on button when selected / inside designated button fragment
        // TODO: make button size bigger
        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction discoverTransaction = childFragment.beginTransaction();
                Fragment discoverFeedFragment = new DiscoverFeedFragment();
                discoverTransaction.replace(R.id.child_fragment_container, discoverFeedFragment).commit();
            }
        });

        btnReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction reviewsTransaction = childFragment.beginTransaction();
                // Fragment reviewsFeedFragment = new ReviewsFeedFragment();
                // reviewsTransaction.replace(R.id.child_fragment_container, reviewsFeedFragment).commit();
            }
        });

        btnLists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction listsTransaction = childFragment.beginTransaction();
                Fragment listsFeedFragment = new ListsFeedFragment();
                listsTransaction.replace(R.id.child_fragment_container, listsFeedFragment).commit();
            }
        });

        //TODO: fix it so that clicking on discover fragment is by default launching discoverFeed
    }
}