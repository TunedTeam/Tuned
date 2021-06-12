package com.example.tuned.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.tuned.R;

public class AddToListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_list);
    }

    // if list is empty ==> make create list button invisible
}