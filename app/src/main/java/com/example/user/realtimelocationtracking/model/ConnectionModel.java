package com.example.user.realtimelocationtracking.model;

public class ConnectionModel {


    private boolean isConnected;

    public ConnectionModel( boolean isConnected) {

        this.isConnected = isConnected;
    }


    public boolean getIsConnected() {
        return isConnected;
    }
}
