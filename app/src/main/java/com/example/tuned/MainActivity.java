package com.example.tuned;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tuned.fragments.CreateReviewFragment;
import com.example.tuned.fragments.DiscoverFragment;
import com.example.tuned.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;

                switch (item.getItemId()) {
                    case R.id.action_discover:
                        Toast.makeText(MainActivity.this, "Discover!", Toast.LENGTH_SHORT).show();
                        fragment = new DiscoverFragment();
                        break;
                    case R.id.action_search:
                        Toast.makeText(MainActivity.this, "Search!", Toast.LENGTH_SHORT).show();
                        fragment = new DiscoverFragment();
                        break;
                    case R.id.action_create:
                        Toast.makeText(MainActivity.this, "Create Review!", Toast.LENGTH_SHORT).show();
                        fragment = new CreateReviewFragment();
                        break;
                    case R.id.action_alerts:
                        Toast.makeText(MainActivity.this, "Alerts!", Toast.LENGTH_SHORT).show();
                        fragment = new DiscoverFragment();
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
}