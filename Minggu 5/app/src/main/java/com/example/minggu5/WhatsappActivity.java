package com.example.minggu5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WhatsappActivity extends AppCompatActivity {

    TextInputEditText txt_no, txt_messages;
    CheckBox btn_checkbox;
    Button btn_open;
    boolean isChecked = true;

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
        setContentView(R.layout.activity_whatsapp);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Send WhatsApp");
        bar.setDisplayHomeAsUpEnabled(true);
        txt_no = findViewById(R.id.txt_nohp);
        btn_open = findViewById(R.id.btn_open);
        txt_messages = findViewById(R.id.txt_field);
        btn_checkbox = findViewById(R.id.btn_checkbox);

        btn_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    isChecked = true;
                    txt_no.setEnabled(true);
                } else {
                    isChecked = false;
                    txt_no.setEnabled(false);
                }
            }
        });

        btn_open.setOnClickListener(view -> {
            String msg = txt_messages.getText().toString();
            String no = txt_no.getText().toString().replace("+", "").replace(" ", "");
            if(isChecked){
                try {
                    String url = "https://api.whatsapp.com/send?phone="+no+"&text=" + URLEncoder.encode(msg, "UTF-8");
                    Intent inten = new Intent(Intent.ACTION_VIEW);
                    inten.setPackage("com.whatsapp");
                    inten.setData(Uri.parse(url));
                    if(msg.equals("") || no.equals("")){
                        Toast.makeText(WhatsappActivity.this, "Isi Messages / NO Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                    } else if(!String.valueOf(no.charAt(0)).equals("6") && !String.valueOf(no.charAt(1)).equals("2")){
                        Toast.makeText(WhatsappActivity.this, "No Telepon Tidak Valid", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(inten);
                    }

                } catch (Exception e) {
                    Toast.makeText(WhatsappActivity.this, "Please Install WhatsApp First", Toast.LENGTH_SHORT).show();

                }
            } else {
                Intent inten = new Intent(Intent.ACTION_SEND);
                inten.setType("text/plain");
                inten.setPackage("com.whatsapp");
                inten.putExtra(Intent.EXTRA_TEXT, msg);
                try {
                    startActivity(inten);
                } catch (Exception e){
                    Toast.makeText(WhatsappActivity.this, "Please Install WhatsApp First", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}