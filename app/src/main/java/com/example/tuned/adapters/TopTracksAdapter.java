package com.example.tuned.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tuned.R;
import com.example.tuned.models.Track;

import java.util.ArrayList;

public class TopTracksAdapter extends RecyclerView.Adapter<TopTracksAdapter.ViewHolder> {

    private static final String TAG = "TopTracksAdapter";

    private ArrayList<Track> tracks = new ArrayList<>();

    private LayoutInflater rvInflater;

    private Context trackContext;

    public TopTracksAdapter(Context trackContext, ArrayList<Track> tracks) {
        this.trackContext = trackContext;
        this.tracks = tracks;
        rvInflater = LayoutInflater.from(trackContext);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = rvInflater.inflate(R.layout.discover_feed_cell, parent, false);

        return new ViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onCreateViewHolder: called.");

        Glide.with(trackContext)
                .asBitmap()
                .load(tracks.get(position).trackImageUrl)
                .into(holder.ivAlbumArt);

        holder.tvAlbumName.setText(tracks.get(position).trackName);
        holder.tvAlbumArtist.setText(tracks.get(position).trackArtist);

        holder.ivAlbumArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on an image: " + tracks.get(position).trackName);
                Toast.makeText(trackContext, tracks.get(position).trackName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAlbumArt;
        TextView tvAlbumName;
        TextView tvAlbumArtist;
        final TopTracksAdapter rvAdapter;

        public ViewHolder(@NonNull View itemView, TopTracksAdapter adapter) {
            super(itemView);

            ivAlbumArt = itemView.findViewById(R.id.ivAlbumArt);
            tvAlbumName = itemView.findViewById(R.id.tvAlbumName);
            tvAlbumArtist = itemView.findViewById(R.id.tvAlbumArtist);
            this.rvAdapter = adapter;
        }
    }

}
