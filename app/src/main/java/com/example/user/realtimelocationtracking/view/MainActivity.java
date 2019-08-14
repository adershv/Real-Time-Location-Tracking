package com.example.user.realtimelocationtracking.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.realtimelocationtracking.R;
import com.example.user.realtimelocationtracking.model.LocationModel;
import com.example.user.realtimelocationtracking.viewmodel.LocationViewModel;




public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 101;

    private static final String TAG = "MainActivity";


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
            Toast.makeText(this, "Already granded", Toast.LENGTH_SHORT).show();
            //locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
                Intent intent =new Intent(MainActivity.this,MapsActivity.class);
                startActivity((intent));
                finish();

        }else {
            requestPermissions();
        }


    }



    private void requestPermissions() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)){

            new AlertDialog.Builder(this).setTitle("Permisssion Needed")
                    .setMessage("Permission Needed To continue")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);

                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();
        }else {
            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);

        }
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            switch(requestCode){
                case REQUEST_CODE :
                    if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(MainActivity.this, "Permision Granded", Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(MainActivity.this,MapsActivity.class);
                        startActivity((intent));
                        finish();
                    }else {
                        Toast.makeText(MainActivity.this, "Permision Denied", Toast.LENGTH_SHORT).show();
                    }


        }
    }


}
