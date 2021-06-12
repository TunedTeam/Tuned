package com.example.tuned.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.tuned.parse.Comment;
import com.example.tuned.parse.Like;
import com.example.tuned.parse.Post;
import com.example.tuned.R;
import com.example.tuned.adapters.CommentsAdapter;
import com.example.tuned.utils.LikeAnim;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    ImageView ivLikeHeart;
    TextView tvNumLikes;
    ImageView ivComment;
    TextView tvNumComments;
    LikeAnim mLikeAnim;
    EditText etComment;
    TextView tvSend;
    RecyclerView rvListComments;
    ScrollView scrollView;
    TextView tvBack;
    SwipeRefreshLayout swipeContainer;

    private Post post;
    private String post_id;
    List<Comment> commentList;
    CommentsAdapter commentsAdapter;

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
        ivLikeHeart = findViewById(R.id.ivLikeHeart);
        tvNumLikes = findViewById(R.id.tvNumLikes);
        ivComment = findViewById(R.id.ivComment);
        tvNumComments = findViewById(R.id.tvNumComments);
        mLikeAnim = new LikeAnim(ivLikeHeart);
        etComment = findViewById(R.id.etComment);
        tvSend = findViewById(R.id.tvSend);
        rvListComments = findViewById(R.id.rvListComments);
        scrollView = findViewById(R.id.scrollView);
        tvBack = findViewById(R.id.tvBack);

        Bundle bundle = getIntent().getExtras();

//        scrollView.scrollTo(0, 300);
//        scrollView.smoothScrollTo(0, 300);
//        scrollView.smoothScrollBy(0, 300);

        scrollView.smoothScrollTo(0, 600);
