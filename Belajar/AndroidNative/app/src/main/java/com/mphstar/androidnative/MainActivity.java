package com.mphstar.androidnative;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends AppCompatActivity {

    TextView email;
    TextView password;
    String token;

    private void changeStatusBarColor(String color) {
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
        setContentView(R.layout.activity_main);
        changeStatusBarColor("#ffffffff");
        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password);

        MaterialButton btn_login = findViewById(R.id.button_login);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    String newtoken = task.getResult();
                    token = newtoken;
                    Log.d("token", newtoken);
                } else {
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(getString(R.string.CHANNEL_ID).toString(), getString(R.string.CHANNEL_NAME), NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(getString(R.string.CHANNEL_DESC));
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    checkLogin(email.getText().toString(), password.getText().toString(), token);
                } catch (JSONException e) {
                    Log.d("mphstar", "hehehe");
                }


//                new SweetAlertDialog(view.getContext(), SweetAlertDialog.WARNING_TYPE)
//                        .setTitleText("Login")
//                        .setContentText("Apakah anda yakin ingin login?")
//                        .setConfirmText("Ya")
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        sweetAlertDialog.cancel();
//                        Intent i = new Intent(getApplicationContext(), homeActivity.class);
//                        startActivity(i);
//                    }
//                }).show();


            }
        });
    }

    void checkLogin(String email, String password, String token) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest requestlogin = new StringRequest(
                Request.Method.POST,
                "https://mphstar.tech/mphstar/login_api.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray data = new JSONArray(response);
                            JSONObject result = data.getJSONObject(0);
                            if (result.getString("status").equals("Berhasil")) {

                                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Berhasil")
                                        .setContentText(result.getString("message"))
                                        .setConfirmText("OK")

                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {


                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                SharedPreferences sh = getSharedPreferences("DetailLogin", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor ed = sh.edit();
                                                ed.putString("email", email);
                                                ed.putBoolean("statusLogin", true);
                                                ed.commit();
                                                ed.apply();

                                                sweetAlertDialog.cancel();
                                                Intent i = new Intent(getApplicationContext(), homeActivity.class);
                                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(i);
                                            }
                                        }).show();


                                Log.d("mphstar", "sukses login");
                            } else {
                                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Berhasil")
                                        .setContentText(result.getString("message"))
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismiss();
                                            }
                                        }).show();
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("mphstar", error.toString());
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("txt_email", email);
                params.put("txt_password", password);
                params.put("txt_token", token);
                return params;
            }
        };
        queue.add(requestlogin);

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sh = getSharedPreferences("DetailLogin", Context.MODE_PRIVATE);
        if(sh.getBoolean("statusLogin", false)){
            Log.d("tes", String.valueOf(sh.getBoolean("statusLogin", false)));
            Intent i = new Intent(getApplicationContext(), homeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }
}