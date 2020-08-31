package com.example.empower.ui.map;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.empower.MainActivity;
import com.example.empower.R;
import com.example.empower.entity.LocationAddressPair;
import com.example.empower.entity.SportsVenue;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
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
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static android.content.Context.LOCATION_SERVICE;

public class MapFragment extends Fragment implements OnMapReadyCallback {


    //user current location

    private MapViewModel mapViewModel;
    private GoogleMap mapAPI;
    private SupportMapFragment mapFragment;

    private MapView mapView;
    private View root;

    private ArrayList<SportsVenue> sportsVenueList = new ArrayList<>();

    private ArrayList<LatLng> latLngList = new ArrayList<>();


    private EditText mapPostcodeEditText;
    private Button mapSearchButton;
    private ProgressBar mapProgressBar;

    private Spinner sportSpinner;
    private List<String> sportDataList;
    private ArrayAdapter<String> sportSpinnerAdapter;


    private static final String TAG = "searchApp";
    static String result = null;
    Integer responseCode = null;
    String responseMessage = "";

    private Location currentLocationFromActivity;


    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        MainActivity mainActivity = (MainActivity) getActivity();
        currentLocationFromActivity = mainActivity.getCurrentLocation();

        root = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapAPI);


        assert mapFragment != null;
        mapFragment.getMapAsync(this);





        createSportsVenueList();

        mapPostcodeEditText = root.findViewById(R.id.map_postcode_editText);
        mapSearchButton = root.findViewById(R.id.map_search_button);
        mapProgressBar = root.findViewById(R.id.map_search_progressBar);



        mapSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String searchPostcode = mapPostcodeEditText.getText().toString();
                String txt = "Searching sports venues for: " + searchPostcode;
                Log.d(TAG, txt);


                // remove spaces
                String searchPostcodeNoSpaces = searchPostcode.replace(" ", "");
                ArrayList<SportsVenue> selectedSportsVenueList = new ArrayList<>();
                selectedSportsVenueList = createSelectedSportsVenueListByPostcode(searchPostcodeNoSpaces);

                if (selectedSportsVenueList.size() > 0) {
                    new AsyncAddMarker().execute(selectedSportsVenueList);
                    mapProgressBar.setVisibility(View.VISIBLE);
                } else {
                    Toast toast_error = Toast.makeText(getContext(), "No result find", Toast.LENGTH_SHORT);
                    toast_error.show();
                }

            }
        });


        sportSpinner = root.findViewById(R.id.map_sports_spinner);
        sportDataList = new ArrayList<>();
        sportDataList.add("Sport");
        sportDataList.add("Wheelchair");
        sportDataList.add("Football");
        sportDataList.add("Basketball");
        sportDataList.add("Cricket");

        sportSpinnerAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,sportDataList);
        sportSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sportSpinner.setAdapter(sportSpinnerAdapter);




        sportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // create new sports venue with selected sport
                if (!sportSpinnerAdapter.getItem(position).equals("Sport")){
                    Toast.makeText(getActivity(), "you selected sport is: " + sportSpinnerAdapter.getItem(position), Toast.LENGTH_SHORT).show();
                    ArrayList<SportsVenue> selectedSportsVenueList = createSelectedSportsVenueListBySport(Objects.requireNonNull(sportSpinnerAdapter.getItem(position)));
                    if (selectedSportsVenueList.size() > 0) {
                        new AsyncAddMarker().execute(selectedSportsVenueList);
                        mapProgressBar.setVisibility(View.VISIBLE);
                    } else {
                        Toast toast_error = Toast.makeText(getContext(), "No result find", Toast.LENGTH_SHORT);
                        toast_error.show();
                    }
                }else {

                }
               }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // ask user to select the sport
                Toast.makeText(getActivity(), "please select a sport you want", Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        googleMap.getUiSettings().setZoomControlsEnabled(true);


        mapAPI = googleMap;



        LatLng currentLocation = new LatLng(currentLocationFromActivity.getLatitude(), currentLocationFromActivity.getLongitude());
        mapAPI.addMarker(new MarkerOptions().position(currentLocation).title("You current location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))).showInfoWindow();


        // set the camera position of application when oping the map on ready
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentLocation).zoom(10).build();

        mapAPI.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }


    // put markers of selected sports venues on the google map
    private class AsyncAddMarker extends AsyncTask<ArrayList<SportsVenue>, Void, ArrayList<LocationAddressPair>> {

        @Override
        protected ArrayList<LocationAddressPair> doInBackground(ArrayList<SportsVenue>... arrayLists) {

            // input parameter of the sports venues array list
            ArrayList<SportsVenue> sportsVenueArrayList = arrayLists[0];

            ArrayList<LocationAddressPair> combineLocationMapping = new ArrayList<>();

            // Use index number to mapping of real address and latitude-longitude address
            for (SportsVenue tempSportsVenue : sportsVenueArrayList) {

                String tempSportsVenueAddress = tempSportsVenue.getAddress() + " "
                        + tempSportsVenue.getPostcode() + " "
                        + tempSportsVenue.getState();
                LatLng tempSportsVenueLatlng = getLocationFromAddress(getContext(), tempSportsVenueAddress);

                if (tempSportsVenueLatlng != null) {
                    combineLocationMapping.add(new LocationAddressPair(tempSportsVenueLatlng, tempSportsVenue));
                }
            }



            return combineLocationMapping;

        }

        @Override
        protected void onPostExecute(ArrayList<LocationAddressPair> combineLocationMapping) {
            super.onPostExecute(combineLocationMapping);

            if (combineLocationMapping.size() != 0) {
                mapAPI.clear();
                for (LocationAddressPair tempLocationAddressPair : combineLocationMapping) {

                    LatLng tempSportsVenueLocation = tempLocationAddressPair.getLatLngInfo();
                    SportsVenue tempSportsVenue = tempLocationAddressPair.getSportsVenueInfo();


                    mapAPI.addMarker(new MarkerOptions().position(tempSportsVenueLocation)
                            .title(tempSportsVenue.getName())
                            .snippet(tempSportsVenue.getAddress() + " " + tempSportsVenue.getPostcode()));

                }
                LatLng currentLocation = new LatLng(currentLocationFromActivity.getLatitude(), currentLocationFromActivity.getLongitude());
                mapAPI.addMarker(new MarkerOptions().position(currentLocation).title("You current location")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                CameraPosition newCameraPosition = new CameraPosition.Builder()
                        .target(currentLocation).zoom(11).build();


                mapAPI.moveCamera(CameraUpdateFactory.newCameraPosition(newCameraPosition));
                mapProgressBar.setVisibility(View.GONE);
                Toast toast_success = Toast.makeText(getContext(), "Result found", Toast.LENGTH_SHORT);
                toast_success.show();
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


    public ArrayList<SportsVenue> createSelectedSportsVenueListByPostcode(String inputPostcode) {
        ArrayList<SportsVenue> selectedSportsVenuList = new ArrayList<>();
        if (inputPostcode.length() == 0) {
            return selectedSportsVenuList;
        }
        for (SportsVenue tempSportsVenue : sportsVenueList) {
            if (tempSportsVenue.getPostcode().equals(inputPostcode)) {
                selectedSportsVenuList.add(tempSportsVenue);
            }
        }

        return selectedSportsVenuList;
    }

    public ArrayList<SportsVenue> createSelectedSportsVenueListBySport(String inputSportName){
        ArrayList<SportsVenue> selectedSportsVenuList = new ArrayList<>();
        String sportName = inputSportName.toLowerCase();
        if (! inputSportName.equals("sport")){
            for (SportsVenue tempSportsVenue : sportsVenueList){
                if (tempSportsVenue.getBusinessCategory().toLowerCase().contains(sportName)){
                    selectedSportsVenuList.add(tempSportsVenue);
                }
            }
        }

        return selectedSportsVenuList;
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