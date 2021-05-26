package com.example.tuned;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Like.class);
        ParseObject.registerSubclass(Comment.class);

        // Register your parse models
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("kZ63bzLOimF20wNdpcWkX8TEsgyVF0ihtSAoWWDY")
                .clientKey("LucDJPDnD3u6b1nCO68FJUxWMWBSDJwAcVFZgmCk")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}

