package com.example.tuned.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tuned.Post;
import com.example.tuned.R;
import com.parse.ParseFile;

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

        private TextView tvUsername;
        private ImageView ivProfileImage;
        private ImageView ivResultImage;
        private TextView tvResultName;
        private TextView tvResultArtist;
        private TextView tvResultType;
        private TextView tvResultBullet;
        private TextView tvReviewTitle;
        private TextView tvReviewDescription;
        private RatingBar ratingBar;

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
            tvReviewTitle.setText(post.getReviewTitle());
            tvReviewDescription.setText(post.getDescription());
        }


    }
}