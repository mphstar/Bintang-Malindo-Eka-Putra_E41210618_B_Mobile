package com.example.minggu5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

public class ImplicitActivity extends AppCompatActivity {

    Button btn_url, btn_whatsapp;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implicit);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Implicit");
        bar.setDisplayHomeAsUpEnabled(true);
        btn_url = findViewById(R.id.btn_url);
        btn_url.setOnClickListener(v -> {
            startActivity(new Intent(ImplicitActivity.this, UrlActivity.class));
        });
        btn_whatsapp = findViewById(R.id.btn_whatsapp);
        btn_whatsapp.setOnClickListener(v -> {
            startActivity(new Intent(ImplicitActivity.this, WhatsappActivity.class));
        });
    }


}