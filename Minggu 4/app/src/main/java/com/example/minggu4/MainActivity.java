package com.example.minggu4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button firstFragment, secondFragment;
    String tes = "mphstar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstFragment = findViewById(R.id.first_fragment);
        secondFragment = findViewById(R.id.second_fragment);

        firstFragment.setOnClickListener(view -> {
            loadFragment(new FragmentFirst());
        });

        secondFragment.setOnClickListener(view -> {
            loadFragment(new FragmentSecond());
        });
    }

    private void loadFragment(Fragment f){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, f);
        fragmentTransaction.commit();
    }
}