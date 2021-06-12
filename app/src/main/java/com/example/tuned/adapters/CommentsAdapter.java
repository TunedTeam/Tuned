package com.example.tuned.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tuned.parse.Comment;
import com.example.tuned.R;
import com.parse.ParseFile;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private static final String TAG = "CommentsAdapter";

    private Context context;
    private List<Comment> comments;

    public CommentsAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void clear() {
        comments.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Comment> commentList) {
        comments.addAll(commentList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfilePicture;
        TextView tvUsername;
        TextView tvComment;
        TextView tvCommentTimePosted;
//        TextView tvCommentNumLikes;
//        TextView tvCommentReply;
//        ImageView ivLikeHeart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProfilePicture = itemView.findViewById(R.id.ivProfilePicture);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvComment = itemView.findViewById(R.id.tvComment);
            tvCommentTimePosted = itemView.findViewById(R.id.tvCommentTimePosted);
//            tvCommentNumLikes = itemView.findViewById(R.id.tvCommentNumLikes);
//            tvCommentReply = itemView.findViewById(R.id.tvCommentReply);
//            ivLikeHeart = itemView.findViewById(R.id.ivLikeHeart);
        }

        public void bind(Comment comment) {
            //Bind the post data to the view elements

            ParseFile userProfilePic = (ParseFile) comment.getUser().get("profile_picture");

            Log.i("PostsAdapter", "user profile " + userProfilePic);

            if (userProfilePic != null) {
                Glide.with(context).load(userProfilePic.getUrl())
                        .placeholder(R.drawable.defaultavatar)
                        .circleCrop()
                        .into(ivProfilePicture);

            }

            tvUsername.setText(comment.getUser().getUsername());
            tvComment.setText(comment.getComment());
            String createdAt = comment.getCreatedAt().toString();
            tvCommentTimePosted.setText(comment.getFormattedTimestamp(createdAt));
        }
    }
}
