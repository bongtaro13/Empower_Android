package com.example.empower;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Looper;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Timer;
import java.util.TimerTask;



/**
 * Class name: SplashActivity.java
 * Function: This activity is aim to display a welcome page at the staring the application
 */
public class SplashActivity extends AppCompatActivity {

    private TextView tv_version;        // text view for slogan display on the welcome page
    private ImageView logoImage;        // image view for the logo displayed on the welcome page
    private ImageView gifImage;

    private LatLng  latLngResult;



    // initialize the welcome page when created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_splash);
        // set current screen vertically
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // run the initialization section

        // add two visual components to this activity
        tv_version = findViewById(R.id.tv_version);
        gifImage = findViewById(R.id.imageView_gif);
        Glide.with(this).asGif().load(R.raw.logo_o2).into(gifImage);





        Dexter.withContext(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(final PermissionGrantedResponse permissionGrantedResponse) {

                        getCurrentLocation();


                        //Use the timer to delay the interface for 3 seconds before jumping. The timer has a thread that continuously executes tasks
                        Timer timer = new Timer();
                        TimerTask timerTask = new TimerTask() {
                            @Override
                            public void run() {
                                //Send intent to realize page jump, the first parameter is the context of the current page
                                // and the second parameter is the homepage to jump to

                                Intent intentMain = new Intent(getBaseContext(), MainActivity3.class);
                                String stringLatLngResult = latLngResult.latitude + "," + latLngResult.longitude;
                                intentMain.putExtra("stringLatLngResult", stringLatLngResult);
                                startActivity(intentMain);
                                //Close the current welcome page after the jump
                                SplashActivity.this.finish();
                            }
                        };
                        //Schedule the execution of timerTask, the second parameter is passed in the delay time (ms)
                        timer.schedule(timerTask, 3330);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        if (permissionDeniedResponse.isPermanentlyDenied()){
                            AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                            builder.setTitle("Permission Denied")
                                    .setMessage("Permission to access device location is permanently denied, you need to go to setting to allow this permission")
                                    .setNegativeButton("Cancel", null)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            intent.setData(Uri.fromParts("package", getPackageName(), null));
                                        }
                                    })
                                    .show();
                        }else {
                            Toast.makeText(SplashActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                            //Use the timer to delay the interface for 3 seconds before jumping. The timer has a thread that continuously executes tasks
                            Timer timer = new Timer();
                            TimerTask timerTask = new TimerTask() {
                                @Override
                                public void run() {
                                    //Send intent to realize page jump, the first parameter is the context of the current page
                                    // and the second parameter is the homepage to jump to
                                    startActivity(new Intent(SplashActivity.this, MainActivity3.class));
                                    //Close the current welcome page after the jump
                                    SplashActivity.this.finish();
                                }
                            };
                            //Schedule the execution of timerTask, the second parameter is passed in the delay time (ms)
                            timer.schedule(timerTask, 3330);

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();

    }



    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        LocationServices.getFusedLocationProviderClient(SplashActivity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(SplashActivity.this)
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                            double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                            latLngResult = new LatLng(latitude, longitude);

                        }
                    }
                }, Looper.getMainLooper());



    }





}