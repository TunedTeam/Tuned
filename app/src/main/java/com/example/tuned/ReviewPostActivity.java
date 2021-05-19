package com.example.tuned;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tuned.Post;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.List;

public class ReviewPostActivity extends AppCompatActivity {

    private static final String TAG = "ReviewPostActivity";

    ImageView ivProfilePicture;
    TextView tvUsername;
    ImageView ivResultImage;
    TextView tvResultName;
    TextView tvResultType;
    TextView tvResultBullet;
    TextView tvResultArtist;
    RatingBar ratingBar;
    TextView tvReviewDate;
    TextView tvReviewTitle;
    TextView tvReviewDescription;
    ImageView ivLike;
    TextView tvLikes;
    ImageView ivComment;
    TextView tvComments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_post);

        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        tvUsername = findViewById(R.id.tvUsername);
        ivResultImage = findViewById(R.id.ivResultImage);
        tvResultName = findViewById(R.id.tvResultName);
        tvResultType = findViewById(R.id.tvResultType);
        tvResultBullet = findViewById(R.id.tvResultBullet);
        tvResultArtist = findViewById(R.id.tvResultArtist);
        ratingBar = findViewById(R.id.ratingBar);
        tvReviewDate = findViewById(R.id.tvReviewDate);
        tvReviewTitle = findViewById(R.id.tvReviewTitle);
        tvReviewDescription = findViewById(R.id.tvReviewDescription);
        ivLike = findViewById(R.id.ivLike);
        tvLikes = findViewById(R.id.tvLikes);
        ivComment = findViewById(R.id.ivComment);
        tvComments = findViewById(R.id.tvComments);

        Bundle bundle = getIntent().getExtras();

        String postId = bundle.getString("postId");
        String postUsername = bundle.getString("postUsername");
        ParseFile postUserPicture = bundle.getParcelable("postUserPicture");
        String postDescription = bundle.getString("postDescription");
        String postTitle = bundle.getString("postTitle");
        String postDate = bundle.getString("postDate");
        Float postRating = bundle.getFloat("postRating");
        String resultId = bundle.getString("resultId");
        String resultImageUrl = bundle.getString("resultImageUrl");
        String resultName = bundle.getString("resultName");
        String resultType = bundle.getString("resultType");
        String resultArtist = bundle.getString("resultArtist");

//        ParseFile profilePic = (ParseFile) ParseUser.get(postUserId);
//        ParseFile userProfilePic = (ParseFile) post.getUser().get("profile_picture");
//
//        Log.i("PostsAdapter", "user profile " + userProfilePic);
//
//
        if(postUserPicture != null) {
            Glide.with(getApplicationContext()).load(postUserPicture.getUrl())
                    .placeholder(R.drawable.defaultavatar)
                    .circleCrop()
                    .into(ivProfilePicture);
        }

        tvUsername.setText(postUsername);
        tvReviewDescription.setText(postDescription);
        tvReviewTitle.setText(postTitle);
        tvReviewDate.setText("Reviewed on " + postDate);
        ratingBar.setRating(postRating);

        Glide.with(this)
                .asBitmap()
                .load(resultImageUrl)
                .into(ivResultImage);

        tvResultName.setText(resultName);
        tvResultType.setText(resultType);
        tvResultArtist.setText(resultArtist);
    }
}