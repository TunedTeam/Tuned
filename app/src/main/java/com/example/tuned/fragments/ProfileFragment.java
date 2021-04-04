package com.example.tuned.fragments;

import android.util.Log;

import com.example.tuned.Review;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends CreateReviewFragment {

    @Override
    protected void queryPosts() {
        ParseQuery<Review> query = ParseQuery.getQuery(Review.class);
        query.include(Review.KEY_USER);
        query.whereEqualTo(Review.KEY_USER, ParseUser.getCurrentUser());
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

               // allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }
}

