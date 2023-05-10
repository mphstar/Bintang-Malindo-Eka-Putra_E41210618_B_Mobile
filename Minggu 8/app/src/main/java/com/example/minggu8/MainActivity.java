package com.example.minggu8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.os.LocaleListCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    private GoogleMap mMap;
    int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String latitude, longitude;
    boolean locationPermissionGranted = false;
    private String mapTypes[] = {"Normal", "Hybrid", "Satellite", "Terrain"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Maps");
        bar.setDisplayHomeAsUpEnabled(true);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        getKnowLastLocation();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(MainActivity.this, "Got Coordinates: " + location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT).show();
        LatLng p = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(p));
        mMap.addMarker(new MarkerOptions().position(p).title("You are here"));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        locationManager.removeUpdates(this);
    }

    private void getKnowLastLocation(){
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            if(locationManager == null){
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    5000, 1000, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.current_place_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.option_get_place) {
            getKnowLastLocation();
        } else if (item.getItemId() == android.R.id.home){
            this.finish();
        } else if (item.getItemId() == R.id.option_map_type){
            AlertDialog.Builder build = new AlertDialog.Builder(this);
            build.setTitle("Map Types");
            build.setItems(mapTypes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (mapTypes[i]){
                        case "Normal":
                            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            break;
                        case "Hybrid":
                            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                            break;
                        case "Satellite":
                            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                            break;
                        case "Terrain":
                            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                            break;
                    }
                }
            });

            AlertDialog dialog = build.create();
            dialog.show();
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode == REQUEST_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
                getKnowLastLocation();
            } else {
                if(!locationPermissionGranted){
                    Toast.makeText(this, "Location tidak diijinkan", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
//        getLocation();
    }
}