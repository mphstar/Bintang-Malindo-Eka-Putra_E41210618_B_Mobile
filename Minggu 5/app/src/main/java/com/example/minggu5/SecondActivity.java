package com.example.minggu5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class SecondActivity extends AppCompatActivity {

    TextInputEditText txt_input;
    Button btn;

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
        setContentView(R.layout.activity_second);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Activity With Result");
        bar.setDisplayHomeAsUpEnabled(true);
        txt_input = findViewById(R.id.txt_field);
        btn = findViewById(R.id.btn_submit);

        btn.setOnClickListener(view -> {
            String msg = txt_input.getText().toString();
            if(msg.equals("")){
                msg = "Tidak ada pesan yang dikirim";
            }

            Intent inten = new Intent();
            inten.putExtra("msg", msg);
            setResult(RESULT_OK, inten);
            finish();
        });
    }
}