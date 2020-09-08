package com.example.empower.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.empower.MainActivity;
import com.example.empower.R;
import com.example.empower.entity.LocationAddressPair;
import com.example.empower.entity.SportsVenue;
import com.example.empower.ui.dialog.GuideDialogMapFragment;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * class name: MapFragment.java
 * function: Main aim of this fragment is to displayed disability supported sports venues on the map
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {


    private MapViewModel mapViewModel;
    private GoogleMap mapAPI;
    private SupportMapFragment mapFragment;

    private View root;

    // arrayList for all all sports venus read from teh csv data file
    private ArrayList<SportsVenue> sportsVenueList = new ArrayList<>();
    private ArrayList<LatLng> latLngList = new ArrayList<>();

    // visual component of the map fragment, include search input, search box, and a progressBar to display progress
    private EditText mapPostcodeEditText;
    private Button mapSearchButton;
    private ProgressBar mapProgressBar;

    // spinner and related data array
    private Spinner sportSpinner;
    private List<String> sportSpinnerDataList;
    private ArrayAdapter<String> sportSpinnerAdapter;


    private static final String TAG = "searchApp";
    static String result = null;
    Integer responseCode = null;
    String responseMessage = "";


    //user current location
    private Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    //FireStore database
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    // initialize the mapFragment
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        root = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapAPI);

        assert mapFragment != null;
        mapFragment.getMapAsync(this);


        // get SharedPreferences value from main activity, check if the guide picture is need or not
        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE);
        if (sp.getBoolean("maps_first_time_dialog", true)) {
            // pop up window for map page help guide
            GuideDialogMapFragment dialogFragment = new GuideDialogMapFragment();
            dialogFragment.show(getFragmentManager(), "GuideDialogFragment");
            dialogFragment.setCancelable(true);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("maps_first_time_dialog", false);
            editor.apply();
        }


        // create sports venue list from csv file
        //createSportsVenueList();
        getSportsListFromFireStore();


        mapPostcodeEditText = root.findViewById(R.id.map_postcode_editText);
        mapSearchButton = root.findViewById(R.id.map_search_button);
        mapProgressBar = root.findViewById(R.id.map_search_progressBar);


        // add listener for search button, if clicked, use input postcode to find the matched sports venus
        mapSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String searchPostcode = mapPostcodeEditText.getText().toString();
                String txt = "Searching sports venues for: " + searchPostcode;
                Log.d(TAG, txt);


                // remove spaces in the postcode input
                String searchPostcodeNoSpaces = searchPostcode.replace(" ", "");
                ArrayList<SportsVenue> selectedSportsVenueList = new ArrayList<>();
                SportsVenuesSelector sportsVenuesSelector = new SportsVenuesSelector(sportsVenueList);
                selectedSportsVenueList = sportsVenuesSelector.createSelectedSportsVenueListByPostcode(searchPostcodeNoSpaces);

                if (selectedSportsVenueList.size() > 0) {
                    new AsyncAddMarker().execute(selectedSportsVenueList);
                    mapProgressBar.setVisibility(View.VISIBLE);
                } else {
                    Toast toast_error = Toast.makeText(getContext(), "No result find", Toast.LENGTH_SHORT);
                    toast_error.show();
                }

            }
        });

        // initialize the spinner of different sport
        sportSpinner = root.findViewById(R.id.map_sports_spinner);
        sportSpinnerDataList = new ArrayList<>();
        sportSpinnerDataList.add("Sport");
        sportSpinnerDataList.add("Football");
        sportSpinnerDataList.add("Basketball");
        sportSpinnerDataList.add("Cricket");

        sportSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sportSpinnerDataList);
        sportSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sportSpinner.setAdapter(sportSpinnerAdapter);


        // add listener to the spinner selected action
        sportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // create new sports venue with selected sport
                // if spinner is selected with keyword "Sport", nothing happen in this condition.
                if (!sportSpinnerAdapter.getItem(position).equals("Sport")) {
                    Toast.makeText(getActivity(), "you selected sport is: " + sportSpinnerAdapter.getItem(position), Toast.LENGTH_SHORT).show();
                    SportsVenuesSelector sportsVenuesSelector = new SportsVenuesSelector(sportsVenueList);
                    ArrayList selectedSportsVenueList = sportsVenuesSelector.createSelectedSportsVenueListBySport(sportSpinnerAdapter.getItem(position));
                    if (selectedSportsVenueList.size() > 0) {
                        new AsyncAddMarker().execute(selectedSportsVenueList);
                        mapProgressBar.setVisibility(View.VISIBLE);
                    } else {
                        Toast toast_error = Toast.makeText(getContext(), "No result find", Toast.LENGTH_SHORT);
                        toast_error.show();
                    }
                } else {

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


    // default method for map is created at the beginning
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mapAPI = googleMap;

        MapsInitializer.initialize(getContext());
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        currentLocation = new Location("default location");
//            currentLocation.setLongitude(145.13170297689055);
//            currentLocation.setLatitude(-37.91341883508321);

        currentLocation.setLongitude(147.13170297689055);
        currentLocation.setLatitude(-35.91341883508321);


        // add user current location on map with location info from the MainActivity
//        LatLng currentLocationMarkerOnMap = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
//        mapAPI.addMarker(new MarkerOptions().position(currentLocationMarkerOnMap).title("You current location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))).showInfoWindow();


//        LatLng melbourneArea = new LatLng(-37.8409, 144.9464);
//
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(melbourneArea).zoom(10).build();
//
//        mapAPI.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

            }
        });

        task.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                    try {
                        resolvableApiException.startResolutionForResult(getActivity(), 51);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });

        getCurrentLocation();
        // add user current location on map with location info from the MainActivity
        currentLocation.getLatitude();
        currentLocation.getLongitude();
        LatLng currentLocationMarkerOnMap = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        mapAPI.addMarker(new MarkerOptions().position(currentLocationMarkerOnMap)
                .title("You current location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
                .showInfoWindow();


        // set the camera position of application when oping the map on ready


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentLocationMarkerOnMap).zoom(12).build();
//
        mapAPI.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }


    public void FindNearVenue() {


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

        // after getting combineLocationMapping of matched sports venues and their latitude and longitude info,
        // place markers with red color on the map, also the address info can be added with snippet of the marker
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
                getCurrentLocation();
                LatLng currentLocationMarkerOnMap = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                mapAPI.addMarker(new MarkerOptions().position(currentLocationMarkerOnMap).title("You current location")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                CameraPosition newCameraPosition = new CameraPosition.Builder()
                        .target(currentLocationMarkerOnMap).zoom(10).build();


                mapAPI.animateCamera(CameraUpdateFactory.newCameraPosition(newCameraPosition));
                mapProgressBar.setVisibility(View.GONE);
                Toast toast_success = Toast.makeText(getContext(), "Result found", Toast.LENGTH_SHORT);
                toast_success.show();
            }
        }

    }


    // get location with lat & lng from the address
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


    // get the sports venues info from the Cloud FireStore NoSql database
    private void getSportsListFromFireStore() {
        db.collection("sportsVenues")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Map<String, Object> tempSportsVenueMap = document.getData();
                                String venueId = Objects.requireNonNull(tempSportsVenueMap.get("venueID")).toString();
                                String name = Objects.requireNonNull(tempSportsVenueMap.get("name")).toString();
                                String address = Objects.requireNonNull(tempSportsVenueMap.get("address")).toString();
                                String suburb = Objects.requireNonNull(tempSportsVenueMap.get("suburb")).toString();
                                String postcode = Objects.requireNonNull(tempSportsVenueMap.get("postcode")).toString();
                                String state = Objects.requireNonNull(tempSportsVenueMap.get("state")).toString();
                                String businessCategory = Objects.requireNonNull(tempSportsVenueMap.get("businessCategory")).toString();
                                String lga = Objects.requireNonNull(tempSportsVenueMap.get("lga")).toString();
                                String region = Objects.requireNonNull(tempSportsVenueMap.get("region")).toString();

                                sportsVenueList.add(new SportsVenue(venueId, name, address, suburb, postcode, state, businessCategory, lga, region));


                            }
                        } else {
                            Log.d(TAG, "Error getting sportsVenues documents: " + task.getException());
                        }
                    }
                });
    }


    public void getCurrentLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(getActivity())
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(getActivity())
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int latestLocationIndex = locationResult.getLocations().size() - 1;
                            double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                            currentLocation = new Location("real current location");
                            currentLocation.setLatitude(latitude);
                            currentLocation.setLongitude(longitude);

                        }
                    }
                }, Looper.getMainLooper());


    }


}