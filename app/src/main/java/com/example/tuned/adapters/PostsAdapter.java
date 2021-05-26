package com.example.tuned.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tuned.Like;
import com.example.tuned.Post;
import com.example.tuned.R;
import com.example.tuned.ReviewPostActivity;
import com.example.tuned.utils.LikeAnim;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private static final String TAG = "PostsAdapter";

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = bundleData(post);
                sendToReviewPost(bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> postList) {
        posts.addAll(postList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvUsername;
        ImageView ivProfileImage;
        ImageView ivResultImage;
        TextView tvResultName;
        TextView tvResultArtist;
        TextView tvResultType;
        TextView tvResultBullet;
        TextView tvReviewTitle;
        TextView tvReviewDescription;
        RatingBar ratingBar;
        ImageView ivLikeHeart;
        TextView tvNumLikes;
        ImageView ivComment;
        TextView tvNumComments;
        LikeAnim mLikeAnim;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivProfileImage = itemView.findViewById(R.id.ivProfilePicture);

            ivResultImage = itemView.findViewById(R.id.ivResultImage);
            tvResultName = itemView.findViewById(R.id.tvResultName);
            tvResultArtist = itemView.findViewById(R.id.tvResultArtist);
            tvResultType = itemView.findViewById(R.id.tvResultType);
            tvResultBullet = itemView.findViewById(R.id.tvResultBullet);
            tvReviewTitle = itemView.findViewById(R.id.tvReviewTitle);
            tvReviewDescription = itemView.findViewById(R.id.tvReviewDescription);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            ivLikeHeart = itemView.findViewById(R.id.ivLikeHeart);
            tvNumLikes = itemView.findViewById(R.id.tvNumLikes);
            ivComment = itemView.findViewById(R.id.ivComment);
            tvNumComments = itemView.findViewById(R.id.tvNumComments);

            mLikeAnim = new LikeAnim(ivLikeHeart);
        }


        public void bind(Post post) {
            //Bind the post data to the view elements

            tvUsername.setText(post.getUser().getUsername());

            ParseFile userProfilePic = (ParseFile) post.getUser().get("profile_picture");

            Log.i("PostsAdapter", "user profile " + userProfilePic);

            if(userProfilePic != null) {
                Glide.with(context).load(userProfilePic.getUrl())
                        .placeholder(R.drawable.defaultavatar)
                        .circleCrop()
                        .into(ivProfileImage);

            }

            if (post.getResultType().equals("Artist")) {
                tvResultType.setText(post.getResultType());
                tvResultName.setText(post.getResultName());
                tvResultBullet.setText("");
                tvResultArtist.setText("");

                Glide.with(context)
                        .asBitmap()
                        .load(post.getResultImageUrl())
                        .circleCrop()
                        .into(ivResultImage);

            } else if (post.getResultType().equals("Album")) {
                tvResultType.setText(post.getResultType());
                tvResultName.setText(post.getResultName());
                tvResultBullet.setText("•");
                tvResultArtist.setText(post.getResultArtist());

                Glide.with(context)
                        .asBitmap()
                        .load(post.getResultImageUrl())
                        .into(ivResultImage);

            } else if (post.getResultType().equals("Track")) {
                tvResultType.setText(post.getResultType());
                tvResultName.setText(post.getResultName());
                tvResultBullet.setText("•");
                tvResultArtist.setText(post.getResultArtist());

                Glide.with(context)
                        .asBitmap()
                        .load(post.getResultImageUrl())
                        .into(ivResultImage);

            }

            ratingBar.setRating(post.getRating());
            ratingBar.setIsIndicator(true);
            tvReviewTitle.setText(post.getReviewTitle());
            tvReviewDescription.setText(post.getDescription());

            if (!post.isLikedByUser) {
                checkIsLikedByUser(post);
                //mLikeAnim.toggleLikeOutline(context);
                Drawable heartDrawable = context.getDrawable(R.drawable.ic_like_outline);
                ivLikeHeart.setImageDrawable(heartDrawable);
                if (post.numOfLikes == 0) {
                    tvNumLikes.setText("");
                }
            } else {
                mLikeAnim.toggleLikeFilled(context);
                if (post.numOfLikes == 0) {
                    tvNumLikes.setText("");
                }
                else if (post.numOfLikes == 1) {
                    tvNumLikes.setText(post.numOfLikes + " like");
                }
                else if (post.numOfLikes > 1) {
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
                    Bundle bundle = bundleData(post);
                    bundle.putString("scroll","scroll");
                    sendToReviewPost(bundle);
                }
            });

            if (post.numOfComments == 0) {
                tvNumComments.setText("");
            }
            else if (post.numOfComments == 1) {
                tvNumComments.setText(post.numOfComments + " comment");
            }
            else if (post.numOfComments > 1) {
                tvNumComments.setText(post.numOfComments + " comments");
            }
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
                        Toast.makeText(context, "There was a problem liking the post", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Log.i(TAG, "Post " + post.getResultId() + " was liked by " + ParseUser.getCurrentUser().getUsername());
                    post.isLikedByUser = true;
                    post.numOfLikes++;

                    // do fill like animation
                    mLikeAnim.toggleLikeFilled(context);

                    if (post.numOfLikes == 1) {
                        tvNumLikes.setText(post.numOfLikes + " like");
                    }
                    else if (post.numOfLikes > 1) {
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
                        Toast.makeText(context, "There was a problem loading the likes", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (post.numOfLikes == 0) {
                        tvNumLikes.setText("");
                    }
                    else if (post.numOfLikes == 1) {
                        tvNumLikes.setText(post.numOfLikes + " like");
                    }
                    else if (post.numOfLikes > 1) {
                        tvNumLikes.setText(post.numOfLikes + " likes");
                    }
                    post.numOfLikes = likes.size();

                    for (Like aLike : likes) {
                        if (aLike.getUser().getObjectId().equals(ParseUser.getCurrentUser().getObjectId())) {
                            mLikeAnim.toggleLikeFilled(context);
                            post.isLikedByUser = true;
                        }
                    }
                }
            });
        }

        private void removeLike (Post post) {
            ParseQuery<Like> query = ParseQuery.getQuery(Like.class);
            query.whereEqualTo(Like.KEY_POST, post);
            query.whereEqualTo(Like.KEY_USER, ParseUser.getCurrentUser());

            query.getFirstInBackground(new GetCallback<Like>() {
                @Override
                public void done(Like like, ParseException e) {

                    if (e != null) {
                        // something went wrong
                        Log.e(TAG, "There was a problem disliking, e");
                        Toast.makeText(context, "There was a problem disliking", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        like.delete();
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();;
                        Log.e(TAG, "There was a problem disliking, e");
                        Toast.makeText(context, "There was a problem disliking", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    post.isLikedByUser = false;
                    post.numOfLikes--;

                    //mLikeAnim.toggleLikeOutline(context);
                    Drawable heartDrawable = context.getDrawable(R.drawable.ic_like_outline);
                    ivLikeHeart.setImageDrawable(heartDrawable);

                    if (post.numOfLikes == 0) {
                        tvNumLikes.setText("");
                    }
                    else if (post.numOfLikes == 1) {
                        tvNumLikes.setText(post.numOfLikes + " like");
                    }
                    else if (post.numOfLikes > 1) {
                        tvNumLikes.setText(post.numOfLikes + " likes");
                    }
                }
            });
        }
    }

    private void sendToReviewPost(Bundle bundle) {
        Intent i = new Intent(context, ReviewPostActivity.class);
        i.putExtras(bundle);
        context.startActivity(i);
    }

    private Bundle bundleData(Post post) {
//        String postId = post.getObjectId();
//        String postUsername = post.getUser().getUsername();
//        ParseFile userProfilePic = (ParseFile) post.getUser().get("profile_picture");
//        String postDescription = post.getDescription();
//        String postTitle = post.getReviewTitle();
//        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
//        String postDate = formatter.format(post.getCreatedAt());
//        Float postRating = post.getRating();
//        String resultId = post.getResultId();
//        String resultImageUrl = post.getResultImageUrl();
//        String resultName = post.getResultName();
//        String resultType = post.getResultType();
//        String resultArtist = post.getResultArtist();

        Bundle bundle = new Bundle();

//        bundle.putString("postId", postId);
//        bundle.putString("postUsername", postUsername);
//        bundle.putParcelable("postUserPicture", userProfilePic);
//        bundle.putString("postDescription", postDescription);
//        bundle.putString("postTitle", postTitle);
//        bundle.putString("postDate", postDate);
//        bundle.putFloat("postRating", postRating);
//        bundle.putString("resultId", resultId);
//        bundle.putString("resultImageUrl", resultImageUrl);
//        bundle.putString("resultName", resultName);
//        bundle.putString("resultType", resultType);
//        bundle.putString("resultArtist", resultArtist);
        bundle.putParcelable("post", post);

        return bundle;
    }
}