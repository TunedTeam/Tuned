package com.example.tuned.fragments;


import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;

import com.example.tuned.Post;
//import com.example.instagram.PostsAdapter;
import com.example.tuned.adapters.PostsAdapter;
import com.example.tuned.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ReviewsFeedFragment extends Fragment {

    public static final String TAG = "ReviewsFeedFragment";
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    private SwipeRefreshLayout swipeContainer;

    private ExtendedFloatingActionButton fabDiscover;
    private ExtendedFloatingActionButton fabReviews;
    private ExtendedFloatingActionButton fabLists;

    public ReviewsFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reviews_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Steps to use the recycler view:
        // 0. Create layout for one row in the list
        rvPosts = view.findViewById(R.id.rvPosts);

        // 1. Create the adapter
        // 2. Create the data source
        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts);

        // 3. Set the adapter on the recycler view
        rvPosts.setAdapter(adapter);

        // 4. Set the layout manager on the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        rvPosts.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                Log.i(TAG,"Fetching new data!");
                queryPosts();
            }
        });

        fabDiscover = view.findViewById(R.id.fabDiscover);
        fabReviews = view.findViewById(R.id.fabReviews);
        fabLists = view.findViewById(R.id.fabLists);

        //hideFabScroll(rvPosts);

        // set current fab color to purple to differentiate from other fabs
        fabReviews.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple)));

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

        queryPosts();
    }


    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);

        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null)
                {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                for (Post post : posts)
                {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername()
                            +post.getImage());
                }
                /*post.getUser().getParseFile("profilePicture")*/
                /*ParseUser.getCurrentUser().get("profile_picture") */
                adapter.clear();
                allPosts.addAll(posts);
                swipeContainer.setRefreshing(false);
                //adapter.notifyDataSetChanged();
            }


        });
    }

    public void hideFabScroll (RecyclerView recyclerView) {

        Animation animScaleUp = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        Animation animScaleDown = AnimationUtils.loadAnimation(getContext(), R.anim.scale_down);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx,int dy){
                super.onScrolled(recyclerView, dx, dy);

                if (dy < 0 && !fabDiscover.isShown() && !fabReviews.isShown() && !fabLists.isShown()) {
                    fabDiscover.startAnimation(animScaleDown);
                    fabReviews.startAnimation(animScaleDown);
                    fabLists.startAnimation(animScaleDown);
                } else if (dy > 0 && fabDiscover.isShown() && fabReviews.isShown() && fabLists.isShown()) {
                    fabDiscover.startAnimation(animScaleUp);
                    fabReviews.startAnimation(animScaleUp);
                    fabLists.startAnimation(animScaleUp);
                }
            }
        });
    }
}