package com.mphstar.androidnative.belajar;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mphstar.androidnative.MainActivity;
import com.mphstar.androidnative.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RestApiActivity extends AppCompatActivity {

    ListView list;
    ArrayList<ListViewItems> listnya;
    TextView nodata;
    RelativeLayout loadingIcons;

    String url = "https://reqres.in/api/users?pages=2";

    private void changeStatusBarColor(String color){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_api);
        changeStatusBarColor("#D43232");
        list = findViewById(R.id.listview_items);
        nodata = findViewById(R.id.nodata);
        loadingIcons = findViewById(R.id.loading_listview);
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("data");
                    JSONObject users;
                    listnya = new ArrayList<ListViewItems>();
                    for (int i = 0; i < data.length(); i++) {
                        users = data.getJSONObject(i);
                        listnya.add(new ListViewItems(users.getString("avatar"), users.getString("email"), users.getString("first_name")));
                    }
                    ListViewAdapter adapt = new ListViewAdapter(RestApiActivity.this, listnya);
                    list.setAdapter(adapt);
                    queue.getCache().clear();
                    loadingIcons.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    Toast.makeText(RestApiActivity.this, " You clicked Cancel ", Toast.LENGTH_LONG).show();
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RestApiActivity.this, error.toString(), Toast.LENGTH_LONG).show();
//                Toast.makeText(RestApiActivity.this, "Tidak Ada Koneksi", Toast.LENGTH_LONG).show();
            }
        }
        );

        queue.add(jsonObjectRequest);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                RoundedImageView image = view.findViewById(R.id.image_item);
                LinearLayout titdesc = view.findViewById(R.id.title_desc);
//                titdesc.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(RestApiActivity.this, listnya.get(i).getTitle(), Toast.LENGTH_LONG).show();
//                    }
//                });

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog d = new Dialog(RestApiActivity.this);
                        d.setContentView(R.layout.dialog_profile_reqres);
                        ImageView img = d.findViewById(R.id.image_item);
                        Glide.with(RestApiActivity.this)
                                .load(listnya.get(i).getGambar())
                                .placeholder(R.drawable.placeholder_image)
                                .apply(new RequestOptions()
                                        .centerCrop())
                                .into(img);
                        d.show();
                    }
                });


            }
        });
    }
}

class ListViewAdapter extends ArrayAdapter<ListViewItems> {

    public ListViewAdapter(@NonNull Context context, @NonNull List<ListViewItems> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View listview = view;

        if (listview == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listview = LayoutInflater.from(getContext()).inflate(R.layout.items_listview_restapi, viewGroup, false);
        }

        ListViewItems list = getItem(position);

        TextView title = (TextView) listview.findViewById(R.id.title_list);
        TextView desc = listview.findViewById(R.id.desc_item);
        RoundedImageView image = listview.findViewById(R.id.image_item);

        title.setText(list.getTitle());
        desc.setText(list.getDescription());
        Picasso.get().load(list.getGambar()).into(image);
//        image.setImageResource(R.drawable.ic_profile);
        return listview;
    }
}

class ListViewItems {
    private String gambar, title, description;

    public ListViewItems(String gambar, String title, String description) {
        this.gambar = gambar;
        this.title = title;
        this.description = description;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}