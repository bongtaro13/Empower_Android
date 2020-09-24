package com.example.empower;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.Toast;

import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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
                Toast.makeText(MainActivity2.this, "Item " +index +" clicked", Toast.LENGTH_SHORT).show();

            }
        });
    }
}