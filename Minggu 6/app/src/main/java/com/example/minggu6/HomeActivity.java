package com.example.minggu6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button btn_file, btn_sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn_file = findViewById(R.id.btn_file);
        btn_sqlite = findViewById(R.id.btn_sqlite);

        btn_file.setOnClickListener(view -> {
            Intent in = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(in);
        });

        btn_sqlite.setOnClickListener(view -> {
            Intent in = new Intent(HomeActivity.this, SqliteActivity.class);
            startActivity(in);
        });
    }
}