package com.example.minggu7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView nama = findViewById(R.id.tv_namaMain);

        nama.setText(getLoggedInUser());

        findViewById(R.id.button_logoutMain).setOnClickListener((v -> {

            SharedPreferences sh = getSharedPreferences("DetailAkun", Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sh.edit();
            ed.remove("statusLogin");
            ed.apply();

            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }));
    }

    private String getLoggedInUser(){
        SharedPreferences sh = getSharedPreferences("DetailAkun", Context.MODE_PRIVATE);
        return sh.getString("username", "");
    }
}