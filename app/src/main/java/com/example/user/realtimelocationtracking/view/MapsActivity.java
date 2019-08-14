package com.example.user.realtimelocationtracking.view;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.realtimelocationtracking.R;
import com.example.user.realtimelocationtracking.model.ConnectionModel;
import com.example.user.realtimelocationtracking.model.LocationModel;
import com.example.user.realtimelocationtracking.viewmodel.LocationViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationViewModel locationViewModel = null;
    Button start,stop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);

        locationViewModel = ViewModelProviders.of(MapsActivity.this).get(LocationViewModel.class);


        stop.setEnabled(false);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mMap.clear();
                locationViewModel.startLocationUpdation();
                start.setEnabled(false);
                stop.setEnabled(true);

                locationViewModel.getLocation().observe(MapsActivity.this, new Observer<LocationModel>() {
                    @Override
                    public void onChanged(LocationModel locationModel) {
                        //12.9588485, 77.6059281
                        LatLng latLng = new LatLng(locationModel.getLatitude(),locationModel.getLongitude());
                        if(locationViewModel.isStartPintIsNull()){
                            locationViewModel.setStartPoint(latLng);
                            setMarker();
                        }
                        updateLastLocation(locationModel);

                        Toast.makeText(MapsActivity.this,locationModel.getLatitude()+" "+locationModel.getLongitude(),Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
        locationViewModel.getModelLiveData().observe(this, new Observer<ConnectionModel>() {
            @Override
            public void onChanged(ConnectionModel connectionModel) {
                 if(!connectionModel.getIsConnected()){
                     Toast.makeText(MapsActivity.this, "Network not available", Toast.LENGTH_SHORT).show();
                     start.setEnabled(false);
                     stop.setEnabled(false);
                 }else {
                     Toast.makeText(MapsActivity.this, "Network  available", Toast.LENGTH_SHORT).show();
                     start.setEnabled(true);

                 }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.setEnabled(true);
                stop.setEnabled(false);
                LatLng start = locationViewModel.getStart();
                LatLng stop = locationViewModel.getLasteLocation();
                LatLng sydney = new LatLng(stop.latitude,stop.longitude);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.getMaxZoomLevel();
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                Double distance = locationViewModel .CalculationByDistance(start,stop);

                Toast.makeText(MapsActivity.this,"Your coverd Distance is "+distance , Toast.LENGTH_SHORT).show();

                locationViewModel.stopLocationUpdate();

                locationViewModel.setStartPoint(null);

            }
        });
    }

    private void updateLastLocation(LocationModel locationModel) {


        LatLng lastLocation = new LatLng(locationModel.getLatitude(),locationModel.getLongitude());
        locationViewModel.setLastLocation(lastLocation);

    }

    private void setMarker() {
        LatLng latLng= locationViewModel.getStart();
        LatLng sydney = new LatLng(latLng.latitude,latLng.longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.getMaxZoomLevel();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

//    private void startPolyline(GoogleMap map, LatLng location){
//        if(map == null){
//            //Log.d(TAG, "Map object is not null");
//            return;
//        }
//        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
//        options.add(location);
//        Polyline polyline = map.addPolyline(options);
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(location)
//                .zoom(16)
//                .build();
//        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//    }
//    private void drawRouteOnMap(GoogleMap map, List<LatLng> positions){
//        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
//        options.addAll(positions);
//        Polyline polyline = map.addPolyline(options);
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(new LatLng(positions.get(0).latitude, positions.get(0).longitude))
//                .zoom(17)
//                .bearing(90)
//                .tilt(40)
//                .build();
//        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//    }
}
