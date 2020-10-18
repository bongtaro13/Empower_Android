package com.example.empower.ui.map;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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


    private Bundle venueDetail;


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
                if (isChecked){
                    SharedPreferences accessibleFlag = getContext().getSharedPreferences("switchFlag", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = accessibleFlag.edit();
                    editor.putBoolean("flag", true);
                    editor.apply();
                }else {
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
            venueBusinessCategory.setText(selectLikedVenue.getBusinessCategory().replace(","," "));
            venueLga.setText(selectLikedVenue.getLga());
            if (venueType.equals("sport venue")){
                activeHour.setVisibility(VISIBLE);
                activeHour.setText(activeHourString);
            }else {
                activeHour.setVisibility(GONE);
            }
        }




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


