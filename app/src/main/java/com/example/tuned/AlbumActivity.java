package com.example.tuned;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.adamratzman.spotify.SpotifyAppApi;
import com.bumptech.glide.Glide;
import com.example.tuned.Spotify.Spotify;
import com.example.tuned.adapters.AlbumAdapter;
import com.example.tuned.adapters.PostsAdapter;
import com.example.tuned.models.Album;
import com.example.tuned.models.Track;

import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {

    ImageView ivAlbumArt;
    TextView tvAlbumName;
    TextView tvAlbumArtist;
    TextView tvListTracks;
    RecyclerView rvListTracks;
    AlbumAdapter adapter;

    private ArrayList<Album> albums = new ArrayList<>();

    private Context albumContext;

    static Spotify spotify = new Spotify();
    static SpotifyAppApi api = spotify.api;
    static ArrayList<Track> track;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        ivAlbumArt = findViewById(R.id.ivAlbumArt);
        tvAlbumName = findViewById(R.id.tvAlbumName);
        tvAlbumArtist = findViewById(R.id.tvAlbumArtist);
        tvListTracks = findViewById(R.id.tvListTracks);
        rvListTracks = findViewById(R.id.rvListTracks);

        Bundle bundle = getIntent().getExtras();
        String albumId = bundle.getString("albumId");
        String albumImage = bundle.getString("albumImage");
        String albumName = bundle.getString("albumName");
        String albumArtist = bundle.getString("albumArtist");
        int totalTracks = bundle.getInt("totalTracks");

        track = spotify.getAlbumTracks(api, albumId);

        Glide.with(this)
                .asBitmap()
                .load(albumImage)
                .into(ivAlbumArt);

        tvAlbumName.setText(albumName);
        tvAlbumArtist.setText(albumArtist);

        // 1. Create the adapter
        // 2. Create the data source
        adapter = new AlbumAdapter(this, track);

        // 3. Set the adapter on the recycler view
        rvListTracks.setAdapter(adapter);

        // 4. Set the layout manager on the recycler view
        rvListTracks.setLayoutManager(new LinearLayoutManager(this));

        rvListTracks.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

    }
}

