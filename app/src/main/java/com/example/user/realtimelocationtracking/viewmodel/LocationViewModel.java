package com.example.user.realtimelocationtracking.viewmodel;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.user.realtimelocationtracking.clasess.location.LocationUpdates;
import com.example.user.realtimelocationtracking.model.ConnectionModel;
import com.example.user.realtimelocationtracking.clasess.connectivity.NetworkConnection;
import com.example.user.realtimelocationtracking.model.LocationModel;
import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LocationViewModel extends AndroidViewModel {

    NetworkConnection networkConnection;
    LiveData<ConnectionModel> connetionLiveData;
    LiveData<LocationModel> locationData;
    LocationUpdates locationUpdates;
    LatLng start=null,lastLocation=null ;
    public LocationViewModel(@NonNull Application application) {
        super(application);

        networkConnection = new NetworkConnection(application);
        connetionLiveData = networkConnection.getNetworkStatus();
        locationUpdates = new LocationUpdates(application);


    }

    public LiveData<ConnectionModel> getModelLiveData() {
        return connetionLiveData;
    }

    public LiveData<LocationModel> getLocation(){
        return  locationUpdates.getLocation();
    }
    public void stopLocationUpdate(){
        locationUpdates.stopLocationUpdate();
    }
    public  void startLocationUpdation(){
        locationUpdates.startLocationUpdate();
    }
    public void  setStartPoint(LatLng start ){
        this.start = start;
    }

    public LatLng getStart(){
        return  start;
    }
    public boolean isStartPintIsNull(){
        boolean result = false;
        if(start== null)
            result = true;
        return result;
    }

    public void setLastLocation(LatLng lastLocation){
        this.lastLocation = lastLocation;
    }
    public LatLng getLasteLocation(){
        return this.lastLocation;
    }


    //code copied from google
    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        //Log./i("Radius Value", "" + valueResult + "   KM  " + kmInDec
              //  + " Meter   " + meterInDec);

        return Radius * c;
    }



}
