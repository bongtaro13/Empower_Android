package com.example.empower;


import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.empower.api.TaskLoadedCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;

//get current location

/**
 * Class name: MainActivity.java
 * Function: This activity is aim to hold three main fragment display to the users
 * also there exits a SharedPreferences settings to check if user open the application in the first time
 * also with location manager to get user current location and used it in the map fragment
 */

public class MainActivity extends AppCompatActivity implements TaskLoadedCallback {


    private LatLng currentLatLng;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;


    // application first run fields
    SharedPreferences settings;

    // main activity test added
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            currentLatLng = new LatLng(-37.876859, 145.044946);
        } else {
            String stringCurrentLatLng = getIntent().getStringExtra("stringLatLngResult");
            assert stringCurrentLatLng != null;
            String [] arrayLatLng = stringCurrentLatLng.split(",");
            double latitude = Double.parseDouble(arrayLatLng[0]);
            double longitude = Double.parseDouble(arrayLatLng[1]);
            currentLatLng = new LatLng(latitude, longitude);
        }

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


    }


    public LatLng getCurrentLatLngFromMain() {
        return currentLatLng;
    }


    @Override
    public void onTaskDone(Object... values) {

    }
}




