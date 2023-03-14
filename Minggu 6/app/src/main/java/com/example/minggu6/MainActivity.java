package com.example.minggu6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    TextInputEditText txt_teks;
    Button btn_private, btn_public, btn_view;

    String[] view = {"Private", "Public"};

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
        setContentView(R.layout.activity_main);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("File Management");
        bar.setDisplayHomeAsUpEnabled(true);
        txt_teks = findViewById(R.id.txt_teks);
        btn_private = findViewById(R.id.btn_private);
        btn_public = findViewById(R.id.btn_public);
        btn_view = findViewById(R.id.btn_view);

        btn_view.setOnClickListener(view -> {
            AlertDialog.Builder build = new AlertDialog.Builder(this);
            build.setTitle("Pilih Menu");
            build.setItems(this.view, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Hasil");
                    if(i == 0){
                        builder.setMessage(getPrivate());
                    } else {
                        builder.setMessage(getPublic());
                    }
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog d = builder.create();
                    d.show();
                }
            });
            AlertDialog dialog = build.create();
            dialog.show();
        });

        btn_public.setOnClickListener(view -> {
            savePublic(txt_teks.getText().toString());
            txt_teks.setText("");
        });

        btn_private.setOnClickListener(view -> {
            savePrivate(txt_teks.getText().toString());
            txt_teks.setText("");
        });
    }

    String getPrivate(){
        File folder = getExternalFilesDir("mphstar");
        File myfile = new File(folder, "teks_private.txt");
        String teks = getData(myfile);
        return teks;
    }

    String getPublic(){
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File myfile = new File(folder, "teks_public.txt");
        String teks = getData(myfile);
        return teks;
    }

    String getData(File myfile){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(myfile);
            int i = -1;
            StringBuffer buffer = new StringBuffer();
            while ((i = fileInputStream.read()) != -1 ){
                buffer.append((char) i);
            }
            return  buffer.toString();
        } catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (Exception e){
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        return null;
    }

    void savePublic(String teks){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 23);
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File myfile = new File(folder, "teks_public.txt");
        writeData(myfile, teks);

    }

    void savePrivate(String teks){
        File folder = getExternalFilesDir("mphstar");
        File myFile = new File(folder, "teks_private.txt");
        writeData(myFile, teks);
    }

    void writeData(File myfile, String data){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(myfile);
            fileOutputStream.write(data.getBytes());
            Toast.makeText(this, "Done " + myfile.getAbsolutePath(), Toast.LENGTH_SHORT).show();

        } catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        } finally {
            if(fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (Exception e){
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}