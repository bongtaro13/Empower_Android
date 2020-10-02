package com.example.empower.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.empower.R;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.StreetViewPanoramaView;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.lxj.xpopup.core.BottomPopupView;

public class StreetViewBottomPopup extends BottomPopupView  {

    private StreetViewPanorama mStreetViewPanorama;
    private StreetViewPanoramaView streetViewPanoramaView;

    private LatLng currentLocation = new LatLng(-37.819760, 144.968029);
    private static final String STREETVIEW_BUNDLE_KEY = "StreetViewBundleKey";

    public StreetViewBottomPopup(@NonNull Context context) {
        super(context);
    }


    protected int getIplLayoutId() {
        return R.layout.street_view_bottom_popup;
    }


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate();


        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment)
                        getSupportFragmentManager().findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(
                new OnStreetViewPanoramaReadyCallback() {
                    @Override
                    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
                        streetViewPanorama = panorama;
                        streetViewPanorama.setOnStreetViewPanoramaChangeListener(
                                SplitStreetViewPanoramaAndMapDemoActivity.this);
                        // Only need to set the position once as the streetview fragment will maintain
                        // its state.
                        if (savedInstanceState == null) {
                            streetViewPanorama.setPosition(SYDNEY);
                        }
                    }
                });

        StreetViewPanoramaOptions options = new StreetViewPanoramaOptions();
        if (savedInstanceState == null) {
            options.position(currentLocation);
        }


        streetViewPanoramaView = new StreetViewPanoramaView(getContext(), options);
        addContentView(streetViewPanoramaView,
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        Bundle streetViewBundle = null;
        if (savedInstanceState != null) {
            streetViewBundle = savedInstanceState.getBundle(STREETVIEW_BUNDLE_KEY);
        }
        streetViewPanoramaView.onCreate(streetViewBundle);





    }

    @Override
    protected void onShow() {
        super.onShow();
        findViewById(R.id.streetviewpanorama_popup);



    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
        Log.e("tag", "CustomEditTextBottomPopup  onDismiss");
    }


    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState();
        Bundle streetViewBundle = outState.getBundle(STREETVIEW_BUNDLE_KEY);
        if (streetViewBundle == null) {
            streetViewBundle = new Bundle();
            outState.putBundle(STREETVIEW_BUNDLE_KEY, streetViewBundle);
        }

        streetViewPanoramaView.onSaveInstanceState(streetViewBundle);
    }



//    @Override
//    protected int getMaxHeight() {
//        return (int) (XPopupUtils.getWindowHeight(getContext())*0.75);
//    }
}
