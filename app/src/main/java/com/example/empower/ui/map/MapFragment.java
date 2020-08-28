package com.example.empower.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.empower.R;
import com.example.empower.entity.SportsVenue;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.Context.LOCATION_SERVICE;

public class MapFragment extends Fragment implements OnMapReadyCallback {


    private MapViewModel mapViewModel;

    private GoogleMap mapAPI;
    private SupportMapFragment mapFragment;

    private MapView mapView;

    private ArrayList<SportsVenue> sportsVenueList = new ArrayList<>();
    private ArrayList<LatLng> latLngList = new ArrayList<>();


    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapAPI);


        assert mapFragment != null;
        mapFragment.getMapAsync(this);


        createSportsVenueList();


        new AsyncAddMarker().execute(sportsVenueList);


        return root;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        googleMap.getUiSettings().setZoomControlsEnabled(true);


        mapAPI = googleMap;


        LatLng monashClayton = new LatLng(-37.913342, 145.131799);
        mapAPI.addMarker(new MarkerOptions().position(monashClayton).title("Monash Clayton")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));


        // set the camera position of application when oping the map on ready
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(monashClayton).zoom(12).build();

        mapAPI.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));



    }



    // put markers of selected sports venues on the google map
    private class AsyncAddMarker extends AsyncTask<ArrayList<SportsVenue>, Void, ArrayList<LatLng>> {

        @Override
        protected ArrayList<LatLng> doInBackground(ArrayList<SportsVenue>... arrayLists) {

            // input parameter of the sports venues array list
            ArrayList<SportsVenue> sportsVenueArrayList = arrayLists[0];

            ArrayList<LatLng> latLngList = new ArrayList<>();


            // Use index number to mapping of real address and latitude-longitude address
            for (SportsVenue tempSportsVenue : sportsVenueArrayList) {

                String tempSportsVenueAddress = tempSportsVenue.getAddress() + " "
                        + tempSportsVenue.getPostcode() + " "
                        + tempSportsVenue.getState();
                LatLng tempSportsVenueLatlng = getLocationFromAddress(getContext(), tempSportsVenueAddress);

                if (tempSportsVenueLatlng != null) {
                    latLngList.add(tempSportsVenueLatlng);
                }
            }


            return latLngList;

        }

        @Override
        protected void onPostExecute(ArrayList<LatLng> latLngList) {
            super.onPostExecute(latLngList);
            if (latLngList.size() != 0) {
                for (int i = 0; i < latLngList.size(); i++) {
                    LatLng tempSportsVenue = latLngList.get(i);
                    mapAPI.addMarker(new MarkerOptions().position(tempSportsVenue)
                            .title(sportsVenueList.get(i).getName())
                            .snippet(sportsVenueList.get(i).getAddress() + " " + sportsVenueList.get(i).getPostcode()));

                }
            }
        }

    }


    // get location with latlng from the address
    public LatLng getLocationFromAddress(Context context, String venueAddress) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        LatLng resultLatLon = null;

        try {
            addresses = geocoder.getFromLocationName(venueAddress, 3);

            if (addresses == null) {
                return null;
            }

            Address location = addresses.get(0);
            if (location != null) {
                resultLatLon = new LatLng(location.getLatitude(), location.getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultLatLon;

    }


    public void createSportsVenueList() {
        InputStream inputStream = getResources().openRawResource(R.raw.disability_wheelchair_sports_vic);
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream, Charset.forName("UTF-8"))
        );

        String line = "";
        try {
            while ((line = bufferedReader.readLine()) != null) {

                String[] sportsVenueRawData = line.split(",");
                int length = sportsVenueRawData.length;

                if (length != 9) {
                    continue;
                }

                SportsVenue tempSportsVenue = new SportsVenue();
                tempSportsVenue.setVenueID(sportsVenueRawData[0]);
                tempSportsVenue.setName(sportsVenueRawData[1]);
                tempSportsVenue.setAddress(sportsVenueRawData[2]);
                tempSportsVenue.setSuburb(sportsVenueRawData[3]);
                tempSportsVenue.setPostcode(sportsVenueRawData[4]);
                tempSportsVenue.setState(sportsVenueRawData[5]);
                tempSportsVenue.setBusinessCategory(sportsVenueRawData[6]);
                tempSportsVenue.setLga(sportsVenueRawData[7]);
                tempSportsVenue.setRegion(sportsVenueRawData[8]);
                sportsVenueList.add(tempSportsVenue);

                Log.d("MainActivity", "current sports venus object: " + tempSportsVenue.toString());
            }
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading data from the line " + line, e);
            e.printStackTrace();
        }

    }

}