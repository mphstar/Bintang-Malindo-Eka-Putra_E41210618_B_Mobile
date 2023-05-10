package com.example.minggu12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonRestApi extends AppCompatActivity implements RecyclerViewListener{

    RecyclerView listdata;
    ArrayList<ModelUser> mData;

    void loadUsers(){
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest req = new StringRequest(Request.Method.GET, "https://jsonplaceholder.typicode.com/users", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    mData = new ArrayList<>();
                    JSONArray datas = new JSONArray(response);
                    JSONObject datamap;
                    for (int i = 0; i < datas.length(); i++){
                        datamap = datas.getJSONObject(i);
                        mData.add(new ModelUser(datamap.getString("id"), datamap.getString("name"), datamap.getString("email"), datamap.getString("username")));
                    }

                    UsersAdapter adap = new UsersAdapter(mData, JsonRestApi.this);
                    listdata.setLayoutManager(new LinearLayoutManager(JsonRestApi.this));
                    listdata.setAdapter(adap);

                } catch (Exception e){
                    Toast.makeText(JsonRestApi.this, "Error JSON", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });

        queue.add(req);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_rest_api);
        listdata = findViewById(R.id.datausers);

        loadUsers();
    }

    @Override
    public void onClick(View view, int position) {
        Intent in = new Intent(JsonRestApi.this, PostActivity.class);
        in.putExtra("name", mData.get(position).getNama());
        in.putExtra("id", mData.get(position).getId());
        startActivity(in);
    }
}

class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.RecyclerViewViewHolder>{
    private ArrayList<ModelUser> dataList;
    RecyclerViewListener listener;
    private Context context;

    public UsersAdapter(ArrayList<ModelUser> dataList, RecyclerViewListener listener) {
        this.dataList = dataList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        View view = layout.inflate(R.layout.item_user, parent, false);
        return new UsersAdapter.RecyclerViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolder holder, int position) {
        holder.nama.setText(dataList.get(position).getNama());
        holder.email.setText(dataList.get(position).getEmail());
        holder.username.setText(dataList.get(position).getUsername());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    class RecyclerViewViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nama, email, username;
        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.txt_nama);
            email = itemView.findViewById(R.id.txt_email);
            username = itemView.findViewById(R.id.txt_username);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }
}


class ModelUser {
    String id, nama, email, username;

    public ModelUser(String id, String nama, String email, String username) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}