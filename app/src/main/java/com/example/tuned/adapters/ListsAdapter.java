package com.example.tuned.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tuned.R;
import com.example.tuned.models.Album;
import com.example.tuned.models.SearchResults;

import java.util.List;

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ViewHolder> {

    private Context mContext;
    private List<SearchResults> resultsList;

    public ListsAdapter(Context mContext, List<SearchResults> resultsList) {
        this.mContext = mContext;
        this.resultsList = resultsList;
    }

    @NonNull
    @Override
    public ListsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflator = LayoutInflater.from(mContext);
        view = mInflator.inflate(R.layout.cardview_item_result, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListsAdapter.ViewHolder holder, int position) {

        Glide.with(mContext)
                .load(resultsList.get(position).getImage())
                .placeholder(R.drawable.image_not_found)
                .into(holder.ivResultImage);

        holder.tvResultName.setText(resultsList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivResultImage;
        TextView tvResultName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivResultImage = itemView.findViewById(R.id.ivResultImage);
            tvResultName = itemView.findViewById(R.id.tvResultName);

        }
    }
}
