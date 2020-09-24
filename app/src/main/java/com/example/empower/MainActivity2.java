package com.example.empower;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

public class MainActivity2 extends AppCompatActivity {

    private View root;
    private TextView titleBarText;
    private ImageView aboutImage;
    private RelativeLayout titleBarLayout;


    @SuppressLint({"ResourceAsColor", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            }
        });

        setTitle("Map");

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);

        BottomNavigationItem bottomNavigationNews = new BottomNavigationItem
                ("News", ContextCompat.getColor(this, R.color.blue_background), R.drawable.ic_news_black_24dp);
        BottomNavigationItem bottomNavigationMap = new BottomNavigationItem
                ("Map", ContextCompat.getColor(this, R.color.orange_background), R.drawable.ic_map_black_24dp);
        BottomNavigationItem bottomNavigationContact = new BottomNavigationItem
                ("Contact us", ContextCompat.getColor(this, R.color.teal_background), R.drawable.ic_about_black_24dp);


        bottomNavigationView.addTab(bottomNavigationNews);
        bottomNavigationView.addTab(bottomNavigationMap);
        bottomNavigationView.addTab(bottomNavigationContact);

        bottomNavigationView.isWithText(false);
        bottomNavigationView.selectTab(1);

        bottomNavigationView.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
            @Override
            public void onNavigationItemClick(int index) {
                Toast.makeText(MainActivity2.this, "Item " + index + " clicked", Toast.LENGTH_SHORT).show();
                if (index == 0) {
                    setStatusBarColor(MainActivity2.this, R.color.blue_background);
                    setTitle("News");
                    titleBarLayout.setBackgroundColor(R.color.blue_background);
                } else if (index == 1) {
                    setStatusBarColor(MainActivity2.this, R.color.orange_background);
                    setTitle("Map");
                    titleBarLayout.setBackgroundColor(R.color.orange_background);
                } else {
                    setStatusBarColor(MainActivity2.this, R.color.teal_background);
                    setTitle("Contact");
                    titleBarLayout.setBackgroundColor(R.color.teal_background);

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

}