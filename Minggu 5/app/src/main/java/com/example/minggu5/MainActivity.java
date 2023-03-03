package com.example.minggu5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_implicit, btn_explicit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_implicit = findViewById(R.id.btn_implicit);
        btn_explicit = findViewById(R.id.btn_explicit);

        btn_implicit.setOnClickListener(v -> {
            Intent in = new Intent(MainActivity.this, ImplicitActivity.class);
            startActivity(in);
        });

        btn_explicit.setOnClickListener(v -> {
            Intent in = new Intent(MainActivity.this, ExplicitActivity.class);
            startActivity(in);
        });
    }
}