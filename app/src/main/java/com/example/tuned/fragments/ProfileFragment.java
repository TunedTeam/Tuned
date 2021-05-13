package com.example.tuned.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tuned.LoginActivity;
import com.example.tuned.MainActivity;
import com.example.tuned.Post;
import com.example.tuned.adapters.PostsAdapter;
import com.example.tuned.R;
import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    public static final String PROFILE_PICTURE = "profile_picture";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    public final static int PICK_PHOTO_CODE = 1046;
    private File photoFile;
    private String photoFileName = "photo.jpg";
    private List<Post> userPosts;
    ParseUser parseUser;
    PostsAdapter postsAdapter;
    Button btnLogout;
    ImageView ivProfilePicture;
    TextView tvUserName;
    RecyclerView rvUserPosts;
    Post post;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        parseUser = ParseUser.getCurrentUser();
        userPosts = new ArrayList<>();
        postsAdapter = new PostsAdapter(getContext(), userPosts);

        btnLogout = view.findViewById(R.id.btnLogout);
        ivProfilePicture = view.findViewById(R.id.ivProfilePicture);

        tvUserName = view.findViewById(R.id.tvUsername);

        rvUserPosts = view.findViewById(R.id.rvUserPosts);

        rvUserPosts.setAdapter(postsAdapter);
        rvUserPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        queryPosts();

        tvUserName.setText("@" + parseUser.getUsername());

        ParseFile userProfilePic = (ParseFile) parseUser.get(PROFILE_PICTURE);
        if (userProfilePic != null)
            Glide.with(this).load(userProfilePic.getUrl())
                    .placeholder(R.drawable.defaultavatar)
                    .circleCrop()
                    .into(ivProfilePicture);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogout();
            }
        });

        ivProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Add Photo!");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo")) {
                           // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                           // startActivityForResult(intent, RESULT_OK);
                            launchCamera();
                        } else if (options[item].equals("Choose from Gallery")) {
                            onPickPhoto(v);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();


            }
        });
    }


    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.setLimit(20);

        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername()
                            + ParseUser.getCurrentUser().get("profile_picture"));

                }

                userPosts.addAll(posts);
                postsAdapter.notifyDataSetChanged();
            }
        });
    }



    // Trigger gallery selection for a photo
    public void onPickPhoto(View view) {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);


        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }


    public Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;

        try {
            // check version of Android on device
            if(Build.VERSION.SDK_INT > 27){
                // on newer versions of Android, use the new decodeBitmap method
                ImageDecoder.Source source = ImageDecoder.createSource(this.getContext().getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                // support older versions of Android by using getBitmap
                image = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                ivProfilePicture.setImageBitmap(takenImage);

                saveProfilePicture();

            }

            else {
                Toast.makeText(getContext(), "Picture was not taken!", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == PICK_PHOTO_CODE && (data != null)){

                Uri photoUri = data.getData();
                // Uri fileProvider = data.getData();

                // Load the image located at photoUri into selectedImage
                 Bitmap selectedImage = loadFromUri(photoUri);
                //Bitmap selectedImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

                    // Load the selected image into a preview
                    // ImageView ivProfilePicture = (ImageView) getView().findViewById(R.id.ivProfilePicture);
                    ivProfilePicture.setImageBitmap(selectedImage);
                    saveProfilePicture();

        }
    }

    private void saveProfilePicture() {

        parseUser.put(PROFILE_PICTURE, new ParseFile(photoFile));

        parseUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e != null) {
                    Log.d(TAG, "Image failed to upload!", e);
                    Toast.makeText(getContext(), "Image failed to upload!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.i(TAG, "Image was successfully saved!");
                Toast.makeText(getContext(), "Image was successfully saved!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //launches the camera and get photo
    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    private void userLogout() {
        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error logging out!", e);
                    Toast.makeText(getContext(), "Error logging out!", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Successfully logged out!");
                Toast.makeText(getContext(), "Successfully logged out", Toast.LENGTH_SHORT).show();
                goLoginActivity();
            }
        });
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
    }

    private void goLoginActivity() {
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }
}


