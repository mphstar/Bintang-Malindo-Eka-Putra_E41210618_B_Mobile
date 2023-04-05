package com.example.minggu8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AllSensor extends AppCompatActivity {

    List<Sensor> myListSensor;
    ListView listviewsensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_sensor);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("List All Sensor");
        bar.setDisplayHomeAsUpEnabled(true);
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        myListSensor = sensorManager.getSensorList(Sensor.TYPE_ALL);
        listviewsensor = findViewById(R.id.sensors);
        listviewsensor.setAdapter(new mySensorAdapter(this, R.layout.item_sensor, myListSensor));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class mySensorAdapter extends ArrayAdapter<Sensor> {

        public mySensorAdapter(@NonNull Context context, int resource, List<Sensor> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItemView = convertView;
            if(listItemView == null){
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_sensor, parent,false);
            }

            Sensor list = getItem(position);

            TextView txt_sensor = listItemView.findViewById(R.id.txt_item_sensor);
            txt_sensor.setText((position + 1) + ". Name: " + list.getName() + "\nInt Type: " + list.getType() + "\nPower: " + list.getPower() + "mAh\nMax Range: " + list.getMaximumRange());

            return listItemView;
        }
    }



}

