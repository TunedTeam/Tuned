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



    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTrack;
        TextView tvArtist;
        final AlbumAdapter rvAdapter;

        public ViewHolder(@NonNull View itemView, AlbumAdapter adapter) {
            super(itemView);

            tvTrack = itemView.findViewById(R.id.tvTrack);
            tvArtist = itemView.findViewById(R.id.tvArtist);
            this.rvAdapter = adapter;
        }
    }

}
