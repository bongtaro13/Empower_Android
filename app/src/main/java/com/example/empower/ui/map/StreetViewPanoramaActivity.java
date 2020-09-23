package com.example.empower.ui.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.empower.MainActivity;
import com.example.empower.R;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaLink;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

public class StreetViewPanoramaActivity extends AppCompatActivity {

    // George St, Melbourne
    private static final LatLng MELBOURNE = new LatLng(-37.819760, 144.968029);


    // Santorini, Greece
    private static final String SANTORINI = "WddsUw1geEoAAAQIt9RnsQ";

    // LatLng with no panorama
    private static final LatLng INVALID = new LatLng(-45.125783, 151.276417);

    /**
     * The amount in degrees by which to scroll the camera
     */
    private static final int PAN_BY_DEG = 30;

    private static final float ZOOM_BY = 0.5f;

    private StreetViewPanorama mStreetViewPanorama;

    private SeekBar mCustomDurationBar;


    private Button backToMapButton;
    private LatLng currentLatLng;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.street_view_panorama_navigation_demo);

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment)
                        getSupportFragmentManager().findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(
                new OnStreetViewPanoramaReadyCallback() {
                    @Override
                    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
                        mStreetViewPanorama = panorama;
                        // Only set the panorama to SYDNEY on startup (when no panoramas have been
                        // loaded which is when the savedInstanceState is null).
                        if (savedInstanceState == null) {
                            mStreetViewPanorama.setPosition(MELBOURNE);
                        }
                    }
                });
        mCustomDurationBar = (SeekBar) findViewById(R.id.duration_bar);

        String stringCurrentLatLng = getIntent().getStringExtra("stringLatLngResult");
        String[] arrayLatLng = stringCurrentLatLng.split(",");
        double latitude = Double.parseDouble(arrayLatLng[0]);
        double longitude = Double.parseDouble(arrayLatLng[1]);
        currentLatLng = new LatLng(latitude, longitude);

        backToMapButton = findViewById(R.id.streetView_backToMap);
        backToMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(StreetViewPanoramaActivity.this, MainActivity.class);
                String stringLatLngResult = currentLatLng.latitude + "," + currentLatLng.longitude;
                intent.putExtra("stringLatLngResult", stringLatLngResult);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * When the panorama is not ready the PanoramaView cannot be used. This should be called on
     * all entry points that call methods on the Panorama API.
     */
    private boolean checkReady() {
        if (mStreetViewPanorama == null) {
            Toast.makeText(this, "panorama_not_ready", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



    /**
     * Called when the Animate To Sydney button is clicked.
     */
    public void onGoToMelbourne(View view) {
        if (!checkReady()) {
            return;

        }
        mStreetViewPanorama.setPosition(MELBOURNE);
    }

    /**
     * Called when the Animate To Santorini button is clicked.
     */
    public void onGoToSantorini(View view) {
        if (!checkReady()) {
            return;
        }
        mStreetViewPanorama.setPosition(SANTORINI);
    }

    /**
     * Called when the Animate To Invalid button is clicked.
     */
    public void onGoToInvalid(View view) {
        if (!checkReady()) {
            return;
        }
        mStreetViewPanorama.setPosition(INVALID);
    }

    public void onZoomIn(View view) {
        if (!checkReady()) {
            return;
        }

        mStreetViewPanorama.animateTo(
                new StreetViewPanoramaCamera.Builder().zoom(
                        mStreetViewPanorama.getPanoramaCamera().zoom + ZOOM_BY)
                        .tilt(mStreetViewPanorama.getPanoramaCamera().tilt)
                        .bearing(mStreetViewPanorama.getPanoramaCamera().bearing)
                        .build(), getDuration());
    }

    public void onZoomOut(View view) {
        if (!checkReady()) {
            return;
        }

        mStreetViewPanorama.animateTo(
                new StreetViewPanoramaCamera.Builder().zoom(
                        mStreetViewPanorama.getPanoramaCamera().zoom - ZOOM_BY)
                        .tilt(mStreetViewPanorama.getPanoramaCamera().tilt)
                        .bearing(mStreetViewPanorama.getPanoramaCamera().bearing)
                        .build(), getDuration());
    }

    public void onPanLeft(View view) {
        if (!checkReady()) {
            return;
        }

        mStreetViewPanorama.animateTo(
                new StreetViewPanoramaCamera.Builder().zoom(
                        mStreetViewPanorama.getPanoramaCamera().zoom)
                        .tilt(mStreetViewPanorama.getPanoramaCamera().tilt)
                        .bearing(mStreetViewPanorama.getPanoramaCamera().bearing - PAN_BY_DEG)
                        .build(), getDuration());
    }

    public void onPanRight(View view) {
        if (!checkReady()) {
            return;
        }

        mStreetViewPanorama.animateTo(
                new StreetViewPanoramaCamera.Builder().zoom(
                        mStreetViewPanorama.getPanoramaCamera().zoom)
                        .tilt(mStreetViewPanorama.getPanoramaCamera().tilt)
                        .bearing(mStreetViewPanorama.getPanoramaCamera().bearing + PAN_BY_DEG)
                        .build(), getDuration());

    }

    public void onPanUp(View view) {
        if (!checkReady()) {
            return;
        }

        float currentTilt = mStreetViewPanorama.getPanoramaCamera().tilt;
        float newTilt = currentTilt + PAN_BY_DEG;

        newTilt = (newTilt > 90) ? 90 : newTilt;

        mStreetViewPanorama.animateTo(
                new StreetViewPanoramaCamera.Builder()
                        .zoom(mStreetViewPanorama.getPanoramaCamera().zoom)
                        .tilt(newTilt)
                        .bearing(mStreetViewPanorama.getPanoramaCamera().bearing)
                        .build(), getDuration());
    }

    public void onPanDown(View view) {
        if (!checkReady()) {
            return;
        }

        float currentTilt = mStreetViewPanorama.getPanoramaCamera().tilt;
        float newTilt = currentTilt - PAN_BY_DEG;

        newTilt = (newTilt < -90) ? -90 : newTilt;

        mStreetViewPanorama.animateTo(
                new StreetViewPanoramaCamera.Builder()
                        .zoom(mStreetViewPanorama.getPanoramaCamera().zoom)
                        .tilt(newTilt)
                        .bearing(mStreetViewPanorama.getPanoramaCamera().bearing)
                        .build(), getDuration());
    }

    public void onRequestPosition(View view) {
        if (!checkReady()) {
            return;
        }
        if (mStreetViewPanorama.getLocation() != null) {
            Toast.makeText(view.getContext(), mStreetViewPanorama.getLocation().position.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void onMovePosition(View view) {
        StreetViewPanoramaLocation location = mStreetViewPanorama.getLocation();
        StreetViewPanoramaCamera camera = mStreetViewPanorama.getPanoramaCamera();
        if (location != null && location.links != null) {
            StreetViewPanoramaLink link = findClosestLinkToBearing(location.links, camera.bearing);
            mStreetViewPanorama.setPosition(link.panoId);
        }
    }

    public static StreetViewPanoramaLink findClosestLinkToBearing(StreetViewPanoramaLink[] links,
                                                                  float bearing) {
        float minBearingDiff = 360;
        StreetViewPanoramaLink closestLink = links[0];
        for (StreetViewPanoramaLink link : links) {
            if (minBearingDiff > findNormalizedDifference(bearing, link.bearing)) {
                minBearingDiff = findNormalizedDifference(bearing, link.bearing);
                closestLink = link;
            }
        }
        return closestLink;
    }

    // Find the difference between angle a and b as a value between 0 and 180
    public static float findNormalizedDifference(float a, float b) {
        float diff = a - b;
        float normalizedDiff = diff - (float) (360 * Math.floor(diff / 360.0f));
        return (normalizedDiff < 180.0f) ? normalizedDiff : 360.0f - normalizedDiff;
    }

    private long getDuration() {
        return mCustomDurationBar.getProgress();
    }
}