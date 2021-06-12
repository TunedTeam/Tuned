package com.example.tuned.parse;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Album")
public class AlbumParse extends ParseObject {

    public static final String ALBUM_ID = "albumId";
    public static final String AVERAGE_RATING = "albumAverageRating";

    public String getAlbumId() {
        return getString(ALBUM_ID);
    }

    public void setAlbumId(String albumId) {
        put(ALBUM_ID, albumId);
    }

    public float getAverageRating() {
        return (float) getDouble(AVERAGE_RATING);
    }

    public void setAverageRating(float averageRating) {
        put(AVERAGE_RATING, averageRating);
    }

}
