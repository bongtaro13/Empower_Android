package com.example.empower.ui.map;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

import com.example.empower.R;
import com.example.empower.database.LikedVenue;
import com.example.empower.entity.Venue;
import com.example.empower.ui.about.AboutViewModel;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.StreetViewPanoramaView;
import com.google.android.gms.maps.model.LatLng;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import java.util.ArrayList;
import java.util.List;


public class VenueDetailPopup extends BottomPopupView {


    // top menu bar
    private LikeButton heartButton;
    private TextView venueName;
    private ImageButton streetViewButton;
    private ImageButton routerPlannerButton;
    private ImageButton closeButton;

    // venue details
    private TextView venueType;
    private TextView venueAddress;
    private TextView activeHour;
    private TextView venueSuburb;
    private TextView venueBusinessCategory;
    private TextView venueLga;

    private String latitude;
    private String longitude;


    private Bundle venueDetail;


    private boolean heartFlag;


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
        streetViewButton = findViewById(R.id.street_view_map_popup);
        routerPlannerButton = findViewById(R.id.router_planner_map_popup);
        closeButton = findViewById(R.id.close_map_popup);


        venueType = findViewById(R.id.venueType_map_popup);
        venueAddress = findViewById(R.id.venueAddress_map_popup);

        activeHour = findViewById(R.id.active_time_map_popup);

        venueSuburb = findViewById(R.id.suburb_map_popup);
        venueBusinessCategory = findViewById(R.id.businessCategory_map_popup);
        venueLga = findViewById(R.id.LGA_map_popup);


        Venue selectLikedVenue = venueDetail.getParcelable("selectLikedVenue");
        latitude = selectLikedVenue.getLatitude();
        longitude = selectLikedVenue.getLongitude();
        heartFlag = venueDetail.getBoolean("heartFlag");

        if (selectLikedVenue != null) {
            venueName.setText(selectLikedVenue.getName());
            venueType.setText(selectLikedVenue.getType());
            venueAddress.setText(selectLikedVenue.getAddress());
            venueSuburb.setText(selectLikedVenue.getSuburb() + " " + selectLikedVenue.getPostcode());
            venueBusinessCategory.setText(selectLikedVenue.getBusinessCategory().replace(","," "));
            venueLga.setText(selectLikedVenue.getLga());
        }

        if (heartFlag){
            heartButton.setLiked(true);
        }else {
            heartButton.setLiked(false);
        }


        heartButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

            }

            @Override
            public void unLiked(LikeButton likeButton) {


                Toast.makeText(getContext(), "Venue added", Toast.LENGTH_SHORT).show();
            }
        });


        closeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });


        streetViewButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        routerPlannerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

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


