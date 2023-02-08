package com.mphstar.androidnative.belajar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.mphstar.androidnative.ListItems;
import com.mphstar.androidnative.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MoviesActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<ListMovies> list;
    EditText searchBox;
    ShimmerFrameLayout shimmerMovies;
    SwipeRefreshLayout swipeRefreshLayout;

    View bottomSheetLayout;

    String url = "https://api.themoviedb.org/3";
    String apikey = "51b76aeef6126a6cb894e4b1840faa8c";
    String accessToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1MWI3NmFlZWY2MTI2YTZjYjg5NGU0YjE4NDBmYWE4YyIsInN1YiI6IjYzNmQxY2E2MDQ5OWYyMDBjZDNjYmQ3YSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.l5ZKARjuegZ4oSuDdxL5Cts7soAs5sQtlWwN63vd6eg";

    private void changeStatusBarColor(String color){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
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
//                    Toast.makeText(MoviesActivity.this, response.getString("title"), Toast.LENGTH_LONG).show();
                    desc.setText(response.getString("overview"));

                    loading.setVisibility(View.GONE);
                    lay.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    Toast.makeText(MoviesActivity.this, " You clicked Cancel ", Toast.LENGTH_LONG).show();
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MoviesActivity.this, error.toString(), Toast.LENGTH_LONG).show();
//                Toast.makeText(RestApiActivity.this, "Tidak Ada Koneksi", Toast.LENGTH_LONG).show();
            }
        }
        );

        queue.add(jsonObjectRequest);
    }

    void loadMovies(){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + "/movie/popular?api_key=" + apikey, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("results");
                    JSONObject result;
                    list = new ArrayList<ListMovies>();
                    for (int i = 0; i < data.length(); i++) {
                        result = data.getJSONObject(i);
                        list.add(new ListMovies(result.getString("id"), result.getString("title"), result.getString("release_date") == "" ? "-" : result.getString("release_date"), result.getString("vote_average"), "https://image.tmdb.org/t/p/w500/" + result.getString("poster_path")));
                    }
                    ListMovieAdapter adapt = new ListMovieAdapter(MoviesActivity.this, list);
                    gridView.setAdapter(adapt);
                    queue.getCache().clear();
                    shimmerMovies.stopShimmer();
                    shimmerMovies.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    gridView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    Toast.makeText(MoviesActivity.this, " You clicked Cancel ", Toast.LENGTH_LONG).show();
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MoviesActivity.this, error.toString(), Toast.LENGTH_LONG).show();
//                Toast.makeText(RestApiActivity.this, "Tidak Ada Koneksi", Toast.LENGTH_LONG).show();
            }
        }
        );

        queue.add(jsonObjectRequest);
    }

    void loadMoviesWithSearch(String keyword){
        RequestQueue queue = Volley.newRequestQueue(MoviesActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + "/search/movie?query="+ Uri.parse(keyword.toString()) +"&api_key=" + apikey, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("results");
                    JSONObject result;
                    list = new ArrayList<ListMovies>();
                    for (int i = 0; i < data.length(); i++) {
                        result = data.getJSONObject(i);
                        list.add(new ListMovies(result.getString("id"), result.getString("title"), result.has("release_date") ? result.getString("release_date") : "-", result.getString("vote_average"), "https://image.tmdb.org/t/p/w500/" + result.getString("poster_path")));
                    }
                    ListMovieAdapter adapt = new ListMovieAdapter(MoviesActivity.this, list);
                    gridView.setAdapter(adapt);
                    queue.getCache().clear();
                    shimmerMovies.stopShimmer();
                    shimmerMovies.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    gridView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    Toast.makeText(MoviesActivity.this, " You clicked Cancel ", Toast.LENGTH_LONG).show();
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MoviesActivity.this, error.toString(), Toast.LENGTH_LONG).show();
//                Toast.makeText(RestApiActivity.this, "Tidak Ada Koneksi", Toast.LENGTH_LONG).show();
            }
        }
        );
        if(keyword.toString().length() != 0){
            queue.add(jsonObjectRequest);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        changeStatusBarColor("#D43232");
        searchBox = findViewById(R.id.search_movie_field);
        gridView = findViewById(R.id.list_movie);
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
                gridView.setVisibility(View.GONE);
                shimmerMovies.setVisibility(View.VISIBLE);
                shimmerMovies.startShimmer();
                if(editable.toString().length() == 0){
                    loadMovies();
                } else {
                    loadMoviesWithSearch(editable.toString());
                }

            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MoviesActivity.this);
                bottomSheetDialog.setContentView(R.layout.movie_details);

                loadDetailMovies(i, bottomSheetDialog);

                bottomSheetDialog.show();
            }
        });
    }
}

class ListMovies {
    String movie_id, title, date, rating;
    String drawableImage;

    public ListMovies(String movie_id, String title, String date, String rating, String drawableImage) {
        this.movie_id = movie_id;
        this.title = title;
        this.date = date;
        this.rating = rating;
        this.drawableImage = drawableImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDrawableImage() {
        return drawableImage;
    }

    public void setDrawableImage(String drawableImage) {
        this.drawableImage = drawableImage;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }
}