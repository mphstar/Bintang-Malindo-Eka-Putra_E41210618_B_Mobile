package com.example.minggu5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class FirstActivity extends AppCompatActivity {

    TextView txt_hasil;

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
        setContentView(R.layout.activity_first);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Intent With Argument");
        bar.setDisplayHomeAsUpEnabled(true);
        txt_hasil = findViewById(R.id.txt_hasil);
        txt_hasil.setText(getIntent().getStringExtra("msg"));
    }
}