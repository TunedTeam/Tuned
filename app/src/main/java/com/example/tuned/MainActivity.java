package com.example.tuned;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.tuned.fragments.ComposeFragment;
import com.example.tuned.fragments.CreateReviewFragment;
import com.example.tuned.fragments.CreateReviewSearchFragment;
import com.example.tuned.fragments.DiscoverFeedFragment;
import com.example.tuned.fragments.ListsFeedFragment;
import com.example.tuned.fragments.PostsFragment;
import com.example.tuned.fragments.ProfileFragment;
import com.example.tuned.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fabCreate;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setCustomAnimations(R.anim.slide_up, R.anim.slide_down);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);

        fabCreate = findViewById(R.id.fabCreate);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;

                fabCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fragment fragment = new CreateReviewSearchFragment();

                        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                        //fabCreate.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.lavender)));
                    }
                });

                switch (item.getItemId()) {
                    case R.id.miDiscover:
                        //Toast.makeText(MainActivity.this, "Discover!", Toast.LENGTH_SHORT).show();
                        fragment = new DiscoverFeedFragment();
                        break;
                    case R.id.miSearch:
                        //Toast.makeText(MainActivity.this, "Search!", Toast.LENGTH_SHORT).show();
                        fragment = new SearchFragment();
                        break;
//                    case R.id.fabCreate:
//                        Toast.makeText(MainActivity.this, "Create Review!", Toast.LENGTH_SHORT).show();
//                        fragment = new CreateReviewSearchFragment();
////                        getSupportFragmentManager().beginTransaction()
////                                .setCustomAnimations(R.anim.slide_up, R.anim.slide_down)
////                                .replace(R.id.flContainer, fragment)
////                                .addToBackStack(null)
////                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
////                                .commit();
//                        break;
                    case R.id.miAlerts:
                        //Toast.makeText(MainActivity.this, "Posts!", Toast.LENGTH_SHORT).show();
                        fragment = new PostsFragment();
                        break;
                    case R.id.miProfile:
                    default:
                        //Toast.makeText(MainActivity.this, "Profile!", Toast.LENGTH_SHORT).show();
                        fragment = new ProfileFragment();
                        break;
                }

                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment, fragment.getTag()).commit();

                return true;
            }
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.miDiscover);
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