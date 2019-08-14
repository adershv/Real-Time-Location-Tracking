package com.example.user.realtimelocationtracking.clasess.connectivity;

import android.net.ConnectivityManager;
import android.net.Network;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.user.realtimelocationtracking.model.ConnectionModel;

public class NetworkCallback extends ConnectivityManager.NetworkCallback {

    private NetworkConnection networkConnection;

    private static final String TAG = "NetworkCallback";
    public NetworkCallback(NetworkConnection networkConnection) {
        this.networkConnection = networkConnection;
    }

    @Override
    public void onAvailable(@NonNull Network network) {
        super.onAvailable(network);
        Log.d(TAG,"onAvailable");
        networkConnection.postValue(new ConnectionModel(true));
    }

    @Override
    public void onLost(@NonNull Network network) {
        super.onLost(network);
        Log.d(TAG,"onLost");
        networkConnection.postValue(new ConnectionModel(false));
    }
}
