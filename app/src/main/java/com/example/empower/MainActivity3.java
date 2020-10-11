package com.example.empower;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.empower.ui.about.AboutFragment;
import com.example.empower.ui.map.MapFragment;
import com.example.empower.ui.news.NewsFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import eu.long1.spacetablayout.SpaceTabLayout;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity3 extends AppCompatActivity {


    private View root;
    private TextView titleBarText;
    private ImageView aboutImage;
    private RelativeLayout titleBarLayout;

    private LatLng currentLatLng;


    private SpaceTabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());


        // get location from welcome activity
        String stringCurrentLatLng = getIntent().getStringExtra("stringLatLngResult");
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            currentLatLng = new LatLng(-37.876859, 145.044946);
        } else {
//            assert stringCurrentLatLng != null;
//            String[] arrayLatLng = stringCurrentLatLng.split(",");
//            double latitude = Double.parseDouble(arrayLatLng[0]);
//            double longitude = Double.parseDouble(arrayLatLng[1]);
//            currentLatLng = new LatLng(latitude, longitude);

            currentLatLng = new LatLng(-37.876859, 145.044946);
        }


        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new NewsFragment());
        fragmentList.add(new MapFragment());
        fragmentList.add(new AboutFragment());


        ViewPager viewPager = (ViewPager) findViewById(R.id.fragment_viewPager);
        tabLayout = (SpaceTabLayout) findViewById(R.id.spaceTabLayout);
        tabLayout.initialize(viewPager, getSupportFragmentManager(),
                fragmentList, savedInstanceState);



    }

    public LatLng getCurrentLatLngFromMain() {
        return currentLatLng;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        tabLayout.saveState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

}