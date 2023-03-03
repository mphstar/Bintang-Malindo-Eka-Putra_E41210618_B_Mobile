package com.example.minggu5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class UrlActivity extends AppCompatActivity {

    Button btn;
    TextInputEditText field;

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
        setContentView(R.layout.activity_url);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Open URL");
        bar.setDisplayHomeAsUpEnabled(true);
        btn = findViewById(R.id.btn_open);
        field = findViewById(R.id.txt_field);

        btn.setOnClickListener(v -> {
            try {
                if(!URLUtil.isValidUrl(String.valueOf(Uri.parse(field.getText().toString())))){
                    Toast.makeText(UrlActivity.this, " This is not a valid link", Toast.LENGTH_LONG).show();
                } else {
                    Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(field.getText().toString()));
                    startActivity(in);
                }
            } catch (Exception e){
                Toast.makeText(UrlActivity.this, " You don't have any browser to open web page", Toast.LENGTH_LONG).show();
            }


        });
    }

}