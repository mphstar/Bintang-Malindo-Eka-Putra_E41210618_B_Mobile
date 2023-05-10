package com.example.minggu12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {

    RecyclerView listdata;
    LinearLayout loading;
    TextView name;
    ArrayList<ModelPost> mData;

    void loadPost(String id){
        listdata.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest req = new StringRequest(Request.Method.GET, "https://jsonplaceholder.typicode.com/users/"+id+"/posts", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    mData = new ArrayList<>();
                    JSONArray datas = new JSONArray(response);
                    JSONObject datamap;
                    for (int i = 0; i < datas.length(); i++){
                        datamap = datas.getJSONObject(i);
                        mData.add(new ModelPost(datamap.getString("id"), datamap.getString("title"), datamap.getString("body")));
                    }

                    PostAdapter adap = new PostAdapter(mData);
                    listdata.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    listdata.setAdapter(adap);

                    listdata.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    queue.getCache().clear();

                } catch (Exception e){
                    Toast.makeText(PostActivity.this, "Error JSON", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_post);
        listdata = findViewById(R.id.datapost);
        loading = findViewById(R.id.loading);
        name = findViewById(R.id.txt_nama);
        name.setText(getIntent().getStringExtra("name"));

        loadPost(getIntent().getStringExtra("id"));
    }
}

class PostAdapter extends RecyclerView.Adapter<PostAdapter.RecyclerViewViewHolder>{
    private ArrayList<ModelPost> dataList;
    RecyclerViewListener listener;
    private Context context;

    public PostAdapter(ArrayList<ModelPost> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        View view = layout.inflate(R.layout.item_post, parent, false);
        return new PostAdapter.RecyclerViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolder holder, int position) {
        holder.title.setText(dataList.get(position).getTitle());
        holder.body.setText(dataList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    class RecyclerViewViewHolder extends  RecyclerView.ViewHolder {
        TextView title, body;
        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_title);
            body = itemView.findViewById(R.id.txt_body);
        }
    }
}


class ModelPost {
    String id, title, body;

    public ModelPost(String id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}