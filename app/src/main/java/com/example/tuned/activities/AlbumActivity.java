package com.example.tuned.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.adamratzman.spotify.SpotifyAppApi;
import com.bumptech.glide.Glide;
import com.example.tuned.R;
import com.example.tuned.spotify.Spotify;
import com.example.tuned.adapters.AlbumAdapter;
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
    Button btnRateAlbum;
    Button btnAddList;
    TextView tvReleaseDate;
    TextView tvNumTracks;
    TextView tvBack;
    RatingBar ratingBar;

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
        btnRateAlbum = findViewById(R.id.btnRateAlbum);
        btnAddList = findViewById(R.id.btnAddList);
        tvReleaseDate = findViewById(R.id.tvReleaseDate);
        tvNumTracks = findViewById(R.id.tvNumTracks);
        tvBack = findViewById(R.id.tvBack);
        ratingBar = findViewById(R.id.ratingBar);

        Bundle bundle = getIntent().getExtras();
        String albumId = bundle.getString("albumId");
        String albumImage = bundle.getString("albumImage");
        String albumName = bundle.getString("albumName");
        String albumArtist = bundle.getString("albumArtist");
        int totalTracks = bundle.getInt("totalTracks");
        int albumReleaseDate = bundle.getInt("albumReleaseDate");

        track = spotify.getAlbumTracks(api, albumId);

        Glide.with(this)
                .asBitmap()
                .load(albumImage)
                .into(ivAlbumArt);

        tvAlbumName.setText(albumName);
        tvAlbumArtist.setText(albumArtist);
        tvNumTracks.setText(totalTracks + " tracks");
        tvReleaseDate.setText(albumReleaseDate + "");

        ratingBar.setRating(5);

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        sendToReview(btnRateAlbum, bundle);

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

    public void sendToReview(Button btnRateAlbum, Bundle bundle) {
        btnRateAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String albumId = bundle.getString("albumId");
                String albumImageUrl = bundle.getString("albumImage");
                String albumName = bundle.getString("albumName");
                String albumArtist = bundle.getString("albumArtist");
                int albumReleaseDate = bundle.getInt("trackReleaseDate");
                String resultType = "album";

                Bundle bundle = new Bundle();

                bundle.putString("resultId", albumId);
                bundle.putString("resultImageUrl", albumImageUrl);
                bundle.putString("resultName", albumName);
                bundle.putString("resultArtist", albumArtist);
                bundle.putInt("resultReleaseDate", albumReleaseDate);
                bundle.putString("resultType", resultType);
            }
        });
    }
}

