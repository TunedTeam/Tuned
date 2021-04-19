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
import com.example.tuned.models.Album;
import com.example.tuned.models.Result;

import java.util.ArrayList;
import java.util.List;

public class ResultAdapter extends ArrayAdapter<Album> {

    private static final String TAG = "ResultAdapter";
    private ArrayList<Album> albums = new ArrayList<>();

    public ResultAdapter(Context context, int resource, ArrayList<Album> albums) {
        super(context, resource, albums);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Album album = getItem(position);

        if (convertView == null) {
            Log.i(TAG, "Is null");
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.result_cell, parent, false);
        }

        TextView tvResultName = (TextView) convertView.findViewById((R.id.tvResultName));
        ImageView ivResultImage =  convertView.findViewById(R.id.ivResultImage);

        tvResultName.setText(album.albumName);
        // ivResultImage.setImageResource(Integer.parseInt(result.albumImageUrl));
//        Glide.with(getContext())
//                .asBitmap()
//                .load(album.get(position).albumImageUrl)
//                .into(ivResultImage);


        return convertView;
    }
}
