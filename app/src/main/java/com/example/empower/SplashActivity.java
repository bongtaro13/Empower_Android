package com.example.empower;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.provider.Settings;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        logoImage = findViewById(R.id.splash_logo_image);


        Dexter.withContext(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(final PermissionGrantedResponse permissionGrantedResponse) {


                        //Use the timer to delay the interface for 3 seconds before jumping. The timer has a thread that continuously executes tasks
                        Timer timer = new Timer();
                        TimerTask timerTask = new TimerTask() {
                            @Override
                            public void run() {
                                //Send intent to realize page jump, the first parameter is the context of the current page
                                // and the second parameter is the homepage to jump to
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                //Close the current welcome page after the jump
                                SplashActivity.this.finish();
                            }
                        };
                        //Schedule the execution of timerTask, the second parameter is passed in the delay time (ms)
                        timer.schedule(timerTask, 3000);
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
                                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                    //Close the current welcome page after the jump
                                    SplashActivity.this.finish();
                                }
                            };
                            //Schedule the execution of timerTask, the second parameter is passed in the delay time (ms)
                            timer.schedule(timerTask, 3000);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();

    }



}