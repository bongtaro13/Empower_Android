package com.example.empower.ui.map;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.empower.R;
import com.example.empower.database.LikedVenue;
import com.example.empower.entity.ActiveHour;
import com.example.empower.entity.Venue;
import com.example.empower.ui.about.AboutViewModel;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.StreetViewPanoramaView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.OpeningHours;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;


public class VenueDetailPopup extends BottomPopupView {


    // top menu bar
    private TextView venueName;
    private ImageButton closeButton;
    private Switch accessibleSwtih;

    // venue details
    private TextView venueType;
    private TextView venueAddress;
    private TextView activeHour;
    private TextView venueSuburb;
    private TextView venueBusinessCategory;
    private TextView venueLga;

    private String latitude;
    private String longitude;

    private Boolean switchFlag;

    private ActiveHour active_hours = new ActiveHour();


    private Bundle venueDetail;

    private String selectedPlaceID = "ChIJdX6CmVhE1moRjNjM6yDQJBs";


    public VenueDetailPopup(@NonNull Context context, Bundle venueInformationBundle) {
        super(context);
        venueDetail = venueInformationBundle;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.venuedetail_popup;
    }


    @Override
    protected void onCreate() {
        super.onCreate();


        venueName = findViewById(R.id.venueName_map_popup);
        closeButton = findViewById(R.id.close_map_popup);
        accessibleSwtih = findViewById(R.id.accessible_switch);


        SharedPreferences accessibleFlag = getContext().getSharedPreferences("switchFlag", Context.MODE_PRIVATE);
        switchFlag = accessibleFlag.getBoolean("flag", false);
        accessibleSwtih.setChecked(switchFlag);

        accessibleSwtih.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences accessibleFlag = getContext().getSharedPreferences("switchFlag", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = accessibleFlag.edit();
                    editor.putBoolean("flag", true);
                    editor.apply();
                } else {
                    SharedPreferences accessibleFlag = getContext().getSharedPreferences("switchFlag", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = accessibleFlag.edit();
                    editor.putBoolean("flag", false);
                    editor.apply();
                }
            }
        });


        venueType = findViewById(R.id.venueType_map_popup);
        venueAddress = findViewById(R.id.venueAddress_map_popup);

        activeHour = findViewById(R.id.active_time_map_popup);

        venueSuburb = findViewById(R.id.suburb_map_popup);
        venueBusinessCategory = findViewById(R.id.businessCategory_map_popup);
        venueLga = findViewById(R.id.LGA_map_popup);


        Venue selectLikedVenue = venueDetail.getParcelable("selectLikedVenue");


        latitude = selectLikedVenue.getLatitude();
        longitude = selectLikedVenue.getLongitude();

        String activeHourString = venueDetail.getString("activeHour");

        if (selectLikedVenue != null) {
            venueName.setText(selectLikedVenue.getName());
            venueType.setText(selectLikedVenue.getType());
            venueAddress.setText(selectLikedVenue.getAddress());
            venueSuburb.setText(selectLikedVenue.getSuburb() + " " + selectLikedVenue.getPostcode());
            venueBusinessCategory.setText(selectLikedVenue.getBusinessCategory().replace(",", " "));
            venueLga.setText(selectLikedVenue.getLga());

            if (selectLikedVenue.getType().equals("sport venue")) {
                activeHour.setVisibility(VISIBLE);
                activeHour.setText(active_hours.getAciveHours());
            } else {
                activeHour.setVisibility(INVISIBLE);
            }
        }


        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String methodPath = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=";
        String inpuType = "&inputtype=textquery&fields=place_id,rating&locationbias=circle:2000@";
        String placeKey = "&key=AIzaSyBenJ8IiMcO7vlKFYcZXb9WhKWuTQEJeo4";
        String venueNameInput = selectLikedVenue.getName().replace(" ", "%20");
        String venueLat = selectLikedVenue.getLatitude();
        String venueLng = selectLikedVenue.getLongitude();

        String url = methodPath + venueNameInput + inpuType + venueLat + "," + venueLng + placeKey;

        // Request a string response from the provided URL
        StringRequest placeIDRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String result) {
                try {
                    JSONObject responseResult = new JSONObject(result);
                    JSONArray candidatesJsonArray = responseResult.getJSONArray("candidates");
                    JSONObject firstCandidate = (JSONObject) candidatesJsonArray.get(0);
                    selectedPlaceID = firstCandidate.getString("place_id");
// Initialize the SDK
                    Places.initialize(getContext().getApplicationContext(), "AIzaSyBenJ8IiMcO7vlKFYcZXb9WhKWuTQEJeo4");

                    // Create a new PlacesClient instance
                    PlacesClient placesClient = Places.createClient(getContext());

                    // Define a Place ID.
                    String placeId = selectedPlaceID;

                    // Specify fields. Requests for photos must always have the PHOTO_METADATAS field.
                    final List<Place.Field> fields = Collections.singletonList(Place.Field.PHOTO_METADATAS);

                    // Get a Place object (this example uses fetchPlace(), but you can also use findCurrentPlace())
                    final FetchPlaceRequest placeRequest = FetchPlaceRequest.newInstance(placeId, fields);

                    placesClient.fetchPlace(placeRequest).addOnSuccessListener((response) -> {
                        final Place place = response.getPlace();

                        OpeningHours openingHours = place.getOpeningHours();
                        if (openingHours != null) {
                            activeHour.setText(openingHours.toString());
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(placeIDRequest);


        closeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });


    }


    //完全可见执行
    @Override
    protected void onShow() {
        super.onShow();
        Log.e("tag", "venue details onShow");

    }

    //完全消失执行
    @Override
    protected void onDismiss() {
        super.onDismiss();
        Log.e("tag", "venue details onDismiss");

    }

    @Override
    protected int getMaxHeight() {
//        return XPopupUtils.getWindowHeight(getContext());
        return (int) (XPopupUtils.getWindowHeight(getContext()) * .9f);
    }


}


