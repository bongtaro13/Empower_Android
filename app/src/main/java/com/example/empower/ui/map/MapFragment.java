package com.example.empower.ui.map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.empower.R;
import com.example.empower.entity.SportsVenue;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapViewModel mapViewModel;

    private GoogleMap mapAPI;
    private SupportMapFragment mapFragment;

    private MapView mapView;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapAPI);
        mapFragment.getMapAsync(this);





//        mapViewModel =
//                ViewModelProviders.of(this).get(MapViewModel.class);

//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        mapViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });





        return root;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapAPI = googleMap;
        LatLng monashClayton = new LatLng(-37.913342, 145.131799);
        mapAPI.addMarker(new MarkerOptions().position(monashClayton).title("Monash Clayton"));


        // set the camera position of application when oping the map on ready
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(monashClayton).zoom(12).build();

        mapAPI.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));



    }
}