package com.example.minggu4;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class FragmentSecond extends Fragment {

    View view;
    Button secondButton, testButton;
    String aaa = "uwoghh";

    View parentview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_second, container, false);
        parentview = container.getRootView();
        testButton = view.findViewById(R.id.testButton);
        Button btn = parentview.findViewById(R.id.second_fragment);

        secondButton = view.findViewById(R.id.secondbutton);
        secondButton.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Second Fragment", Toast.LENGTH_SHORT).show();
        });

        testButton.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Try Access Activity Variable From Fragment", Toast.LENGTH_SHORT).show();
            ((MainActivity) getActivity()).tes = "Bintang";
            btn.setText(((MainActivity) getActivity()).tes);
        });
        return view;

    }
}
