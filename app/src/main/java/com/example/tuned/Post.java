package com.example.tuned;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_USER = "user";
    public static final String PROFILE_PICTURE = "profile_picture";
    public static final String KEY_CREATED_KEY = "createdAt";
    public static final String RESULT_ID = "resultId";
    public static final String RESULT_IMAGE_URL = "resultImageUrl";
    public static final String RESULT_NAME = "resultName";
    public static final String RESULT_ARTIST = "resultArtist";
    public static final String RESULT_TYPE = "resultType";
    public static final String REVIEW_TITLE = "reviewTitle";
    public static final String REVIEW_RATING = "reviewRating";

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getProfilePicture() {
        return getUser().getParseFile(PROFILE_PICTURE);
    }

    public void setProfilePicture(ParseFile parseFile) {
        put(PROFILE_PICTURE, parseFile);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public float getRating() {
        return (float) getDouble(REVIEW_RATING);
    }

    public void setRating(float rating) {
        put(REVIEW_RATING, rating);
    }

    public String getResultId() {
        return getString(RESULT_ID);
    }

    public void setResultId(String resultId) {
        put(RESULT_ID, resultId);
    }

    public String getResultImageUrl() {
        return getString(RESULT_IMAGE_URL);
    }

    public void setResultImageUrl(String resultImageUrl) {
        put(RESULT_IMAGE_URL, resultImageUrl);
    }

    public String getReviewTitle() {
        return getString(REVIEW_TITLE);
    }

    public void setReviewTitle(String reviewTitle) {
        put(REVIEW_TITLE, reviewTitle);
    }

    public String getResultName() {
        return getString(RESULT_NAME);
    }

    public void setResultName(String resultName) {
        put(RESULT_NAME, resultName);
    }

    public String getResultArtist() {
        return getString(RESULT_ARTIST);
    }

    public void setResultArtist(String resultArtist) {
        put(RESULT_ARTIST, resultArtist);
    }

    public String getResultType() {
        return getString(RESULT_TYPE);
    }

    public void setResultType(String resultType) {
        put(RESULT_TYPE, resultType);
    }

}
