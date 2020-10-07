package com.example.empower;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.empower.ui.about.AboutBottomPopup;
import com.example.empower.ui.about.ContactPopup;
import com.google.android.gms.maps.model.LatLng;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;
import com.lxj.xpopup.XPopup;
import com.stephentuso.welcome.WelcomeHelper;

public class MainActivity2 extends AppCompatActivity {

    private View root;
    private TextView titleBarText;
    private ImageView aboutImage;
    private RelativeLayout titleBarLayout;


    // welcome page
    WelcomeHelper welcomeScreen;

    // current location required
    private LatLng currentLatLng;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    NavController navigation;


    @SuppressLint({"ResourceAsColor", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        welcomeScreen = new WelcomeHelper(this, MyWelcomeActivity.class);
        welcomeScreen.show(savedInstanceState);

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


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        titleBarText = findViewById(R.id.text_title);
        aboutImage = findViewById(R.id.toolbarImageViewAbout);


        titleBarLayout = (RelativeLayout) findViewById(R.layout.layout_titlebar);


        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        titleBarLayout = (RelativeLayout) layoutInflater.inflate(R.layout.layout_titlebar,null);


        aboutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity2.this, "About clicked", Toast.LENGTH_SHORT).show();
                new XPopup.Builder(v.getContext())
                        .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                        .enableDrag(true)
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
//                        .isThreeDrag(true) //是否开启三阶拖拽，如果设置enableDrag(false)则无效
                        .asCustom(new ContactPopup(v.getContext())/*.enableDrag(false)*/)
                        .show();
            }
        });

        setTitle("Map");

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);

        BottomNavigationItem bottomNavigationNews = new BottomNavigationItem
                ("News", ContextCompat.getColor(this, R.color.blue_background), R.drawable.ic_news_black_24dp);
        BottomNavigationItem bottomNavigationMap = new BottomNavigationItem
                ("Map", ContextCompat.getColor(this, R.color.blue_background), R.drawable.ic_map_black_24dp);
        BottomNavigationItem bottomNavigationContact = new BottomNavigationItem
                ("Liked Venue", ContextCompat.getColor(this, R.color.blue_background), R.drawable.ic_favorite_24dp);



        bottomNavigationView.addTab(bottomNavigationNews);
        bottomNavigationView.addTab(bottomNavigationMap);
        bottomNavigationView.addTab(bottomNavigationContact);

        bottomNavigationView.isWithText(false);
        bottomNavigationView.selectTab(1);

        navigation = Navigation.findNavController(this, R.id.nav_host_fragment_main2);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.



        bottomNavigationView.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
            @Override
            public void onNavigationItemClick(int index) {
                Toast.makeText(MainActivity2.this, "Item " + index + " clicked", Toast.LENGTH_SHORT).show();
                switch (index){
                    case 0:
                        navigation.navigate(R.id.navigation_news);
                        setTitle("News");
                        titleBarLayout.setBackgroundColor(R.color.blue_background);
                        break;
                    case 1:
                        navigation.navigate(R.id.navigation_map);
                        setTitle("Map");
                        titleBarLayout.setBackgroundColor(R.color.orange_background);
                        break;
                    case 2:
                        navigation.navigate(R.id.navigation_about);
                        setTitle("Liked Venue");
                        titleBarLayout.setBackgroundColor(R.color.teal_background);
                        break;
                }
            }
        });

    }



    public void setTitle(String inputTitle) {
        titleBarText.setText(inputTitle);
    }


    static void setStatusBarColor(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        //取消状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(statusColor);
        //设置系统状态栏处于可见状态
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        //让view不根据系统窗口来调整自己的布局
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }

    public LatLng getCurrentLatLngFromMain() {
        return currentLatLng;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        welcomeScreen.onSaveInstanceState(outState);
    }

}