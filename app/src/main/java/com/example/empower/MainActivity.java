package com.example.empower;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//get current location

import android.location.Location;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

/**
 * Class name: MainActivity.java
 * Function: This activity is aim to hold three main fragment display to the users
 *              also there exits a SharedPreferences settings to check if user open the application in the first time
 *              also with location manager to get user current location and used it in the map fragment
 */

public class MainActivity extends AppCompatActivity {


    // application first run fields
    SharedPreferences settings;

    // positioning are all related by the class LocationManager
    private LocationManager locationManager;
    private String provider;
    private Location currentLocation;

    // main activity test added
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // check if user first use app
        final String PREFS_NAME = "MyPrefsFile";

        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);





        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_news, R.id.navigation_map, R.id.navigation_about)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);




        //Get location services
        locationManager = (LocationManager) getBaseContext().getSystemService(Context.LOCATION_SERVICE);
        //Gets the currently available location controller
        List<String> list = locationManager.getProviders(true);


        if (list.contains(LocationManager.GPS_PROVIDER)) {
            // check if location from GPS location controller
            provider = LocationManager.GPS_PROVIDER;
        } else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
            // check if location from network location controller
            provider = LocationManager.NETWORK_PROVIDER;

        } else {
            Toast.makeText(this, "Please check if the GPS is open Or you are in good GPS signal",
                    Toast.LENGTH_LONG).show();
            return;
        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        // get current location from the location manager with provider chosen
        currentLocation = locationManager.getLastKnownLocation(provider);
        if (currentLocation != null) {
            //To get the current location, I'm just using latitude and longitude
            String string = "latitude：" + currentLocation.getLatitude() + ",longitude："
                    + currentLocation.getLongitude();
        }


        // Bind to locate an event and listen for changes in location
        // The first parameter is the controller type and
        // the second parameter is the interval (in milliseconds) to listen for changes in position.
        // The third parameter is the interval of position change (unit: m) and the fourth parameter is the position listener
        locationManager.requestLocationUpdates(provider, 2000, 2, locationListener);

    }

    // monitor the changed of the location
    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderDisabled(String arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onLocationChanged(Location arg0) {
            // TODO Auto-generated method stub
            // update current latitude and longitude
        }
    };

    // Unplug the listener when it is closed
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }


    // get current location field variable from the result of location manager
    public Location getCurrentLocation(){
        // if there is no location info, make the Monash Clayton the default location
        if (currentLocation  == null){
            currentLocation = new Location("tempLocation");
            currentLocation.setLongitude(145.13170297689055);
            currentLocation.setLatitude(-37.91341883508321);
        }
        return currentLocation;
    }



    




}




