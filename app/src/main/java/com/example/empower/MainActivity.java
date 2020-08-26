package com.example.empower;

import android.os.Bundle;
import android.util.Log;

import com.example.empower.entity.SportsVenue;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList sportsVenueList = new ArrayList();

    // main activity test added
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        createSportsVenueList();
    }



    public void createSportsVenueList() {
        InputStream inputStream = getResources().openRawResource(R.raw.disability_wheelchair_sports_vic);
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream, Charset.forName("UTF-8"))
        );

        String line = "";
        try {
            while ((line = bufferedReader.readLine()) != null){

                String[] sportsVenueRawData = line.split(",");
                int length = sportsVenueRawData.length;

                if (length != 9){
                    continue;
                }

                SportsVenue tempSportsVenue = new SportsVenue();
                tempSportsVenue.setVenueID(sportsVenueRawData[0]);
                tempSportsVenue.setName(sportsVenueRawData[1]);
                tempSportsVenue.setAddress(sportsVenueRawData[2]);
                tempSportsVenue.setSuburb(sportsVenueRawData[3]);
                tempSportsVenue.setPostcode(sportsVenueRawData[4]);
                tempSportsVenue.setState(sportsVenueRawData[5]);
                tempSportsVenue.setBusinessCategory(sportsVenueRawData[6]);
                tempSportsVenue.setLga(sportsVenueRawData[7]);
                tempSportsVenue.setRegion(sportsVenueRawData[8]);
                sportsVenueList.add(tempSportsVenue);

                Log.d("MainActivity", "current sports venus object: " + tempSportsVenue.toString());
            }
        }catch (IOException e){
            Log.e("MainActivity", "Error reading data from the line " + line, e);
            e.printStackTrace();
        }

    }

}