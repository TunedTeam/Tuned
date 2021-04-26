package com.example.tuned.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tuned.R;
import com.example.tuned.models.Album;
import com.example.tuned.models.SearchResults;
import com.parse.ParseUser;

public class CreateReviewFragment extends Fragment {

    String resultType;

    String albumId;
    String albumImageUrl;
    String albumName;
    String albumArtist;
    int albumReleaseDate;

    String artistId;
    String artistImageUrl;
    String artistName;

    String trackId;
    String trackImageUrl;
    String trackName;
    String trackArtist;
    int trackReleaseDate;

    ImageView ivResultImage;
    TextView tvResultName;
    TextView tvResultArtist;
    TextView tvResultType;
    TextView tvResultYear;

    TextView tvSave;
    EditText etReviewComment;

    public CreateReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivResultImage = view.findViewById(R.id.ivResultImage);
        tvResultName = view.findViewById(R.id.tvResultName);
        tvResultArtist = view.findViewById(R.id.tvResultArtist);
        tvResultType = view.findViewById(R.id.tvResultType);
        tvResultYear = view.findViewById(R.id.tvResultYear);

        tvSave = view.findViewById(R.id.tvSave);
        etReviewComment = view.findViewById(R.id.etReviewComment);

        if (getArguments().getString("resultType").equals("album")) {

            resultType = "Album";

            albumId = getArguments().getString("albumId");
            albumImageUrl = getArguments().getString("albumImageUrl");
            albumName = getArguments().getString("albumName");
            albumArtist = getArguments().getString("albumArtist");
            albumReleaseDate = getArguments().getInt("albumReleaseDate");

            tvResultName.setText(albumName);
            tvResultArtist.setText(albumArtist);
            tvResultType.setText(resultType);
            tvResultYear.setText("" + albumReleaseDate);

            Glide.with(getContext())
                    .asBitmap()
                    .load(albumImageUrl)
                    .into(ivResultImage);

        } else if (getArguments().getString("resultType").equals("artist")) {

            resultType = "Artist";

            artistId = getArguments().getString("artistId");
            artistImageUrl = getArguments().getString("artistImageUrl");
            artistName = getArguments().getString("artistName");

            tvResultName.setText(artistName);
            tvResultArtist.setText("");
            tvResultType.setText(resultType);
            tvResultYear.setText("");

            Glide.with(getContext())
                    .asBitmap()
                    .load(artistImageUrl)
                    .circleCrop()
                    .into(ivResultImage);

        } else if (getArguments().getString("resultType").equals("track")) {

            resultType = "Track";

            trackId = getArguments().getString("trackId");
            trackImageUrl = getArguments().getString("trackImageUrl");
            trackName = getArguments().getString("trackName");
            trackArtist = getArguments().getString("trackArtist");
            trackReleaseDate = getArguments().getInt("trackReleaseDate");

            tvResultName.setText(trackName);
            tvResultArtist.setText(trackArtist);
            tvResultType.setText(resultType);
            tvResultYear.setText("" + trackReleaseDate);

            Glide.with(getContext())
                    .asBitmap()
                    .load(trackImageUrl)
                    .into(ivResultImage);
        }

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reviewComment = etReviewComment.getText().toString();

                if (reviewComment.isEmpty())
                {
                    Toast.makeText(getContext(), "Description cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(reviewComment, currentUser);
            }
        });

    }

    private void savePost(String reviewComment, ParseUser currentUser) {

    }
}