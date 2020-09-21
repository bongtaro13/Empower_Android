package com.example.empower.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.empower.R;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

public class StreetViewFragment extends DialogFragment implements OnStreetViewPanoramaReadyCallback {


    private static final LatLng MELBOURNE = new LatLng(-37.819760, 144.968029);
    private static final float ZOOM_BY = 0.5f;

    private View root;
    private SupportStreetViewPanoramaFragment streetViewPanoramaFragment;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        root = inflater.inflate(R.layout.fragment_streetview, container, false);

        SupportStreetViewPanoramaFragment supportStreetViewPanoramaFragment= (SupportStreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);

        return root;

    }




    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        streetViewPanorama.setPosition(MELBOURNE);
        StreetViewPanoramaCamera camera = new StreetViewPanoramaCamera.Builder()
                .zoom(streetViewPanorama.getPanoramaCamera().zoom + ZOOM_BY)
                .tilt(streetViewPanorama.getPanoramaCamera().tilt)
                .bearing(streetViewPanorama.getPanoramaCamera().bearing)
                .build();

    }
}
