package com.example.user.realtimelocationtracking.clasess.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.user.realtimelocationtracking.model.LocationModel;
import com.example.user.realtimelocationtracking.view.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.Executor;


public class LocationUpdates extends LiveData<LocationModel> {

    private static final String TAG = "LocationUpdates";
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    Context context;
    LatLng latLng =null;


    public LocationUpdates(Context context) {
        Log.d(TAG, "Location updates constructor building");
        //Toast.makeText(context,"Building Location Request",Toast.LENGTH_SHORT).show();
        this.context = context;
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        buildLocationRequest();
        bildLocationCallback();

    }


    @Override
    protected void onActive() {
        Log.d(TAG, "Location Live data OnActive");
        super.onActive();
        startLocationUpdate();
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "Location Live data OnInactive");
        super.onInactive();
        stopLocationUpdate();
    }

    private void buildLocationRequest() {

        Log.d(TAG, "Building Location Request");
        //Toast.makeText(context,"Building Location Request",Toast.LENGTH_SHORT).show();
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void bildLocationCallback() {
        Log.d(TAG, "Building Location Callback");
        Toast.makeText(context,"Building Location Callback",Toast.LENGTH_SHORT).show();
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {

                Location location = locationResult.getLastLocation();
                Log.d(TAG, location.getLatitude() + "  " + location.getLongitude() + "");
                //Toast.makeText(context,location.getLatitude()+"",Toast.LENGTH_LONG).show();
                postValue(new LocationModel(location.getLatitude(), location.getLongitude()));
                if(latLng == null){
                    latLng = new LatLng(location.getLatitude(),location.getLongitude());
                }

            }
        };

    }


    @SuppressLint("MissingPermission")
    public void startLocationUpdate() {
        Log.d(TAG, "Location updates started");
        //Toast.makeText(context,"Location updates started",Toast.LENGTH_SHORT).show();
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }



    public void stopLocationUpdate(){

        Log.d(TAG,"Location updates stoped");
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    public LiveData<LocationModel> getLocation(){
        return this;
    }



}
