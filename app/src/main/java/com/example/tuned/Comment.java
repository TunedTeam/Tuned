package com.example.tuned;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("Comment")
public class Comment extends ParseObject {
    public static final String POST_ID = "postId";
    public static final String USER_ID = "userId";
    public static final String LIKED_BY_USER = "likedByUser";

    public String getPostId() {
        return getString(POST_ID);
    }

    public void setPostId(String postId) {
        put(POST_ID, postId);
    }

    public String getUserId() {
        return getString(USER_ID);
    }

    public void setUserId(String userId) {
        put(USER_ID, userId);
    }

    public List<String> getLikedbyUser() {
        return getList(LIKED_BY_USER);
    }

    public void setLikedByUser(List<String> likedByUser) {
        put(LIKED_BY_USER, likedByUser);
    }
}
