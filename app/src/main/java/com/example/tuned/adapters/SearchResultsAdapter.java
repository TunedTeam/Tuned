package com.example.tuned.adapters;

import android.content.Context;
import android.util.AndroidException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tuned.R;
import com.example.tuned.models.SearchResults;
import com.example.tuned.models.Track;

import java.util.ArrayList;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private static final String TAG = "SearchResultsAdapter";

    private ArrayList<SearchResults> searchResults = new ArrayList<>();

    private LayoutInflater rvInflater;

    private Context resultsContext;

    public SearchResultsAdapter(Context resultsContext, ArrayList<SearchResults> searchResults) {
        this.resultsContext = resultsContext;
        this.searchResults = searchResults;
        rvInflater = LayoutInflater.from(resultsContext);
    }

    @NonNull
    @Override
    public SearchResultsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = rvInflater.inflate(R.layout.search_result_cell, parent, false);

        return new ViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultsAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onCreateViewHolder: called.");


        //try {
            Glide.with(resultsContext)
                    .asBitmap()
                    .load(searchResults.get(position).getImage())
                    .into(holder.ivResultImage);
        /*} catch (Exception e) {
            // in the case that there is no image found
            Glide.with(resultsContext)
                    .asBitmap()
                    .load(R.drawable.image_not_found)
                    .into(holder.ivResultImage);
        }*/

        holder.tvResultName.setText(searchResults.get(position).getName());
        holder.tvResultType.setText(searchResults.get(position).getType());

        // on click listener to bring you to type page
        // insert if blocks sending you to page depending on the type
        /*
        holder.ivAlbumArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on an image: " + tracks.get(position).trackName);
                Toast.makeText(trackContext, tracks.get(position).trackName, Toast.LENGTH_SHORT).show();
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvResultType;
        TextView tvResultName;
        ImageView ivResultImage;
        final SearchResultsAdapter rvAdapter;

        public ViewHolder(@NonNull View itemView, SearchResultsAdapter adapter) {
            super(itemView);

            tvResultType = itemView.findViewById(R.id.ivAlbumArt);
            tvResultName = itemView.findViewById(R.id.tvAlbumName);
            ivResultImage = itemView.findViewById(R.id.tvAlbumArtist);
            this.rvAdapter = adapter;
        }
    }
}
