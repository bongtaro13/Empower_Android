package com.example.empower;

import androidx.fragment.app.Fragment;

import com.example.empower.ui.welcome.VenueDetailGuideFragment;
import com.example.empower.ui.welcome.VenueLikedGuideFragment;
import com.example.empower.ui.welcome.VenueSearchGuideFragment;
import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.FragmentWelcomePage;
import com.stephentuso.welcome.TitlePage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;

public class MyWelcomeActivity extends WelcomeActivity {
    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .defaultBackgroundColor(R.color.blue_background)
                .page(new FragmentWelcomePage(){
                    @Override
                    protected Fragment fragment(){
                        return new VenueSearchGuideFragment();
                    }
                })
                .page(new FragmentWelcomePage(){
                    @Override
                    protected Fragment fragment(){
                        return new VenueDetailGuideFragment();
                    }
                })
                .page(new FragmentWelcomePage(){
                    @Override
                    protected Fragment fragment(){
                        return new VenueLikedGuideFragment();
                    }
                })
                .swipeToDismiss(true)
                .build();
    }
}
