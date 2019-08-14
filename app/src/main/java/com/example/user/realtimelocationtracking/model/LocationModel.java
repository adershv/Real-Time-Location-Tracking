package com.example.user.realtimelocationtracking.model;

public class LocationModel {

    private Double latitude;
    private Double longitude;
    private Double startLatitude;
    private Double startLongitude;

    public LocationModel(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }



}
