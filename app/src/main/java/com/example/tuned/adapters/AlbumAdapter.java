package com.example.tuned.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tuned.AlbumActivity;
import com.example.tuned.R;
import com.example.tuned.StreamingActivity;
import com.example.tuned.models.Track;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private static final String TAG = "AlbumAdapter";

    private ArrayList<Track> tracks = new ArrayList<>();

    private LayoutInflater rvInflater;

    private Context trackContext;

    public AlbumAdapter(Context trackContext, ArrayList<Track> tracks) {
        this.trackContext = trackContext;
        this.tracks = tracks;
        rvInflater = LayoutInflater.from(trackContext);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = rvInflater.inflate(R.layout.album_cell, parent, false);

        return new ViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onCreateViewHolder: called.");

        holder.tvTrack.setText(tracks.get(position).trackName);
        holder.tvArtist.setText(tracks.get(position).trackArtist);


        holder.tvTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on a track: " + tracks.get(position).trackName);
                Toast.makeText(trackContext, tracks.get(position).trackName, Toast.LENGTH_SHORT).show();

                String trackId = tracks.get(position).trackId;
                String trackName = tracks.get(position).trackName;
                String trackArtist = tracks.get(position).trackArtist;

                Bundle bundle = new Bundle();

                bundle.putString("trackId",trackId);
                bundle.putString("trackName",trackName);
                bundle.putString("trackArtist",trackArtist);
                Intent i = new Intent(trackContext, StreamingActivity.class);
                i.putExtras(bundle);
                trackContext.startActivity(i);



            }
        });

//        int rating =
//        holder.ratingBar.setRating();
//
//        holder.tvRating.setText();

    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTrack;
        TextView tvArtist;
        RatingBar ratingBar;
        TextView tvRating;
        final AlbumAdapter rvAdapter;

        public ViewHolder(@NonNull View itemView, AlbumAdapter adapter) {
            super(itemView);

            tvTrack = itemView.findViewById(R.id.tvTrack);
            tvArtist = itemView.findViewById(R.id.tvArtist);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            tvRating = itemView.findViewById(R.id.tvRating);
            this.rvAdapter = adapter;
        }
    }

}
