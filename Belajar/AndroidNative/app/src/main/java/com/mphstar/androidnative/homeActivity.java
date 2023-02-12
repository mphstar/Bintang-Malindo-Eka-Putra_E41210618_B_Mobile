package com.mphstar.androidnative;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;

import com.mphstar.androidnative.belajar.ComboBoxActivity;
import com.mphstar.androidnative.belajar.MoviesActivity;
import com.mphstar.androidnative.belajar.RestApiActivity;
import com.mphstar.androidnative.belajar.pushnotif.push_notifications;
import com.mphstar.androidnative.belajar.scannerqr.ScannerActivity;
import com.mphstar.androidnative.belajar.sqllite.MainSqlLiteActivity;

import java.util.ArrayList;
import java.util.List;

public class homeActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<ListItems> list;
    ArrayList<ListItems> searchItem;
    EditText search;

    private void changeStatusBarColor(String color){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        changeStatusBarColor("#ffffffff");
        searchItem = new ArrayList<ListItems>();
        search = findViewById(R.id.search_content_home);
        gridView = findViewById(R.id.gridview);
        ImageButton btnexit = findViewById(R.id.btn_exit);

        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh = getSharedPreferences("DetailLogin", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sh.edit();
                ed.clear();
                ed.apply();

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        list = new ArrayList<ListItems>();
        list.add(new ListItems(R.drawable.ic_profile, "Rest API + Load Image", "Volley + Picasso", RestApiActivity.class));
        list.add(new ListItems(R.drawable.img2, "Combo Box", "Example Combobox in Android Java", ComboBoxActivity.class));
        list.add(new ListItems(R.drawable.img3, "API Movies", "Simple API Movie with themoviedb.org", MoviesActivity.class));
        list.add(new ListItems(R.drawable.img4, "Push Notifications", "Push Notification with Firebase & PHP", push_notifications.class));
        list.add(new ListItems(R.drawable.img5, "Sqlite", "Save Data Using SQLite", MainSqlLiteActivity.class));
        list.add(new ListItems(R.drawable.img6, "Scanner", "Scanner QRCode or BarCode", ScannerActivity.class));

        ListItemAdapter adapter = new ListItemAdapter(this, list);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in;
                try {
                    in = new Intent(getApplicationContext(), searchItem.get(i).getClassDestination());

                } catch (Exception e){
                    in = new Intent(getApplicationContext(), list.get(i).getClassDestination());
                }

                startActivity(in);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length() != 0){
                    searchItem.clear();
                    for (int i = 0; i < list.size(); i++){
                        if(list.get(i).getTitle().toLowerCase().contains(editable.toString().toLowerCase())){
                            searchItem.add(list.get(i));
                        }
                    }
                    ListItemAdapter adapt = new ListItemAdapter(homeActivity.this, searchItem);
                    gridView.setAdapter(adapt);
                } else {
                    searchItem.clear();
                    ListItemAdapter adapter = new ListItemAdapter(homeActivity.this, list);
                    gridView.setAdapter(adapter);
                }
            }
        });
    }
}