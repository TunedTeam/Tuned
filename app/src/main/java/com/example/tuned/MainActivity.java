package com.example.tuned;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.tuned.fragments.ComposeFragment;
import com.example.tuned.fragments.CreateReviewFragment;
import com.example.tuned.fragments.CreateReviewSearchFragment;
import com.example.tuned.fragments.DiscoverFeedFragment;
import com.example.tuned.fragments.PostsFragment;
import com.example.tuned.fragments.ProfileFragment;
import com.example.tuned.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setCustomAnimations(R.anim.slide_up, R.anim.slide_down);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;

                switch (item.getItemId()) {
                    case R.id.action_discover:
                        Toast.makeText(MainActivity.this, "Discover!", Toast.LENGTH_SHORT).show();
                        fragment = new DiscoverFeedFragment();
                        break;
                    case R.id.action_search:
                        Toast.makeText(MainActivity.this, "Search!", Toast.LENGTH_SHORT).show();
                        fragment = new SearchFragment();
                        break;
                    case R.id.action_create:
                        Toast.makeText(MainActivity.this, "Create Review!", Toast.LENGTH_SHORT).show();
                        fragment = new CreateReviewSearchFragment();
//                        getSupportFragmentManager().beginTransaction()
//                                .setCustomAnimations(R.anim.slide_up, R.anim.slide_down)
//                                .replace(R.id.flContainer, fragment)
//                                .addToBackStack(null)
//                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                                .commit();
                        break;
                    case R.id.action_alerts:
                        Toast.makeText(MainActivity.this, "Posts!", Toast.LENGTH_SHORT).show();
                        fragment = new PostsFragment();
                        break;
                    case R.id.action_profile:
                    default:
                        Toast.makeText(MainActivity.this, "Profile!", Toast.LENGTH_SHORT).show();
                        fragment = new ProfileFragment();
                        break;
                }

                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();

                return true;
            }
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_discover);
    }


    public Animation onCreateAnimation(int transit, final boolean enter, int nextAnim) {
        Animation nextAnimation = AnimationUtils.loadAnimation(this, nextAnim);
        nextAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return nextAnimation;
    }
}