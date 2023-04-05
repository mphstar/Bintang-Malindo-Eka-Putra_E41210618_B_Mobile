package com.example.minggu8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

public class MainSensorActivity extends AppCompatActivity {

    Button allsensor, lightsensor, proxsensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sensor);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Sensors");
        bar.setDisplayHomeAsUpEnabled(true);
        allsensor = findViewById(R.id.all_sensor);
        allsensor.setOnClickListener(v -> {
            startActivity(new Intent(this, AllSensor.class));
        });
        lightsensor = findViewById(R.id.lightsensor);
        lightsensor.setOnClickListener(v -> {
            startActivity(new Intent(this, LightSensor.class));
        });

        proxsensor = findViewById(R.id.proximitysensor);
        proxsensor.setOnClickListener(v -> {
            startActivity(new Intent(this, ProximitySensor.class));
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}