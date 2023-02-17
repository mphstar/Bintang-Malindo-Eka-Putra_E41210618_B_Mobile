package com.mphstar.androidnative.belajar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.mphstar.androidnative.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ComboBoxActivity extends AppCompatActivity {

    // initialize variables
    TextView textView;
    TextView cb_search;
    boolean[] selectedLanguage;
    int selectedCB;
    ArrayList<Integer> langList = new ArrayList<>();
    String[] langArray = {"Java", "C++", "Kotlin", "C", "Python", "Javascript"};
    String[] listWarna = {"Merah", "Hijau", "Kuning", "Biru", "Putih"};

    private void changeStatusBarColor(String color){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }

    private void combo_box_search(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ComboBoxActivity.this);
        builder.setTitle("Pilih warna");
        builder.setSingleChoiceItems(listWarna, selectedCB, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("aaaa", String.valueOf(i).toString());
                selectedCB = i;
                dialogInterface.dismiss();
                Toast.makeText(ComboBoxActivity.this, String.valueOf(i), Toast.LENGTH_SHORT);

                cb_search.setText(listWarna[i]);
            }
        });

        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combo_box);
        changeStatusBarColor("#D43232");
        textView = findViewById(R.id.multiselect);
        cb_search = findViewById(R.id.combobox_with_search);

        selectedLanguage = new boolean[langArray.length];

        cb_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                combo_box_search();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ComboBoxActivity.this);
                builder.setTitle("Select Language");
                builder.setMultiChoiceItems(langArray, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int index, boolean isChecked) {
                        if(isChecked){
                            langList.add(index);
                            // sort array list
                            Collections.sort(langList);
                        } else {
                            langList.remove(Integer.valueOf(index));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int j = 0; j < langList.size(); j++ ){
                            stringBuilder.append(langArray[langList.get(j)]);
                            if(j != langList.size() - 1 ){
                                stringBuilder.append(", ");
                            }
                        }
                        textView.setText(stringBuilder.toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            // remove all selection
                            selectedLanguage[j] = false;
                            // clear language list
                            langList.clear();
                            // clear text view value
                            textView.setText("");
                        }
                    }
                });
                builder.show();
            }
        });
    }
}