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
import com.example.tuned.models.Album;
import com.example.tuned.R;

import java.util.ArrayList;

public class TopTracksAdapter extends RecyclerView.Adapter<TopTracksAdapter.ViewHolder> {

    private static final String TAG = "NewReleasesAdapter";

    private ArrayList<Album> albums = new ArrayList<>();

    private LayoutInflater rvInflater;

    private Context albumContext;

    public TopTracksAdapter(Context albumContext, ArrayList<Album> albums) {
        this.albumContext = albumContext;
        this.albums = albums;
        rvInflater = LayoutInflater.from(albumContext);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = rvInflater.inflate(R.layout.card_view_design, parent, false);

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
                Toast.makeText(albumContext, albums.get(position).albumName, Toast.LENGTH_SHORT).show();
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
