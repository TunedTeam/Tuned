package com.example.tuned.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adamratzman.spotify.SpotifyAppApi;
import com.bumptech.glide.Glide;
import com.example.tuned.AlbumActivity;
import com.example.tuned.R;
import com.example.tuned.Spotify.Spotify;
import com.example.tuned.models.Track;

import java.util.ArrayList;

public class PopularWeekAdapter extends RecyclerView.Adapter<PopularWeekAdapter.ViewHolder> {

    private static final String TAG = "PopularWeekAdapter";

    private ArrayList<Track> tracks = new ArrayList<>();

    private LayoutInflater rvInflater;

    private Context trackContext;

    static Spotify spotify = new Spotify();
    static SpotifyAppApi api = spotify.api;

    public PopularWeekAdapter(Context trackContext, ArrayList<Track> tracks) {
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
                // Toast.makeText(trackContext, tracks.get(position).trackName, Toast.LENGTH_SHORT).show();
                String albumId = tracks.get(position).trackAlbumId;
                String albumImage = spotify.getAlbumImage(api, albumId);
                String albumName = spotify.getAlbumName(api, albumId);
                String albumArtist = spotify.getAlbumArtist(api, albumId);

                Bundle bundle = new Bundle();

                bundle.putString("albumId",albumId);
                bundle.putString("albumImage",albumImage);
                bundle.putString("albumName",albumName);
                bundle.putString("albumArtist",albumArtist);
                Intent i = new Intent(trackContext, AlbumActivity.class);
                i.putExtras(bundle);
                trackContext.startActivity(i);
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
        final PopularWeekAdapter rvAdapter;

        public ViewHolder(@NonNull View itemView, PopularWeekAdapter adapter) {
            super(itemView);

            ivAlbumArt = itemView.findViewById(R.id.ivAlbumArt);
            tvAlbumName = itemView.findViewById(R.id.tvAlbumName);
            tvAlbumArtist = itemView.findViewById(R.id.tvAlbumArtist);
            this.rvAdapter = adapter;
        }
    }

}
