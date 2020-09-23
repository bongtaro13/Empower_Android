package com.example.empower;

import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.TitlePage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;

public class MyWelcomeActivity extends WelcomeActivity {
    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .defaultBackgroundColor(R.color.blue_background)
                .page(new TitlePage(R.drawable.logo,
                        "Title")
                )
                .page(new BasicPage(R.drawable.football,
                        "Header",
                        "More text.")
                        .background(R.color.red_background)
                )
                .page(new BasicPage(R.drawable.basketball,
                        "Lorem ipsum",
                        "dolor sit amet.")
                )
                .swipeToDismiss(true)
                .build();
    }
}
