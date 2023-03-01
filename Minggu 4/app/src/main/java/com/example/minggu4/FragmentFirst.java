package com.example.minggu4;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class FragmentFirst extends Fragment {

    View view;
    Button firstButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first, container, false);
        firstButton = view.findViewById(R.id.firstbutton);
        firstButton.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "First Fragment", Toast.LENGTH_SHORT).show();
        });
        return view;

    }
}
