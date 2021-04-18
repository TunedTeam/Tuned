package com.example.tuned.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tuned.R;
import com.example.tuned.models.Result;

import java.util.List;

public class ResultAdapter extends ArrayAdapter<Result> {

    public ResultAdapter(Context context, int resource, List<Result> resultList) {
        super(context, resource, resultList);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Result result = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.result_cell, parent, false);
        }

        TextView tvResultName = (TextView) convertView.findViewById((R.id.tvResultName));
        ImageView ivResultImage = (ImageView) convertView.findViewById(R.id.ivResultImage);

        tvResultName.setText(result.getResultName());
        ivResultImage.setImageResource(result.getResultImageUrl());

        return convertView;
    }
}
