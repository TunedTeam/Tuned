package com.example.tuned.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tuned.R;
import com.example.tuned.models.SearchResults;

import java.util.ArrayList;

public class ResultAdapter extends ArrayAdapter<SearchResults> {

    private static final String TAG = "ResultAdapter";

    //private ArrayList<SearchResults> results = new ArrayList<>();

    public ResultAdapter(Context context, int resource, ArrayList<SearchResults> results) {
        super(context, resource, results);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        SearchResults result = getItem(position);

        if (convertView == null) {
            Log.i(TAG, "Is null");
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_result_cell, parent, false);
        }

        TextView tvResultType = convertView.findViewById(R.id.tvResultType);
        TextView tvResultName = convertView.findViewById(R.id.tvResultName);
        TextView tvResultArtist = convertView.findViewById(R.id.tvResultArtist);
        TextView tvResultBullet = convertView.findViewById(R.id.tvResultBullet);
        ImageView ivResultImage = convertView.findViewById(R.id.ivResultImage);

        tvResultType.setText(result.getType());
        tvResultName.setText(result.getName());

        if (result.getType().equals("artist")) {
            tvResultType.setText("Artist");
            tvResultName.setText(result.getName());
            tvResultBullet.setText("");
            tvResultArtist.setText("");

            if (result.getImage().equals("")) {
                Glide.with(getContext())
                        .asBitmap()
                        .load(R.drawable.image_not_found)
                        .circleCrop()
                        .into(ivResultImage);
            } else {
                Glide.with(getContext())
                        .asBitmap()
                        .load(result.getImage())
                        .circleCrop()
                        .into(ivResultImage);
            }
        } else if (result.getType().equals("album")) {
            tvResultType.setText("Album");
            tvResultName.setText(result.getName());
            tvResultBullet.setText("•");
            tvResultArtist.setText(result.getArtist());

            if (result.getImage().equals("")) {
                Glide.with(getContext())
                        .asBitmap()
                        .load(R.drawable.image_not_found)
                        .into(ivResultImage);
            } else {
                Glide.with(getContext())
                        .asBitmap()
                        .load(result.getImage())
                        .into(ivResultImage);
            }
        } else if (result.getType().equals("track")) {
            tvResultType.setText("Track");
            tvResultName.setText(result.getName());
            tvResultBullet.setText("•");
            tvResultArtist.setText(result.getArtist());

            if (result.getImage().equals("")) {
                Glide.with(getContext())
                        .asBitmap()
                        .load(R.drawable.image_not_found)
                        .into(ivResultImage);
            } else {
                Glide.with(getContext())
                        .asBitmap()
                        .load(result.getImage())
                        .into(ivResultImage);
            }
        }

        return convertView;
    }
}
