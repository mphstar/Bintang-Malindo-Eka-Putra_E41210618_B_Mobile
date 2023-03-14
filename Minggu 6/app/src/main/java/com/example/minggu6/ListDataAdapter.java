package com.example.minggu6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;

public class ListDataAdapter extends ArrayAdapter<DataDiri> {
    public ListDataAdapter(@NonNull Context context, @NonNull ArrayList<DataDiri> objects) {
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

        DataDiri list = getItem(position);
        TextView nama = listitemView.findViewById(R.id.txt_nama);
        TextView alamat = listitemView.findViewById(R.id.txt_alamat);
        TextView hobi = listitemView.findViewById(R.id.txt_hobi);

        nama.setText(list.getNama());
        alamat.setText(list.getAlamat());
        hobi.setText(list.getHobi());

        return listitemView;
    }
}
