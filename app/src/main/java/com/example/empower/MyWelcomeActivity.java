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
                        "Logo")
                )
                .page(new BasicPage(R.drawable.map_guide,
                        "Map guide",
                        "This is text in map guide")
                        .background(R.color.red_background)
                )
                .page(new BasicPage(R.drawable.news_guide,
                        "News guide",
                        "This is text in news guide")
                )
                .swipeToDismiss(true)
                .build();
    }
}
