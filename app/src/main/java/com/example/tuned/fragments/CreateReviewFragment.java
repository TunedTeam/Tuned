package com.example.tuned.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tuned.LoginActivity;
import com.example.tuned.MainActivity;
import com.example.tuned.Review;
import com.example.tuned.ReviewAdapter;
import com.example.tuned.R;
import com.example.tuned.Review;
import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateReviewFragment extends Fragment {

    public static final String TAG = "CreateReview";
    protected ReviewAdapter adapter;
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    private EditText etDescription;
    private Button btnSubmit;
    private Button btnLogout;


    public CreateReviewFragment() {
        // Required empty public constructor
    }


    // The onCreateView method is called when Fragment should create its View object hierarchy,
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_createreview, container, false);
    }


    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etDescription = view.findViewById(R.id.etDescription);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnLogout = view.findViewById(R.id.btnLogout);

        // queryPosts();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = etDescription.getText().toString();

                if (description.isEmpty())
                {
                    Toast.makeText(getContext(), "Review cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                ParseUser currentUser = ParseUser.getCurrentUser();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogout();
            }
        });

    }

    private void userLogout() {
        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error logging out!", e);
                    Toast.makeText(getContext(), "Erorr logging out!", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Successfully logged out!");
                Toast.makeText(getContext(), "Successfully logged out", Toast.LENGTH_SHORT).show();
                goLoginActivity();
            }
        });
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
    }


    private void goLoginActivity() {
        Intent i  = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        getActivity().finish();
    }


    private void savePost(String description, ParseUser currentUser) {
        Review post = new Review();
        post.setDescription(description);

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
                etDescription.setText("");

            }
        });
    }


    protected void queryPosts() {
        ParseQuery<Review> query = ParseQuery.getQuery(Review.class);
        query.include(Review.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Review.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Review>() {
            @Override
            public void done(List<Review> posts, ParseException e) {
                if (e != null)
                {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                for (Review post : posts)
                {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }

                adapter.clear();
                //allPosts.addAll(posts);
                //swipeContainer.setRefreshing(false);
                //adapter.notifyDataSetChanged();
            }


        });
    }

}