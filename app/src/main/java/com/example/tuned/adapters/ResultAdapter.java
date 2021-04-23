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

        TextView tvResultType = (TextView) convertView.findViewById(R.id.tvResultType);
        TextView tvResultName = (TextView) convertView.findViewById(R.id.tvResultName);
        ImageView ivResultImage =  convertView.findViewById(R.id.ivResultImage);

        tvResultType.setText(result.getType());
        tvResultName.setText(result.getName());

        try {
        Glide.with(getContext())
                .asBitmap()
                .load(result.getImage())
                .into(ivResultImage);
        } catch (Exception e) {
            // in the case that there is no image found
            Glide.with(getContext())
                    .asBitmap()
                    .load(R.drawable.image_not_found)
                    .into(ivResultImage);
        }


        return convertView;
    }
}
