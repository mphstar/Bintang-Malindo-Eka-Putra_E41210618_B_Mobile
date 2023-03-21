package com.example.minggu7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    EditText username, pass, pass2;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.et_emailSignup);
        pass = findViewById(R.id.et_passwordSignup);
        pass2 = findViewById(R.id.et_passwordSignup2);
        btn = findViewById(R.id.button_SignupSignup);
        btn.setOnClickListener(v -> {
            if(pass.getText().toString().equals(pass2.getText().toString())){
                SharedPreferences sp = getSharedPreferences("DetailAkun", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("username", username.getText().toString());
                editor.putString("pass", pass.getText().toString());
                editor.commit();
                editor.apply();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }
}