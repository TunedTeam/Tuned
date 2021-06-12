package com.example.tuned.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tuned.R;
import com.example.tuned.adapters.PostsAdapter;
import com.example.tuned.parse.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserPageActivity extends AppCompatActivity {

    private static final String TAG = "UserPageActivity";

    ImageView ivProfilePicture;
    TextView tvUsername;
    TextView tvUserReviews;
    TextView tvBack;

    private List<Post> userPosts;
    ParseUser parseUser;
    PostsAdapter postsAdapter;
    RecyclerView rvUserPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        tvUsername = findViewById(R.id.tvUsername);
        tvUserReviews = findViewById(R.id.tvUserReviews);
        rvUserPosts = findViewById(R.id.rvUserPosts);
        tvBack = findViewById(R.id.tvBack);

        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("username");
        String userpic = bundle.getString("userpic");
        ParseUser parseuserinf = bundle.getParcelable("parseUser");

        tvUsername.setText("@" + username);

        tvUserReviews.setText(username + "'s reviews");

        // Go back to your old search result
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Glide.with(this).load(userpic)
                .placeholder(R.drawable.defaultavatar)
                .circleCrop()
                .into(ivProfilePicture);

        parseUser = parseuserinf;
        userPosts = new ArrayList<>();
        postsAdapter = new PostsAdapter(getApplicationContext(), userPosts);

        rvUserPosts.setAdapter(postsAdapter);
        rvUserPosts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        queryPosts(parseUser);

    }

    private void queryPosts(ParseUser parseUser) {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, parseUser);
        query.setLimit(20);

        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername()
                            + ParseUser.getCurrentUser().get("profile_picture"));

                }

                userPosts.addAll(posts);
                postsAdapter.notifyDataSetChanged();
            }
        });
    }
}