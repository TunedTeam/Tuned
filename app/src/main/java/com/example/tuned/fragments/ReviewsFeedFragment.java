package com.example.tuned.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuned.Review;
import com.example.tuned.ReviewAdapter;
import com.example.tuned.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFeedFragment extends Fragment {

    public static final String TAG = "ReviewsFeedFragment";
    private RecyclerView rvPosts;
    protected ReviewAdapter adapter;
    protected List<Review> allPosts;
    private SwipeRefreshLayout swipeContainer;

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
        adapter = new ReviewAdapter(getContext(), allPosts);

        // 3. Set the adapter on the recycler view
        rvPosts.setAdapter(adapter);

        // 4. Set the layout manager on the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));

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

        queryPosts();
    }

    protected void queryPosts() {
        ParseQuery<Review> query = ParseQuery.getQuery(Review.class);
        query.include(Review.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Review.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Review>() {
            @Override
            public void done(List<Review> posts, ParseException e) {
                if (e != null)
                {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                for (Review post : posts)
                {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }

                adapter.clear();
                allPosts.addAll(posts);
                swipeContainer.setRefreshing(false);
                //adapter.notifyDataSetChanged();
            }
        });
    }
}