package com.example.empower.ui.welcomepage;

import android.os.Bundle;

import com.example.empower.R;
import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.TitlePage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;
import com.stephentuso.welcome.WelcomeHelper;



/**
 * Class name: MyWelcomeActivity.java
 * function: Main aim of this function is to create the welcome page with slide function,
 *              and let user slide with different guidance card,
 *              also user can skip this page with skip button
 *              not finished currently, will be done in the next iteration.
 * */
public class MyWelcomeActivity extends WelcomeActivity {

    // design the welcome page by welcome configuration
    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .defaultBackgroundColor(R.color.blue_background)
                .page(new TitlePage(R.drawable.logo,
                        "Title")
                )
                .page(new BasicPage(R.drawable.logo,
                        "Header",
                        "More text.")
                        .background(R.color.red_background)
                )
                .page(new BasicPage(R.drawable.logo,
                        "Lorem ipsum",
                        "dolor sit amet.")
                )
                .swipeToDismiss(true)
                .build();
    }

    public static String welcomeKey() {
        return "WelcomeScreen";
    }

    // initialized welcome helper from the Gradle API
    WelcomeHelper welcomeScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        welcomeScreen = new WelcomeHelper(this, MyWelcomeActivity.class);
        welcomeScreen.show(savedInstanceState);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        welcomeScreen.onSaveInstanceState(outState);
    }

}
