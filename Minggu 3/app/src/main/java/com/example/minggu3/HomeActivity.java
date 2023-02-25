package com.example.minggu3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button listview, reycyleview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        listview = findViewById(R.id.part1);
        reycyleview = findViewById(R.id.part2);
        listview.setOnClickListener(view -> {
            Intent in = new Intent(HomeActivity.this, ListViewActivity.class);
            startActivity(in);
        });

        reycyleview.setOnClickListener(view -> {
            Intent in = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(in);
        });
    }
}