//        if (bundle.getString("scroll").equals("scroll")) {
//          scrollView.smoothScrollTo(0, 200);
//        }

        post = bundle.getParcelable("post");

        post_id = post.getObjectId();
        String postUsername = post.getUser().getUsername();
        ParseFile userProfilePic = (ParseFile) post.getUser().get("profile_picture");
        String postDescription = post.getDescription();
        String postTitle = post.getReviewTitle();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String postDate = formatter.format(post.getCreatedAt());
        Float postRating = post.getRating();
        String resultId = post.getResultId();
        String resultImageUrl = post.getResultImageUrl();
        String resultName = post.getResultName();
        String resultType = post.getResultType();
        String resultArtist = post.getResultArtist();

        if (userProfilePic != null) {
            Glide.with(getApplicationContext()).load(userProfilePic.getUrl())
                    .placeholder(R.drawable.defaultavatar)
                    .circleCrop()
                    .into(ivProfilePicture);
        }

        tvUsername.setText(postUsername);
        tvReviewDescription.setText(postDescription);
        tvReviewTitle.setText(postTitle);
        tvReviewDate.setText("Reviewed on " + postDate);
        ratingBar.setRating(postRating);
        ratingBar.setIsIndicator(true);

        Glide.with(this)
                .asBitmap()
                .load(resultImageUrl)
                .transform(new CenterCrop(),new RoundedCorners(10))
                .into(ivResultImage);

        tvResultName.setText(resultName);
        tvResultType.setText(resultType);
        tvResultArtist.setText(resultArtist);

        if (!post.isLikedByUser) {
            checkIsLikedByUser(post);
            //mLikeAnim.toggleLikeOutline(context);
            Drawable heartDrawable = getApplicationContext().getDrawable(R.drawable.ic_like_outline);
            ivLikeHeart.setImageDrawable(heartDrawable);
            if (post.numOfLikes == 0) {
                tvNumLikes.setText("");
            }
        } else {
            mLikeAnim.toggleLikeFilled(getApplicationContext());
            if (post.numOfLikes == 0) {
                tvNumLikes.setText("");
            } else if (post.numOfLikes == 1) {
                tvNumLikes.setText(post.numOfLikes + " like");
            } else if (post.numOfLikes > 1) {
                tvNumLikes.setText(post.numOfLikes + " likes");
            }
        }

        ivLikeHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if post is already liked get rid of the like
                // else like the post
                if (post.isLikedByUser) {
                    removeLike(post);
                } else {
                    likePost(post);
                }
            }
        });

        ivComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // scroll down to comment and pop up keyboard
            }
        });

        if (post.numOfComments == 0) {
            tvNumComments.setText("");
        } else if (post.numOfComments == 1) {
            tvNumComments.setText(post.numOfLikes + " comment");
        } else if (post.numOfComments > 1) {
            tvNumComments.setText(post.numOfLikes + " comments");
        }

        commentList = new ArrayList<>();

        commentsAdapter = new CommentsAdapter(this, commentList);

        rvListComments.setAdapter(commentsAdapter);
        rvListComments.setLayoutManager(new LinearLayoutManager(this));

        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postComment(post);
            }
        });

        // Go back to your review screen
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        etComment.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etComment.setRawInputType(InputType.TYPE_CLASS_TEXT);

        loadComments();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
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
                loadComments();
            }
        });

        ivProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UserPageActivity.class);
                Bundle bundle = new Bundle();

                String username = post.getUser().getUsername();
                String userpic = ((ParseFile) post.getUser().get("profile_picture")).getUrl();
                ParseUser parseUser = post.getUser();

                bundle.putString("username", username);
                bundle.putString("userpic", userpic);
                bundle.putParcelable("parseUser", parseUser);

                i.putExtras(bundle);
                startActivity(i);
            }
        });

        tvUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UserPageActivity.class);
                Bundle bundle = new Bundle();

                String username = post.getUser().getUsername();
                String userpic = ((ParseFile) post.getUser().get("profile_picture")).getUrl();
                ParseUser parseUser = post.getUser();

                bundle.putString("username", username);
                bundle.putString("userpic", userpic);
                bundle.putParcelable("parseUser", parseUser);

                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }

    private void closeKeyboard() {
        View view = getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void postComment(Post post) {
        String comment = etComment.getText().toString();

        if (comment.isEmpty()) {
            Toast.makeText(this, "Comment can not be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        postCommentQuery(comment, post);
    }

    private void postCommentQuery(String text, Post post) {
        Comment comment = new Comment();
        comment.setComment(text);
        comment.setPost(post);
        comment.setUser(ParseUser.getCurrentUser());

        post.numOfComments++;

        comment.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e != null) {
                    // something went wrong
                    Log.e(TAG, "There was a problem posting the comment", e);
                    Toast.makeText(ReviewPostActivity.this, "There was a problem posting the comment", Toast.LENGTH_SHORT).show();
                    return;
                }

                etComment.setText("");
                commentList.add(comment);
                commentsAdapter.notifyDataSetChanged();
                closeKeyboard();
            }
        });
    }

    private void loadComments() {
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        query.include(Comment.KEY_POST);
        query.include(Comment.KEY_USER);
        query.whereEqualTo(Comment.KEY_POST, post);

        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> comments, ParseException e) {
                Log.i(TAG, "test");
                if (e != null) {
                    Log.e(TAG, "There was a problem loading the comments", e);
                    Toast.makeText(ReviewPostActivity.this, "There was a problem loading the comments", Toast.LENGTH_SHORT).show();
                    return;
                }

                    commentsAdapter.clear();
                    commentsAdapter.addAll(comments);
                    swipeContainer.setRefreshing(false);

//                commentsAdapter.clear();
//                commentList.addAll(comments);
            }
        });
    }

    private void likePost(Post post) {
        Like newLike = new Like();

        newLike.setPost(post);
        newLike.setUser(ParseUser.getCurrentUser());

        newLike.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    // something went wrong
                    Log.e(TAG, "There was a problem liking the post", e);
                    Toast.makeText(getApplicationContext(), "There was a problem liking the post", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.i(TAG, "Post " + post.getResultId() + " was liked by " + ParseUser.getCurrentUser().getUsername());
                post.isLikedByUser = true;
                post.numOfLikes++;

                // do fill like animation
                mLikeAnim.toggleLikeFilled(getApplicationContext());

                if (post.numOfLikes == 1) {
                    tvNumLikes.setText(post.numOfLikes + " like");
                } else if (post.numOfLikes > 1) {
                    tvNumLikes.setText(post.numOfLikes + " likes");
                }
            }
        });
    }

    private void checkIsLikedByUser(Post post) {
        ParseQuery<Like> query = ParseQuery.getQuery(Like.class);
        query.include(Like.KEY_USER);
        query.whereEqualTo(Like.KEY_POST, post);

        query.findInBackground(new FindCallback<Like>() {
            @Override
            public void done(List<Like> likes, ParseException e) {

                if (e != null) {
                    // something went wrong
                    Log.e(TAG, "There was a problem loading the likes", e);
                    Toast.makeText(getApplicationContext(), "There was a problem loading the likes", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (post.numOfLikes == 0) {
                    tvNumLikes.setText("");
                } else if (post.numOfLikes == 1) {
                    tvNumLikes.setText(post.numOfLikes + " like");
                } else if (post.numOfLikes > 1) {
                    tvNumLikes.setText(post.numOfLikes + " likes");
                }
                post.numOfLikes = likes.size();

                for (Like aLike : likes) {
                    if (aLike.getUser().getObjectId().equals(ParseUser.getCurrentUser().getObjectId())) {
                        mLikeAnim.toggleLikeFilled(getApplicationContext());
                        post.isLikedByUser = true;
                    }
                }
            }
        });
    }

    private void removeLike(Post post) {
        ParseQuery<Like> query = ParseQuery.getQuery(Like.class);
        query.whereEqualTo(Like.KEY_POST, post);
        query.whereEqualTo(Like.KEY_USER, ParseUser.getCurrentUser());

        query.getFirstInBackground(new GetCallback<Like>() {
            @Override
            public void done(Like like, ParseException e) {

                if (e != null) {
                    // something went wrong
                    Log.e(TAG, "There was a problem disliking, e");
                    Toast.makeText(getApplicationContext(), "There was a problem disliking", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    like.delete();
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                    ;
                    Log.e(TAG, "There was a problem disliking, e");
                    Toast.makeText(getApplicationContext(), "There was a problem disliking", Toast.LENGTH_SHORT).show();
                    return;
                }

                post.isLikedByUser = false;
                post.numOfLikes--;

                //mLikeAnim.toggleLikeOutline(context);
                Drawable heartDrawable = getApplicationContext().getDrawable(R.drawable.ic_like_outline);
                ivLikeHeart.setImageDrawable(heartDrawable);

                if (post.numOfLikes == 0) {
                    tvNumLikes.setText("");
                } else if (post.numOfLikes == 1) {
                    tvNumLikes.setText(post.numOfLikes + " like");
                } else if (post.numOfLikes > 1) {
                    tvNumLikes.setText(post.numOfLikes + " likes");
                }
            }
        });
    }

}