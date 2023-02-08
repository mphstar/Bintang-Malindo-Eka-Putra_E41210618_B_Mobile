package com.mphstar.androidnative.belajar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mphstar.androidnative.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListMovieAdapter extends ArrayAdapter<ListMovies> {
    public ListMovieAdapter(@NonNull Context context, @NonNull ArrayList<ListMovies> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemmMovie = convertView;
        if (listItemmMovie == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listItemmMovie = LayoutInflater.from(getContext()).inflate(R.layout.items_movies, parent, false);
        }

        ListMovies list = getItem(position);
        TextView title = listItemmMovie.findViewById(R.id.title_movie);
        TextView date = listItemmMovie.findViewById(R.id.date_release);
        TextView rating = listItemmMovie.findViewById(R.id.rating_movie);
        RoundedImageView image = listItemmMovie.findViewById(R.id.image_movie);

        title.setText(list.getTitle());
        date.setText(list.getDate());
        rating.setText(list.getRating());
//        Picasso.get().load(list.getDrawableImage()).into(image);

        Glide.with(parent.getContext())
                .load(list.getDrawableImage())
                .placeholder(R.drawable.placeholder_image)
                .apply(new RequestOptions()
                        .centerCrop())
                .into(image);


        return listItemmMovie;
    }
}
