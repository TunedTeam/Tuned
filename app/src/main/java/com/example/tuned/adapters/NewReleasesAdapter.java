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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adamratzman.spotify.SpotifyAppApi;
import com.bumptech.glide.Glide;
import com.example.tuned.activities.AlbumActivity;
import com.example.tuned.spotify.Spotify;
import com.example.tuned.models.Album;
import com.example.tuned.R;

import java.util.ArrayList;

public class NewReleasesAdapter extends RecyclerView.Adapter<NewReleasesAdapter.ViewHolder> {

    private static final String TAG = "NewReleasesAdapter";

    private ArrayList<Album> albums = new ArrayList<>();

    private LayoutInflater rvInflater;

    private Context albumContext;

    static Spotify spotify = new Spotify();
    static SpotifyAppApi api = spotify.api;

    public NewReleasesAdapter(Context albumContext, ArrayList<Album> albums) {
        this.albumContext = albumContext;
        this.albums = albums;
        rvInflater = LayoutInflater.from(albumContext);
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

        Glide.with(albumContext)
                .asBitmap()
                .load(albums.get(position).albumImageUrl)
                .into(holder.ivAlbumArt);

        holder.tvAlbumName.setText(albums.get(position).albumName);
        holder.tvAlbumArtist.setText(albums.get(position).albumArtist);

        holder.ivAlbumArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on an image: " + albums.get(position).albumName);
                // Toast.makeText(albumContext, albums.get(position).albumName, Toast.LENGTH_SHORT).show();

                String albumId = albums.get(position).albumId;
                String albumImage = albums.get(position).albumImageUrl;
                String albumName = albums.get(position).albumName;
                String albumArtist = albums.get(position).albumArtist;
                int albumReleaseDate = albums.get(position).albumReleaseDate;
                int totalTracks = albums.get(position).totalTracks;

                Log.d(TAG,"The total tracks" + totalTracks);

                Bundle bundle = new Bundle();

                bundle.putString("albumId",albumId);
                bundle.putString("albumImage",albumImage);
                bundle.putString("albumName",albumName);
                bundle.putString("albumArtist",albumArtist);
                bundle.putInt("totalTracks", totalTracks);
                bundle.putInt("albumReleaseDate", albumReleaseDate);
                Intent i = new Intent(albumContext, AlbumActivity.class);
                i.putExtras(bundle);
                albumContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAlbumArt;
        TextView tvAlbumName;
        TextView tvAlbumArtist;
        final NewReleasesAdapter rvAdapter;

        public ViewHolder(@NonNull View itemView, NewReleasesAdapter adapter) {
            super(itemView);

            ivAlbumArt = itemView.findViewById(R.id.ivAlbumArt);
            tvAlbumName = itemView.findViewById(R.id.tvAlbumName);
            tvAlbumArtist = itemView.findViewById(R.id.tvAlbumArtist);
            this.rvAdapter = adapter;
        }
    }



}
