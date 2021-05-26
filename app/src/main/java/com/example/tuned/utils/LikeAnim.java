package com.example.tuned.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.example.tuned.R;

public class LikeAnim {
    private static final String TAG = "LikeAnim";

    private static final DecelerateInterpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();

    public ImageView likeHeart;

    public LikeAnim(ImageView likeHeart) {
        this.likeHeart = likeHeart;
    }

    public void toggleLikeOutline(Context context) {

        Log.d(TAG, "toggleLikeOutline: toggling like heart");

        AnimatorSet animationSet = new AnimatorSet();

        Log.d(TAG, "toggledlike: toggling purple heart off.");

        likeHeart.setScaleX(0.1f);
        likeHeart.setScaleY(0.1f);

        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(likeHeart, "scaleY", 1f, 0f);
        scaleDownY.setDuration(300);
        scaleDownY.setInterpolator(ACCELERATE_INTERPOLATOR);


        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(likeHeart, "scaleX", 1f, 0f);
        scaleDownX.setDuration(300);
        scaleDownX.setInterpolator(ACCELERATE_INTERPOLATOR);

//        Drawable heartDrawable = context.getDrawable(R.drawable.ic_like_outline);
//        likeHeart.setImageDrawable(heartDrawable);

        animationSet.playTogether(scaleDownY, scaleDownX);

        animationSet.start();
    }

    public void toggleLikeFilled(Context context) {

        Log.d(TAG, "toggleLikeFilled: toggling like heart");

        AnimatorSet animationSet = new AnimatorSet();

        Log.d(TAG, "toggledlike: toggling purple heart on.");

        likeHeart.setScaleX(0.1f);
        likeHeart.setScaleY(0.1f);

        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(likeHeart, "scaleY", 0.1f, 1f);
        scaleDownY.setDuration(300);
        scaleDownY.setInterpolator(DECELERATE_INTERPOLATOR);


        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(likeHeart, "scaleX", 0.1f, 1f);
        scaleDownX.setDuration(300);
        scaleDownX.setInterpolator(DECELERATE_INTERPOLATOR);

        Drawable heartDrawable = context.getDrawable(R.drawable.ic_like_filled);
        likeHeart.setImageDrawable(heartDrawable);

        animationSet.playTogether(scaleDownY, scaleDownX);

        animationSet.start();
    }
}
