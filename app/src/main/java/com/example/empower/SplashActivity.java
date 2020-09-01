package com.example.empower;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity{

    private TextView tv_version;
    private ImageView logoImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //set current screen vertically
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        init();
    }

    private void init() {
         tv_version = findViewById(R.id.tv_version);
         logoImage = findViewById(R.id.splash_logo_image);


//        try {
//            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
//            tv_version.setText("version:"+packageInfo.versionName);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//            tv_version.setText("version");
//        }
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
        timer.schedule(timerTask,3000);
    }



}