package com.example.empower.ui.about;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.empower.R;
import com.example.empower.entity.Venue;
import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.empower.ui.dialog.LikedVenueDialogAboutFragment.sendMail;


public class LikedVenuePopup extends BottomPopupView {


    private Bundle venueInformation;


    // components ont the liked venue detail info window
    private ImageView venueImage;
    private TextView venueID;
    private TextView venueName;
    private TextView venueType;
    private TextView venueAddress;
    private TextView venueSuburb;
    private TextView venueBusinessCategory;
    private TextView venueLga;


    private ImageView shareVenueInfo;

    private ImageButton closeButton;

//    private Button closeVenueDetail;

    private String selectedPlaceID = "ChIJdX6CmVhE1moRjNjM6yDQJBs";


    public LikedVenuePopup(@NonNull Context context, Bundle venueInfoBundle) {

        super(context);
        venueInformation = venueInfoBundle;
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.likedvenue_popup;
    }


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate() {
        super.onCreate();

        venueImage = findViewById(R.id.venuePicture);

        //venueID = findViewById(R.id.venueID_detail_textView);
        venueName = findViewById(R.id.venueName_detail_textView);
        venueType = findViewById(R.id.venueType_detail_textView);
        venueAddress = findViewById(R.id.venueAddress_detail_textView);
        venueSuburb = findViewById(R.id.suburb_detail_textView);
        venueBusinessCategory = findViewById(R.id.businessCategory_detail_textView);
        venueLga = findViewById(R.id.LGA_detail_textView);

        shareVenueInfo = findViewById(R.id.share_email_detail_textView);



        closeButton = findViewById(R.id.close_button_likedVenueDetail);
        closeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        Venue selectLikedVenue = venueInformation.getParcelable("selectLikedVenue");
        if (selectLikedVenue != null) {
            //venueID.setText(selectLikedVenue.getVenueID());
            venueName.setText(selectLikedVenue.getName());
            venueType.setText(selectLikedVenue.getType());
            venueAddress.setText(selectLikedVenue.getAddress());
            venueSuburb.setText(selectLikedVenue.getSuburb() + " " + selectLikedVenue.getPostcode());
            venueBusinessCategory.setText(selectLikedVenue.getBusinessCategory().replace(",", " "));
            venueLga.setText(selectLikedVenue.getLga());

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
//                        selectedPlaceID = "ChIJdX6CmVhE1moRjNjM6yDQJBs";

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

                // Get the photo metadata.
                final List<PhotoMetadata> metadata = place.getPhotoMetadatas();
                if (metadata == null || metadata.isEmpty()) {
                    Log.w(TAG, "No photo metadata.");
                    return;
                }
                final PhotoMetadata photoMetadata = metadata.get(0);

                // Get the attribution text.
                final String attributions = photoMetadata.getAttributions();

                // Create a FetchPhotoRequest.
                final FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                        .build();
                placesClient.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
                    Bitmap bitmap = fetchPhotoResponse.getBitmap();
                    if (bitmap != null) {
                        venueImage.setImageBitmap(bitmap);
                    }else {
                        venueImage.setImageResource(R.raw.likedvenue_pic1);
                    }
                }).addOnFailureListener((exception) -> {
                    if (exception instanceof ApiException) {
                        final ApiException apiException = (ApiException) exception;
                        Log.e(TAG, "Place not found: " + exception.getMessage());
                        final int statusCode = apiException.getStatusCode();


                    }
                });
            });







        }


        shareVenueInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "email share: click");
                sendMail(getContext(), "Venue share",
                        "Hi, I found a sport venue in "
                                + selectLikedVenue.getAddress() + "called "
                                +  selectLikedVenue.getName());
            }
        });

    }


    //完全可见执行
    @Override
    protected void onShow() {
        super.onShow();
        Log.e("tag", "liked venue detail onShow");

    }

    //完全消失执行
    @Override
    protected void onDismiss() {
        super.onDismiss();
        Log.e("tag", "liked venue detail onDismiss");


    }

    @Override
    protected int getMaxHeight() {
//        return XPopupUtils.getWindowHeight(getContext());
        return (int) (XPopupUtils.getWindowHeight(getContext()) * .9f);
    }


    private class PLacePhotoSearchAsyncTask extends AsyncTask<ArrayList<String>, Void, String> {

        @Override
        protected String doInBackground(ArrayList<String>... arrayLists) {

            String locationName = arrayLists[0].get(0).replace(" ", "%20");
            locationName = "Audiology%20ultra%20hearing%20service";

            String locationLatitude = arrayLists[0].get(1);
            String locationLongitude = arrayLists[0].get(2);


            String url = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=" + locationName + "&inputtype=textquery&fields=photos,,opening_hours&locationbias=circle:2000@47.6918452,-122.2226413&key=AIzaSyBenJ8IiMcO7vlKFYcZXb9WhKWuTQEJeo4";
            return null;
        }
    }


}
