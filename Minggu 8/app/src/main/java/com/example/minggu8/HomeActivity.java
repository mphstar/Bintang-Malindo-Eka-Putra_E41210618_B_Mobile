package com.example.minggu8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button maps, sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        maps = findViewById(R.id.maps);
        sensor = findViewById(R.id.sensor);

        sensor.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, MainSensorActivity.class));
        });
        maps.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
        });
    }
}