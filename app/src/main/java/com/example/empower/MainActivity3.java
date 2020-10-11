package com.example.empower;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.transition.Slide;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.empower.ui.about.AboutFragment;
import com.example.empower.ui.about.ContactPopup;
import com.example.empower.ui.map.MapFragment;
import com.example.empower.ui.news.NewsFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.listener.SettingsClickListener;
import com.lxj.xpopup.XPopup;
import com.stephentuso.welcome.WelcomeHelper;

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


    // welcome page
    WelcomeHelper welcomeScreen;

    // current location required
    private LatLng currentLatLng;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    private SpaceTabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        welcomeScreen = new WelcomeHelper(this, MyWelcomeActivity.class);
        welcomeScreen.show(savedInstanceState);


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
            assert stringCurrentLatLng != null;
            String[] arrayLatLng = stringCurrentLatLng.split(",");
            double latitude = Double.parseDouble(arrayLatLng[0]);
            double longitude = Double.parseDouble(arrayLatLng[1]);
            currentLatLng = new LatLng(latitude, longitude);
        }



        titleBarText = findViewById(R.id.text_title);
        aboutImage = findViewById(R.id.toolbarImageViewAbout);

        titleBarText.setText("Map");

        aboutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity3.this, "About clicked", Toast.LENGTH_SHORT).show();
                new XPopup.Builder(v.getContext())
                        .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                        .enableDrag(true)
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
//                        .isThreeDrag(true) //是否开启三阶拖拽，如果设置enableDrag(false)则无效
                        .asCustom(new ContactPopup(v.getContext())/*.enableDrag(false)*/)
                        .show();
            }
        });


        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        titleBarLayout = (RelativeLayout) layoutInflater.inflate(R.layout.layout_titlebar,null);


        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new NewsFragment());
        fragmentList.add(new MapFragment());
        fragmentList.add(new AboutFragment());


        ViewPager viewPager = (ViewPager) findViewById(R.id.fragment_viewPager);
        tabLayout = (SpaceTabLayout) findViewById(R.id.spaceTabLayout);


        tabLayout.initialize(viewPager, getSupportFragmentManager(),
                fragmentList, savedInstanceState);


        int a = tabLayout.getCurrentPosition();

        tabLayout.setTabOneOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleBarText.setText("News");
            }
        });

        tabLayout.setTabTwoOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleBarText.setText("News");
            }
        });

        tabLayout.setTabThreeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleBarText.setText("News");
            }
        });



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