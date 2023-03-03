package com.example.minggu5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ExplicitActivity extends AppCompatActivity {
    Button btn_act1, btn_act2;

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
        setContentView(R.layout.activity_explicit);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Explicit");
        bar.setDisplayHomeAsUpEnabled(true);
        btn_act1 = findViewById(R.id.btn_passargument);
        btn_act1.setOnClickListener(view -> {
            AlertDialog.Builder build = new AlertDialog.Builder(ExplicitActivity.this);
            build.setTitle("Masukkan Nama");

            EditText field = new EditText(ExplicitActivity.this);
            field.setHint("Masukkan Pesan");
            field.setInputType(InputType.TYPE_CLASS_TEXT);
            field.setPadding(getResources().getDimensionPixelSize(R.dimen.horizontal_margin), 40, getResources().getDimensionPixelSize(R.dimen.horizontal_margin), 40);

            build.setView(field);
            build.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String msg = field.getText().toString();
                    if(msg.equals("")){
                        msg = "Tidak ada pesan yang dikirim";
                    }
                    Intent inten = new Intent(ExplicitActivity.this, FirstActivity.class);
                    inten.putExtra("msg", msg);
                    startActivity(inten);
                }
            });
            build.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog dialog = build.create();
            dialog.show();
        });
        btn_act2 = findViewById(R.id.btn_result);
        btn_act2.setOnClickListener(view -> {
            Intent in = new Intent(ExplicitActivity.this, SecondActivity.class);
            startActivityForResult(in, 1);
        });


    }

    String testing = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                String getMsg = data.getStringExtra("msg");
                testing += getMsg;
                AlertDialog.Builder build = new AlertDialog.Builder(ExplicitActivity.this);
                build.setTitle("Result");
                build.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                build.setMessage(testing);
                AlertDialog dialog = build.create();
                dialog.show();
                Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show();
            }
        }
    }
}