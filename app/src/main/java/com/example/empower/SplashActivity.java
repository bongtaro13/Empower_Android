package com.example.empower;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Class name: SplashActivity.java
 * Function: This activity is aim to display a welcome page at the staring the application
 */
public class SplashActivity extends AppCompatActivity {

    private TextView tv_version;        // text view for slogan display on the welcome page
    private ImageView logoImage;        // image view for the logo displayed on the welcome page


    // initialize the welcome page when created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // set current screen vertically
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // run the initialization section
        init();
    }

    private void init() {

        // add two visual components to this activity
        tv_version = findViewById(R.id.tv_version);
        logoImage = findViewById(R.id.splash_logo_image);


        //Use the timer to delay the interface for 3 seconds before jumping. The timer has a thread that continuously executes tasks
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //Send intent to realize page jump, the first parameter is the context of the current page
                // and the second parameter is the homepage to jump to
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                //Close the current welcome page after the jump
                SplashActivity.this.finish();
            }
        };
        //Schedule the execution of timerTask, the second parameter is passed in the delay time (ms)
        timer.schedule(timerTask, 3000);
    }


}