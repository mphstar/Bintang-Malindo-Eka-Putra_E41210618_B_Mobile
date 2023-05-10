package com.example.minggu12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity implements RecyclerViewListener {

    String url = "https://api.themoviedb.org/3";
    String apikey = "51b76aeef6126a6cb894e4b1840faa8c";

    RecyclerView recyclerView;
    ArrayList<MovieModel> list;
    EditText searchBox;
    ShimmerFrameLayout shimmerMovies;
    SwipeRefreshLayout swipeRefreshLayout;
    MovieAdapter adapt;

    void setRecyclerView(ArrayList<MovieModel> listt){
        adapt = new MovieAdapter(listt, this);
        RecyclerView.LayoutManager layout = new GridLayoutManager(MovieActivity.this, 2);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(adapt);
    }

    void loadDetailMovies(int index, BottomSheetDialog bottomSheetDialog){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + "/movie/"+list.get(index).getMovie_id()+"?api_key=" + apikey, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
//                    JSONArray data = response.getJSONArray("results");
                    ScrollView lay = bottomSheetDialog.findViewById(R.id.content_details_movie);
                    RelativeLayout loading = bottomSheetDialog.findViewById(R.id.loading_details_movie);
                    ImageView image = bottomSheetDialog.findViewById(R.id.image_movie_detail);
                    TextView title = bottomSheetDialog.findViewById(R.id.title_movie_detail);
                    TextView desc = bottomSheetDialog.findViewById(R.id.desc_movie_detail);

                    Glide.with(getApplicationContext())
                            .load("https://image.tmdb.org/t/p/w500/" + response.getString("backdrop_path"))
                            .placeholder(R.drawable.placeholder_image)
                            .apply(new RequestOptions()
                                    .centerCrop())
                            .into(image);

                    title.setText(response.getString("title"));
                    desc.setText(response.getString("overview"));

                    loading.setVisibility(View.GONE);
                    lay.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    Toast.makeText(MovieActivity.this, "Error JSON", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MovieActivity.this, "Tidak Ada Koneksi", Toast.LENGTH_LONG).show();
            }
        }
        );

        queue.add(jsonObjectRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        searchBox = findViewById(R.id.search_movie_field);
        recyclerView = findViewById(R.id.recyclerview);
        shimmerMovies = findViewById(R.id.shimmer_movies);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                shimmerMovies.setVisibility(View.VISIBLE);
                shimmerMovies.startShimmer();
                searchBox.setText("");
                loadMovies();
            }
        });

        loadMovies();

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                recyclerView.setVisibility(View.GONE);
                shimmerMovies.setVisibility(View.VISIBLE);
                shimmerMovies.startShimmer();
                if(editable.toString().length() == 0){
                    loadMovies();
                } else {
                    loadMoviesWithSearch(editable.toString());
                }

            }
        });


    }

    void loadMoviesWithSearch(String keyword){
        RequestQueue queue = Volley.newRequestQueue(MovieActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + "/search/movie?query="+ Uri.parse(keyword.toString()) +"&api_key=" + apikey, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("results");
                    JSONObject result;
                    list = new ArrayList<MovieModel>();
                    for (int i = 0; i < data.length(); i++) {
                        result = data.getJSONObject(i);
                        list.add(new MovieModel(result.getString("id"), result.getString("title"), result.has("release_date") ? result.getString("release_date") : "-", result.getString("vote_average"), "https://image.tmdb.org/t/p/w500/" + result.getString("poster_path")));
                    }

                    setRecyclerView(list);

                    queue.getCache().clear();
                    shimmerMovies.stopShimmer();
                    shimmerMovies.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    recyclerView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    Toast.makeText(MovieActivity.this, " You clicked Cancel ", Toast.LENGTH_LONG).show();
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MovieActivity.this, error.toString(), Toast.LENGTH_LONG).show();
//                Toast.makeText(RestApiActivity.this, "Tidak Ada Koneksi", Toast.LENGTH_LONG).show();
            }
        }
        );
        if(keyword.toString().length() != 0){
            queue.add(jsonObjectRequest);
        }
    }

    void loadMovies(){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + "/movie/popular?api_key=" + apikey, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("results");
                    JSONObject result;
                    list = new ArrayList<MovieModel>();
                    for (int i = 0; i < data.length(); i++) {
                        result = data.getJSONObject(i);
                        list.add(new MovieModel(result.getString("id"), result.getString("title"), result.getString("release_date") == "" ? "-" : result.getString("release_date"), result.getString("vote_average"), "https://image.tmdb.org/t/p/w500/" + result.getString("poster_path")));
                    }

                    setRecyclerView(list);

                    queue.getCache().clear();
                    shimmerMovies.stopShimmer();
                    shimmerMovies.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    recyclerView.setVisibility(View.VISIBLE);


                } catch (JSONException e) {
                    Toast.makeText(MovieActivity.this, " You clicked Cancel ", Toast.LENGTH_LONG).show();
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MovieActivity.this, error.toString(), Toast.LENGTH_LONG).show();
//                Toast.makeText(RestApiActivity.this, "Tidak Ada Koneksi", Toast.LENGTH_LONG).show();
            }
        }
        );

        queue.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View view, int position) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MovieActivity.this);
        bottomSheetDialog.setContentView(R.layout.movie_details);

        loadDetailMovies(position, bottomSheetDialog);

        bottomSheetDialog.show();
    }
}

class  MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    private ArrayList<MovieModel> dataList;
    RecyclerViewListener listener;
    private Context context;
    public MovieAdapter(ArrayList<MovieModel> dataList, RecyclerViewListener listener) {
        this.dataList = dataList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        View view = layout.inflate(R.layout.items_movies, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        holder.title.setText(dataList.get(position).getName());
        holder.date.setText(dataList.get(position).getDate());
        holder.rating.setText(dataList.get(position).getRating());
        Glide.with(context)
                .load(dataList.get(position).getImage())
                .placeholder(R.drawable.placeholder_image)
                .apply(new RequestOptions()
                        .centerCrop())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, date, rating;
        RoundedImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_movie);
            date = itemView.findViewById(R.id.date_release);
            rating = itemView.findViewById(R.id.rating_movie);
            image = itemView.findViewById(R.id.image_movie);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }
}

class MovieModel {
    private String movie_id, name, date, rating, image;

    public MovieModel(String movie_id, String name, String date, String rating, String image) {
        this.movie_id = movie_id;
        this.name = name;
        this.date = date;
        this.rating = rating;
        this.image = image;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}