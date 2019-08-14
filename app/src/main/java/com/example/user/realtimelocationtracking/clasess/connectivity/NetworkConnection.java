package com.example.user.realtimelocationtracking.clasess.connectivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;


import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.user.realtimelocationtracking.model.ConnectionModel;

public class NetworkConnection extends MutableLiveData<ConnectionModel> {

    private static final String TAG = "NetworkConnection";
    private Context context;
    ConnectivityManager cm ;
    NetworkCallback networkCallback;

    public NetworkConnection(Context context) {
        this.context = context;
        cm= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkCallback = new NetworkCallback(this);
    }

    @Override
    protected void onActive() {
        super.onActive();

        updateConnection();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(networkCallback);

        } else  {
            NetworkRequest networkRequest = new NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .build();
            cm.registerNetworkCallback(networkRequest, networkCallback);
        }
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        //min sdk 21 ,no need to check sdk
       cm.unregisterNetworkCallback(networkCallback);

    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super ConnectionModel> observer) {
        super.observe(owner, observer);

    }

    public LiveData<ConnectionModel> getNetworkStatus(){
        return this;
    }

    private void updateConnection() {
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                postValue(new ConnectionModel(true));
            }else{
                postValue(new ConnectionModel(false));
            }
        }

    }


}
