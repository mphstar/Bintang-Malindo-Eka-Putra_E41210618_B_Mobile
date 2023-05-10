package com.example.minggu12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button moviedb, json_manual, restapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moviedb = findViewById(R.id.btn_movie);
        json_manual = findViewById(R.id.btn_json_manual);
        restapi = findViewById(R.id.btn_restapi);

        moviedb.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, MovieActivity.class));
        });

        json_manual.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, JsonManualActivity.class));
        });

        restapi.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, JsonRestApi.class));
        });
    }
}