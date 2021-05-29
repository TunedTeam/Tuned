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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adamratzman.spotify.SpotifyAppApi;
import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.tuned.AlbumActivity;
import com.example.tuned.R;
import com.example.tuned.Spotify.Spotify;
import com.example.tuned.StreamingActivity;
import com.example.tuned.models.Album;
import com.example.tuned.models.Track;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Headers;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    private static final String TAG = "AlbumAdapter";

    private ArrayList<Track> tracks = new ArrayList<>();

    private LayoutInflater rvInflater;

    private Context trackContext;

    RelativeLayout container;

    static Spotify spotify = new Spotify();
    static SpotifyAppApi api = spotify.api;

    public AlbumAdapter(Context trackContext, ArrayList<Track> tracks) {
        this.trackContext = trackContext;
        this.tracks = tracks;
        rvInflater = LayoutInflater.from(trackContext);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = rvInflater.inflate(R.layout.album_cell, parent, false);

        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onCreateViewHolder: called.");

        holder.tvTrack.setText(tracks.get(position).trackName);
        holder.tvArtist.setText(tracks.get(position).trackArtist);


        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on a track: " + tracks.get(position).trackName);
                Toast.makeText(trackContext, tracks.get(position).trackName, Toast.LENGTH_SHORT).show();

                String trackId = tracks.get(position).trackId;
                String trackName = tracks.get(position).trackName;
                String trackAlbumId = tracks.get(position).trackAlbumId;
                Log.d(TAG, "the id:" + trackAlbumId);
                //Log.d(TAG, "track photo: " + albumImage);
                int totalTracks = tracks.size();
                Log.d(TAG, "TOTAL TRACKS:" + tracks.size());

                String trackPreview = tracks.get(position).trackDeezerPreview;
                //String trackArtist = tracks.get(position).trackArtist;
//                String url = tracks.get(position).trackPreviewUrl;
//                Log.d(TAG, "url: " + url);

                Bundle bundle = new Bundle();

                bundle.putString("trackId", trackId);
                bundle.putString("trackName", trackName);
                bundle.putString("trackAlbumId", trackAlbumId);
                bundle.putInt("position", position);
                bundle.putString("trackPreview", trackPreview);
                bundle.putInt("totalTracks", totalTracks);

                Intent intent = new Intent(trackContext, StreamingActivity.class);
                intent.putExtras(bundle);
                trackContext.startActivity(intent);

                // bundle.putString("trackImage",trackImage);
                // bundle.putString("trackArtist",trackArtist);
//                bundle.putString("url", url);
//                Intent i = new Intent(trackContext, StreamingActivity.class);
//                //Intent intent = new Intent(albumContext, StreamingActivity.class);
//                // intent.putExtra("albumImage",albumImage);
//                //i.putExtra("position", position);
//                i.putExtras(bundle);
//                trackContext.startActivity(i);


            }
        });

//        int rating =
//        holder.ratingBar.setRating();
//
//        holder.tvRating.setText();

//        public void getDeezerPreview() {
//
//            AsyncHttpClient client = new AsyncHttpClient();
//            client.get(deezerUrl, new JsonHttpResponseHandler() {
//                @Override
//                public void onSuccess(int i, Headers headers, JSON json) {
//                    JSONObject jsonObject = json.jsonObject;
//
//                    try {
//                        previewURL = jsonObject.getString("preview");
//                        Log.i("Spotify", "checking previewurl: " + previewURL);
//
//                    } catch (JSONException e) {
//                        Log.e("Spotify", "Hit JSON exception", e);
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(int i, Headers headers, String s, Throwable throwable) {
//                    Log.d("Spotify", "onFailure");
//                }
//            });
//        }

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
            container = itemView.findViewById(R.id.container);
            this.rvAdapter = adapter;
        }
    }

}
