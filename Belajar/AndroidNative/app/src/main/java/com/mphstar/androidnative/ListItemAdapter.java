package com.mphstar.androidnative;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class ListItemAdapter  extends ArrayAdapter<ListItems> {
    public ListItemAdapter(@NonNull Context context, @NonNull ArrayList<ListItems> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
        }

        ListItems list = getItem(position);
        TextView title = listitemView.findViewById(R.id.title_text);
        TextView desc = listitemView.findViewById(R.id.desc);
        RoundedImageView image = listitemView.findViewById(R.id.ic_image);

        title.setText(list.getTitle());
        desc.setText(list.getDesc());
        image.setImageResource(list.getImages_path());

        return listitemView;
    }
}
