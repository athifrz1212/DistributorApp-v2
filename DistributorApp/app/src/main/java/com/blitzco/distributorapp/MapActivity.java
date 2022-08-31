package com.blitzco.distributorapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.blitzco.distributorapp.models.Shop;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";
    static String mapIndex;
    Address address;
    String mapAddress;
    GoogleMap map;
    Button b1;
    EditText edIndex;
    public static boolean lock;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private DatabaseReference shopRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);


        shopRef = FirebaseDatabase.getInstance().getReference("Shop");
        edIndex = findViewById(R.id.serch_shop);
        b1 = findViewById(R.id.btnMap);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapIndex = getIntent().getStringExtra("ShopName");

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        if(lock) {
            geoLocate();
            lock=false;
        }else{
            geoLocateALL();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
    }

    private void geoLocate() {
        Address address = null;

        Log.d(TAG, "getDeviceLocation: geoLocating");

        shopRef.orderByChild("shop").equalTo(mapIndex).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap: snapshot.getChildren()) {
                    mapAddress = snap.getValue(Shop.class).getAddress();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        try{
            Geocoder geocoder = new Geocoder(MapActivity.this);
            List<Address> list = new ArrayList<>();
            try {
                list = geocoder.getFromLocationName(mapAddress, 1);
            } catch (IOException e) {
                Log.e(TAG, "geoLocate: IOException" + e.getMessage());
            }
            if (list.size() > 0) {
                address = list.get(0);

                Log.d(TAG, "geoLocate: found a location: " + address.toString());
            }
            LatLng Shop = new LatLng(address.getLatitude(), address.getLongitude());
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()),
                    9f);
            map.addMarker(new MarkerOptions().position(Shop).title(address.getAddressLine(0)));
            getDeviceLocation();
        }catch (Exception e){
            Toast.makeText(this, "Enter a shop name available in the list", Toast.LENGTH_LONG).show();
        }

    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {


            final Task location = mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "onComplete: found location!");
                        Location currentLocation = (Location) task.getResult();

                    } else {
                        Log.d(TAG, "onComplete: current location is null");
                        Toast.makeText(MapActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }

    }

    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }
    private void geoLocateALL() {

        Log.d(TAG, "getDeviceLocation: geoLocating");

        shopRef.orderByChild("shop").equalTo(mapIndex).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap: snapshot.getChildren()) {
                    mapAddress = snap.getValue(Shop.class).getAddress();

                    Geocoder geocoder = new Geocoder(MapActivity.this);
                    List<Address> list = new ArrayList<>();
                    try {
                        list = geocoder.getFromLocationName(mapAddress, 1);
                    } catch (IOException e) {
                        Log.e(TAG, "geoLocate: IOException" + e.getMessage());
                    }
                    if (list.size() > 0) {
                        address = list.get(0);

                        Log.d(TAG, "geoLocate: found a location: " + address.toString());
                    }
                    LatLng Shop = new LatLng(address.getLatitude(), address.getLongitude());

                    map.addMarker(new MarkerOptions().position(Shop).title(address.getAddressLine(0)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        getDeviceLocation();
        moveCamera(new LatLng(7.900381, 80.684307),
                7.5f);
    }
}