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
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.tuned.parse.Post;
import com.example.tuned.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class CreateReviewFragment extends Fragment {

    private static final String TAG = "CreateReviewFragment";

    String resultId;
    String resultImageUrl;
    String resultName;
    String resultArtist;
    String resultType;
    int resultReleaseDate;

    ImageView ivResultImage;
    TextView tvResultName;
    TextView tvResultArtist;
    TextView tvResultType;
    TextView tvResultYear;
    TextView tvResultBullet;

    RatingBar ratingBar;

    TextView tvBack;
    TextView tvSave;
    EditText etReviewTitle;
    EditText etReviewComment;

    ScrollView scrollView;

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

        ratingBar = view.findViewById(R.id.ratingBar);

        tvBack = view.findViewById(R.id.tvBack);
        tvSave = view.findViewById(R.id.tvSave);
        etReviewTitle = view.findViewById(R.id.etReviewTitle);
        etReviewComment = view.findViewById(R.id.tvReviewDescription);

        if (getArguments().getString("resultType").equals("album")) {

            resultType = "Album";

            resultId = getArguments().getString("resultId");
            resultImageUrl = getArguments().getString("resultImageUrl");
            resultName = getArguments().getString("resultName");
            resultArtist = getArguments().getString("resultArtist");
            resultReleaseDate = getArguments().getInt("resultReleaseDate");

            tvResultName.setText(resultName);
            tvResultArtist.setText(resultArtist);
            tvResultType.setText(resultType);
            tvResultYear.setText("" + resultReleaseDate);

            Glide.with(getContext())
                    .asBitmap()
                    .load(resultImageUrl)
                    .transform(new CenterCrop(),new RoundedCorners(10))
                    .into(ivResultImage);

        } else if (getArguments().getString("resultType").equals("artist")) {

            resultType = "Artist";

            resultId = getArguments().getString("resultId");
            resultImageUrl = getArguments().getString("resultImageUrl");
            resultName = getArguments().getString("resultName");

            tvResultName.setText(resultName);
            tvResultArtist.setText("");
            tvResultType.setText(resultType);
            tvResultYear.setText("");
            tvResultBullet.setText("");

            Glide.with(getContext())
                    .asBitmap()
                    .load(resultImageUrl)
                    .circleCrop()
                    .into(ivResultImage);

        } else if (getArguments().getString("resultType").equals("track")) {

            resultType = "Track";

            resultId = getArguments().getString("resultId");
            resultImageUrl = getArguments().getString("resultImageUrl");
            resultName = getArguments().getString("resultName");
            resultArtist = getArguments().getString("resultArtist");
            resultReleaseDate = getArguments().getInt("resultReleaseDate");

            tvResultName.setText(resultName);
            tvResultArtist.setText(resultArtist);
            tvResultType.setText(resultType);
            tvResultYear.setText("" + resultReleaseDate);

            Glide.with(getContext())
                    .asBitmap()
                    .load(resultImageUrl)
                    .transform(new CenterCrop(),new RoundedCorners(10))
                    .into(ivResultImage);
        }

        // Save your review post
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Float reviewRating = ratingBar.getRating();
                String reviewTitle = etReviewTitle.getText().toString();
                String reviewComment = etReviewComment.getText().toString();
                String resultName = tvResultName.getText().toString();
                String resultArtist = tvResultArtist.getText().toString();
                String resultType = tvResultType.getText().toString();
                String reviewImage = resultImageUrl;

                if (reviewComment.isEmpty())
                {
                    Toast.makeText(getContext(), "Description cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(reviewRating, reviewTitle, reviewComment, resultName, resultArtist, resultType, resultId, reviewImage, currentUser);
                returnBack();
            }
        });

        // Go back to your old search result
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

    }

    private void savePost(Float reviewRating, String reviewTitle, String reviewComment, String resultName, String resultArtist, String resultType, String resultId, String reviewImage, ParseUser currentUser) {
        Post post = new Post();

        post.setRating(reviewRating);
        post.setReviewTitle(reviewTitle);
        post.setDescription(reviewComment);
        post.setResultName(resultName);
        post.setResultArtist(resultArtist);
        post.setResultType(resultType);
        post.setResultId(resultId);
        post.setResultImageUrl(resultImageUrl);

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

                ratingBar.setRating(0);
                etReviewTitle.setText("");
                etReviewComment.setText("");
            }
        });
    }

    public void returnBack() {
        Toast.makeText(getContext(), "Successfully created review!", Toast.LENGTH_SHORT).show();

        DiscoverFeedFragment discoverFeedFragment = new DiscoverFeedFragment();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, discoverFeedFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}