package com.example.minggu12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonManualActivity extends AppCompatActivity implements RecyclerViewListener{

    RecyclerView listdata;
    ArrayList<ModelData> mdata;
    String raw_data = "[\n" +
            "  {\n" +
            "    \"nama\": \"Monkey D Luffy\",\n" +
            "    \"hobi\": \"Keliling Dunia\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"nama\": \"Zoro\",\n" +
            "    \"hobi\": \"Main Pedang\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"nama\": \"Sanji\",\n" +
            "    \"hobi\": \"Memasak\"\n" +
            "  }\n" +
            "]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_manual);
        listdata = findViewById(R.id.datadiri);

        try {
            JSONArray datas = new JSONArray(String.valueOf(raw_data));
            System.out.println(datas);
            JSONObject datamapping;
            mdata = new ArrayList<>();
            for(int i=0;i<datas.length(); i++){
                datamapping = datas.getJSONObject(i);
                mdata.add(new ModelData(datamapping.getString("nama"), datamapping.getString("hobi")));
            }

            AdapterData adap = new AdapterData(mdata, this);
            listdata.setLayoutManager(new LinearLayoutManager(JsonManualActivity.this));
            listdata.setAdapter(adap);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onClick(View view, int position) {
        Toast.makeText(JsonManualActivity.this, mdata.get(position).getNama(), Toast.LENGTH_SHORT).show();
    }
}

class AdapterData extends RecyclerView.Adapter<AdapterData.AdapterDataViewHolder>{
    private ArrayList<ModelData> dataList;
    RecyclerViewListener listener;
    private Context context;

    public AdapterData(ArrayList<ModelData> dataList, RecyclerViewListener listener) {
        this.dataList = dataList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        View view = layout.inflate(R.layout.item_konten, parent, false);
        return new AdapterData.AdapterDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataViewHolder holder, int position) {
        holder.name.setText(dataList.get(position).getNama());
        holder.hobi.setText(dataList.get(position).getHobi());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    class AdapterDataViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name, hobi;
        public AdapterDataViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_nama);
            hobi = itemView.findViewById(R.id.txt_hobi);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }
}

class ModelData {
    private String nama, hobi;

    public ModelData(String nama, String hobi) {
        this.nama = nama;
        this.hobi = hobi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHobi() {
        return hobi;
    }

    public void setHobi(String hobi) {
        this.hobi = hobi;
    }
}