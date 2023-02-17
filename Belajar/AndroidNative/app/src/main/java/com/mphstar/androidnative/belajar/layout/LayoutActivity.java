package com.mphstar.androidnative.belajar.layout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.mphstar.androidnative.R;

public class LayoutActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLayout();
        ActionBar bar = getSupportActionBar();
        bar.setTitle(R.string.appbar_home);
    }

    private void getLayout(){
        LinearLayout linear = findViewById(R.id.activity_linear);
        linear.setOnClickListener(v -> {
            Intent in = new Intent(this, LinearActivity.class);
            startActivity(in);
        });

        LinearLayout relative = findViewById(R.id.activity_relative);
        relative.setOnClickListener(v -> {
            Intent in = new Intent(this, RelativeLayout.class);
            startActivity(in);
        });

        LinearLayout constraint = findViewById(R.id.activity_constraint);
        constraint.setOnClickListener(v -> {
            Intent in = new Intent(this, ConstraintActivity.class);
            startActivity(in);
        });

        LinearLayout frame = findViewById(R.id.activity_frame);
        frame.setOnClickListener(v -> {
            Intent in = new Intent(this, FrameActivity.class);
            startActivity(in);
        });

        LinearLayout table = findViewById(R.id.activity_table);
        table.setOnClickListener(v -> {
            Intent in = new Intent(this, TableActivity.class);
            startActivity(in);
        });

        LinearLayout material = findViewById(R.id.activity_material);
        material.setOnClickListener(v -> {
            Intent in = new Intent(this, MaterialComponentsActivity.class);
            startActivity(in);
        });

        LinearLayout scroll = findViewById(R.id.activity_scroll);
        scroll.setOnClickListener(v -> {
            Intent in = new Intent(this, ScrollviewActivity.class);
            startActivity(in);
        });

        LinearLayout horiscroll = findViewById(R.id.activity_horizontalscrollview);
        horiscroll.setOnClickListener(v -> {
            Intent in = new Intent(this, HorizontalScrollviewActivity.class);
            startActivity(in);
        });
    }
}