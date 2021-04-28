package com.example.tuned.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tuned.Post;
import com.example.tuned.R;
import com.example.tuned.models.Album;
import com.example.tuned.models.SearchResults;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class CreateReviewFragment extends Fragment {

    private static final String TAG = "CreateReviewFragment";

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
    TextView tvResultBullet;

    TextView tvBack;
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
        tvResultBullet = view.findViewById(R.id.tvResultBullet);

        tvBack = view.findViewById(R.id.tvBack);
        tvSave = view.findViewById(R.id.tvSave);
        etReviewComment = view.findViewById(R.id.etReviewComment);

        if (getArguments().getString("resultType").equals("album")) {

            resultType = "Album";

            albumId = getArguments().getString("albumId");
            albumImageUrl = getArguments().getString("resultImageUrl");
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
            artistImageUrl = getArguments().getString("resultImageUrl");
            artistName = getArguments().getString("artistName");

            tvResultName.setText(artistName);
            tvResultArtist.setText("");
            tvResultType.setText(resultType);
            tvResultYear.setText("");
            tvResultBullet.setText("");

            Glide.with(getContext())
                    .asBitmap()
                    .load(artistImageUrl)
                    .circleCrop()
                    .into(ivResultImage);

        } else if (getArguments().getString("resultType").equals("track")) {

            resultType = "Track";

            trackId = getArguments().getString("trackId");
            trackImageUrl = getArguments().getString("resultImageUrl");
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

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateReviewSearchFragment createReviewSearchFragment = new CreateReviewSearchFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContainer, createReviewSearchFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }

    private void savePost(String reviewComment, ParseUser currentUser) {
        Post post = new Post();
        post.setDescription(reviewComment);

        //post.setImage(new ParseFile(photoFile));

        //post.setImage(getArguments().getString("resultImageUrl"));

        post.setUser(currentUser);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null)
                {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "Error while saving!", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Post save was successful!");
                etReviewComment.setText("");
            }
        });
    }
}